package myjava.mysql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@className: Test01
 *@description:
 *@author: TY
 *@date: 2021/10/31 16:04
 *@version: V1.0
 **/
public class Test01 {
    @Data
    @NoArgsConstructor
    public static class Table1 {
        private int a;

        public static Table1 build(int a) {
            return new Table1(a);
        }

        public Table1(int a) {
            this.a = a;
        }
    }

    @Data
    @NoArgsConstructor
    public static class Table2 {
        private int b;

        public static Table2 build(int b) {
            return new Table2(b);
        }

        public Table2(int b) {
            this.b = b;
        }
    }

    public enum JoinType {
        INNER_JOIN, LEFT_JOIN
    }

    @Data
    @NoArgsConstructor
    public static class Record<R1, R2> {
        private R1 r1;
        private R2 r2;

        public static <R1, R2> Record<R1, R2> build(R1 r1, R2 r2) {
            return new Record<>(r1, r2);
        }

        public Record(R1 r1, R2 r2) {
            this.r1 = r1;
            this.r2 = r2;
        }
    }

    public interface Filter<R1, R2> {
        boolean accept(R1 r1, R2 r2);
    }

    public static <R1, R2> List<Record<R1, R2>> join(List<R1> table1, List<R2> table2, JoinType joinType, Filter<R1, R2> onFilter,
            Filter<R1, R2> whereFilter) {
        // 不满足必要条件
        if (Objects.isNull(table1) || Objects.isNull(table2) || Objects.isNull(joinType)) {
            return new ArrayList<>();
        }
        List<Record<R1, R2>> result = new CopyOnWriteArrayList<>();
        for (R1 r1 : table1) {
            List<Record<R1, R2>> joinOnResults = joinOn(r1, table2, onFilter);
            result.addAll(joinOnResults);
        }

        if (JoinType.LEFT_JOIN.equals(joinType)) {
            List<R1> r1JoinList = result.stream().map(Record::getR1).collect(Collectors.toList());
            List<Record<R1, R2>> leftAppendRecords = new ArrayList<>();
            for (R1 r1 : table1) {
                // 未关联数据
                if (!r1JoinList.contains(r1)) {
                    leftAppendRecords.add(Record.build(r1, null));
                }
            }
            result.addAll(leftAppendRecords);
        }
        // where filter
        result.removeIf(
                r1R2Record -> Objects.nonNull(whereFilter) && !whereFilter.accept(r1R2Record.getR1(), r1R2Record.getR2()));

        return result;
    }

    private static <R1, R2> List<Record<R1, R2>> joinOn(R1 r1, List<R2> table2, Filter<R1, R2> onFilter) {
        List<Record<R1, R2>> result = new ArrayList<>();
        for (R2 r2 : table2) {
            // 匹配 table2 的数据
            if (Objects.isNull(onFilter) || onFilter.accept(r1, r2)) {
                result.add(Record.build(r1, r2));
            }
        }
        return result;
    }

    public static final List<Table1> table1;
    public static final List<Table2> table2;
    static {
        table1 = Arrays.asList(Table1.build(1), Table1.build(2), Table1.build(3));
        table2 = Arrays.asList(Table2.build(3), Table2.build(4), Table2.build(5));
    }

    @Test
    public void innerJoinTest() {
        join(table1, table2, JoinType.INNER_JOIN, null, null).forEach(System.out::println);
        System.out.println("split-------------------------------");
        join(table1, table2, JoinType.INNER_JOIN, (r1, r2) -> r1.a == r2.b, null).forEach(System.out::println);
        /**
         Test01.Record(r1=Test01.Table1(a=1), r2=Test01.Table2(b=3))
         Test01.Record(r1=Test01.Table1(a=1), r2=Test01.Table2(b=4))
         Test01.Record(r1=Test01.Table1(a=1), r2=Test01.Table2(b=5))
         Test01.Record(r1=Test01.Table1(a=2), r2=Test01.Table2(b=3))
         Test01.Record(r1=Test01.Table1(a=2), r2=Test01.Table2(b=4))
         Test01.Record(r1=Test01.Table1(a=2), r2=Test01.Table2(b=5))
         Test01.Record(r1=Test01.Table1(a=3), r2=Test01.Table2(b=3))
         Test01.Record(r1=Test01.Table1(a=3), r2=Test01.Table2(b=4))
         Test01.Record(r1=Test01.Table1(a=3), r2=Test01.Table2(b=5))
         split-------------------------------
         Test01.Record(r1=Test01.Table1(a=3), r2=Test01.Table2(b=3))
         */
    }

    @Test
    public void leftJoinTest() {
        join(table1, table2, JoinType.LEFT_JOIN, (r1, r2) -> r1.a == r2.b, null).forEach(System.out::println);
        System.out.println("split-------------------------------");
        join(table1, table2, JoinType.LEFT_JOIN, (r1, r2) -> r1.a > 10, null).forEach(System.out::println);
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
