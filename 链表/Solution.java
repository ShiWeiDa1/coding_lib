package 链表;

import java.util.ArrayList;
import java.util.Collections;

//idea 格式化代码快捷键 ctr+alt+l 
class ListNode {
    // 保存的数据
    int val;
    // 指向下个元素
    ListNode next = null;

    // 构造方法
    public ListNode(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "ListNode{" + "val=" + val + ", next=" + next + '}';
    }
}

/**
 * 输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。
 */
public class Solution {

    // 第一种方式,遍历所有的节点,然后通过Collections工具类反转list
    public static ArrayList<Integer> printListFromTailToHead(ListNode listNode) {

        ArrayList<Integer> list = new ArrayList<>();

        while (listNode != null) {
            // 添加列表中的值
            list.add(listNode.val);
            listNode = listNode.next;

        }
        Collections.reverse(list);
        return list;

    }

    // 第二种方式,通过递归来拿到尾部的值
    static ArrayList<Integer> list3 = new ArrayList<>();

    public static ArrayList<Integer> printListFromTailToHead2(ListNode listNode) {

        if (listNode != null) {
            printListFromTailToHead(listNode.next);
            list3.add(listNode.val);
        }
        return list3;
    }

    // 头插法创建链表
    public static ListNode createNode() {

        int val = 0;

        // 头节点
        ListNode hNode = new ListNode(-1);

        //
        while (val != 10) {
            ListNode node = new ListNode(val);
            node.next = hNode.next;
            hNode.next = node;
            ++val;
        }
        // ListNode{val=-1, next=ListNode{val=9, next=ListNode{val=8,
        // next=ListNode{val=7, next=ListNode{val=6, next=ListNode{val=5,
        // next=ListNode{val=4, next=ListNode{val=3, next=ListNode{val=2,
        // next=ListNode{val=1, next=ListNode{val=0, next=null}}}}}}}}}}}
        return hNode;
    }

    public static void main(String[] args) {
        ListNode node = createNode();
        System.out.println(node);
        System.out.println(printListFromTailToHead(node));
    }
}
