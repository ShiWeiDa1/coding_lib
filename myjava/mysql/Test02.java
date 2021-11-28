package myjava.mysql;

import static myjava.mysql.Test01.table1;
import static myjava.mysql.Test01.table2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.junit.Test;

import myjava.mysql.Test01.Filter;
import myjava.mysql.Test01.JoinType;
import myjava.mysql.Test01.Record;

/**
 *@className: Test02
 *@description:
 *@author: TY
 *@date: 2021/10/31 23:17
 *@version: V1.0
 **/
public class Test02 {
    public static final int JOIN_BUFFER_SIZE = 10000;
    public static List<?> joinBufferList = new ArrayList<>();

    @SuppressWarnings(value = { "unchecked" })
    public static <R1, R2> List<Record<R1, R2>> join(List<R1> table1, List<R2> table2, JoinType joinType, Filter<R1, R2> onFilter,
            Filter<R1, R2> whereFilter) {
        if (Objects.isNull(table1) || Objects.isNull(table2) || Objects.isNull(joinType)) {
            return new ArrayList<>();
        }

        List<Record<R1, R2>> result = new CopyOnWriteArrayList<>();
        int table1Size = table1.size();
        int fromIndex = 0, toIndex = Math.min(table1Size, JOIN_BUFFER_SIZE);
        while (fromIndex < table1Size && toIndex <= table1Size) {
            joinBufferList = table1.subList(fromIndex, toIndex);
            fromIndex = toIndex;
            toIndex = Math.min(toIndex + JOIN_BUFFER_SIZE, table1Size);
            List<Record<R1, R2>> blockNestedLoops = blockNestedLoop((List<R1>) joinBufferList, table2, onFilter);
            result.addAll(blockNestedLoops);
        }

        if (JoinType.LEFT_JOIN.equals(joinType)) {
            List<R1> r1JoinList = result.stream().map(Record::getR1).collect(Collectors.toList());
            List<Record<R1, R2>> leftJoinAppendList = new ArrayList<>();
            for (R1 r1 : table1) {
                if (!r1JoinList.contains(r1)) {
                    leftJoinAppendList.add(Record.build(r1, null));
                }
            }
            result.addAll(leftJoinAppendList);
        }

        result.removeIf(record -> Objects.nonNull(whereFilter) && !whereFilter.accept(record.getR1(), record.getR2()));

        return result;
    }

    private static <R2, R1> List<Record<R1, R2>> blockNestedLoop(List<R1> joinBufferList, List<R2> table2,
            Filter<R1, R2> onFilter) {
        List<Record<R1, R2>> blockNestedLoops = new ArrayList<>();
        for (R2 r2 : table2) {
            for (R1 r1 : joinBufferList) {
                if (Objects.isNull(onFilter) || onFilter.accept(r1, r2)) {
                    blockNestedLoops.add(Record.build(r1, r2));
                }
            }
        }
        return blockNestedLoops;
    }

    @Test
    public void innerJoinTest() {
        join(table1, table2, JoinType.INNER_JOIN, null, null).forEach(System.out::println);
        System.out.println("split-------------------------------");
        join(table1, table2, JoinType.INNER_JOIN, (r1, r2) -> r1.getA() == r2.getB(), null).forEach(System.out::println);
        /***
         * Test01.Record(r1=Test01.Table1(a=1), r2=Test01.Table2(b=3))
         * Test01.Record(r1=Test01.Table1(a=2), r2=Test01.Table2(b=3))
         * Test01.Record(r1=Test01.Table1(a=3), r2=Test01.Table2(b=3))
         * Test01.Record(r1=Test01.Table1(a=1), r2=Test01.Table2(b=4))
         * Test01.Record(r1=Test01.Table1(a=2), r2=Test01.Table2(b=4))
         * Test01.Record(r1=Test01.Table1(a=3), r2=Test01.Table2(b=4))
         * Test01.Record(r1=Test01.Table1(a=1), r2=Test01.Table2(b=5))
         * Test01.Record(r1=Test01.Table1(a=2), r2=Test01.Table2(b=5))
         * Test01.Record(r1=Test01.Table1(a=3), r2=Test01.Table2(b=5))
         * split-------------------------------
         * Test01.Record(r1=Test01.Table1(a=3), r2=Test01.Table2(b=3))
         */
    }

    @Test
    public void leftJoinTest() {
        join(table1, table2, JoinType.LEFT_JOIN, (r1, r2) -> r1.getA() == r2.getB(), null).forEach(System.out::println);
        System.out.println("split-------------------------------");
        join(table1, table2, JoinType.LEFT_JOIN, (r1, r2) -> r1.getA() > 10, null).forEach(System.out::println);
        /**
         Test01.Record(r1=Test01.Table1(a=3), r2=Test01.Table2(b=3))
         Test01.Record(r1=Test01.Table1(a=1), r2=null)
         Test01.Record(r1=Test01.Table1(a=2), r2=null)
         split-------------------------------
         Test01.Record(r1=Test01.Table1(a=1), r2=null)
         Test01.Record(r1=Test01.Table1(a=2), r2=null)
         Test01.Record(r1=Test01.Table1(a=3), r2=null)
         */
    }
}
