/*
* 题目描述：convert Tree to Doubly LinkedList 并且保持inorder(左中右)遍历的顺序
* https://articles.leetcode.com/convert-binary-search-tree-bst-to/
*
* 分析：
* 方法1：recursion
* 方法2：iteration
* 两种方法都是time: O(n) space: O(h), h是树的高度
*
* 思路
* 方法1：recursion DFS
* 方法2：iteration DFS
*
* follow-up: convert sorted list back to a height balanced BST
* double linked list 转回 tree 不需要考虑双向链表的属性，直接当成单向链表来做就可以了~
* */

import java.util.Stack;

public class convert_BST_to_doubly_linkedlist {
    public static class TreeNode {
        int val;
        TreeNode left; // prev
        TreeNode right; // next
        TreeNode(int x) { val = x; }
    }
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    // recursive method
    static TreeNode head = null, prev = null;
    public static TreeNode recursiveBSTtoCircularDL(TreeNode root) {
        convert(root);
        return head;
    }
    public static void convert(TreeNode root) {
        // recursion的base case
        if (root == null) return;
        // inorder因为要从最左的child开始，所以DFS到最左
        convert(root.left);
        // root和prev连起来
        root.left = prev;
        // 如果prev不是null，说明不是head，prev和root连起来
        if (prev != null) prev.right = root;
        // 如果prev是null，说明是head，指向root
        else head = root;
        // 先把right存起来，接下来要改变
        TreeNode right = root.right;
        // 因为是inorder traversal, 总是让head.left指向current，即目前的最后一个
        head.left = root;
        // root.right同上
        root.right = head;
        // update prev
        prev = root;
        // inorder是左中右，所以右边最后价差
        convert(right);
    }

    // iterative method, DFS，因为是iterative DFS，所以可以用stack
    public static TreeNode iterativeBSTtoCircularDL(TreeNode root) {
        TreeNode head = null, prev = null;
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left; // inorder，先到最左
            }
            root = stack.pop();
            root.left = prev;
            if (prev != null) prev.right = root;
            else head = root;
            TreeNode right = root.right;
            head.left = root;
            root.right = head;
            prev = root;
            root = right; // inorder, right最后
        }
        return head;
    }
    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        TreeNode result = iterativeBSTtoCircularDL(root);
        System.out.println("root: " + result.val);
        System.out.println(result.right.val);
        System.out.println(result.right.right.val);
        System.out.println(result.right.right.right.val);
        System.out.println(result.right.right.right.right.val);

    }


    // follow-up convert sorted list back to BST
    private static ListNode node;
    public static TreeNode sortedListtoBST(ListNode root) {
        if (root == null) return null;

        int size = 0;
        ListNode runner = root;
        node = root;

        while(runner != null) {
            runner = runner.next;
            size++;
        }

        return inorderHelper(0, size - 1);
    }
    private static TreeNode inorderHelper(int start, int end){
        if (start > end) return null;

        int mid = start + (end - start) / 2;
        TreeNode left = inorderHelper(start, mid - 1);

        TreeNode treenode = new TreeNode(node.val);
        treenode.left = left;
        node = node.next;

        TreeNode right = inorderHelper(mid + 1, end);
        treenode.right = right;

        return treenode;
    }
}
