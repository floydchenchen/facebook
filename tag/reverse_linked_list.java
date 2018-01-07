/*
*
* 206. Reverse Linked List
* https://leetcode.com/problems/reverse-linked-list/description/
*
* 题目描述：Reverse a singly linked list.
*
* */

package tag;

import java.util.List;
import java.util.Stack;

public class reverse_linked_list {

    // iterative way, time O(n), space: O(1)
    public static ListNode reverseList(ListNode head) {
        // make a null prev
        ListNode prev = null;
        // 注意顺序，建next，改指针，改prev，改head
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    // 不改变head
    public static ListNode reverseList2(ListNode head) {
        ListNode prev = null, curr = head, next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    //recursive way
    public ListNode reverseList3(ListNode head) {
        return reverseHelper(head, null);
    }

    private ListNode reverseHelper(ListNode head, ListNode prev) {
        if (head == null) return prev;
        ListNode next = head.next;
        head.next = prev;
        return reverseHelper(next, head);
    }

    /*
    *
    * follow-up: print linkedlist in reverse order, (print linked list, no reverse)
    *
    * */

    // recursive
    // if cannot use recursion, cannot modify the linkedlist, we can use StringBuilder.reverse().toString()
    public static void printList(ListNode head) {
        if (head == null) return;
        printList(head.next);
        System.out.print(head.val + " ");
    }

    // iterative: O(n) space, O(n) time
    public static void printList2(ListNode head){
        Stack<ListNode> stack = new Stack<>();
        while (head != null) {
            stack.push(head);
            head = head.next;
        }
        while (!stack.isEmpty())
            System.out.println(stack.pop().val);
    }

    /*
    *
    * follow-up:
    * if we need to use O(logn) space ? [一看到O(logn)就该想到binary search]
    * we can use recursion to print the right part, and then the left part
    *
    * time: O(nlogn), space: O(logn)
    * */

    public static void printList3(ListNode head) {
        if (head == null) return;
        // count the length
        ListNode temp = head;
        int length = 0;
        while (temp != null) {
            temp = temp.next;
            length++;
        }
        printHelper(head, length);
    }

    private static void printHelper(ListNode head, int length) {
        if (length == 1) {
            System.out.println(head.val + " ");
            return;
        }
        ListNode rightHead = head;
        int count = 0;
        // 一定是 < length / 2才对！
        while (count < length / 2) { // 累积起来是O(n) time
            rightHead = rightHead.next;
            count++;
        }
        // 先print右边，再print左边，以达到print in reverse order
        // 注意两边的length，用笔画一画就知道了
        // 比如5个node，right范围是（3~5），left范围是（1~2）
        printHelper(rightHead, length / 2); // O(logn) time
        printHelper(head, length - length / 2); // O(logn) time
    }


    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        head.next = node2;
        node2.next = node3;
        printList3(head);
    }
}
