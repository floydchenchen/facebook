/*
*
* 题目描述：
* 173. Binary Search Tree Iterator
*
* Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.
* Calling next() will return the next smallest number in the BST.
* Note: next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
*
* next smaller说明从小到大，其实就是BST的inorder traversal（左-根-右）
*
* 思路：用stack
*
* */

package tag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class binary_search_tree_iterator {

    // stack: O(h) space
    Stack<TreeNode> stack = new Stack<>();
    // constructor: 一直push left child
    public binary_search_tree_iterator(TreeNode root) {
        pushLeft(root);
    }


    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.empty();
    }

    /** @return the next smallest number */
    // pop出来之后，把它的right child，push进去
    public TreeNode next() {
        TreeNode node = stack.pop();
        pushLeft(node.right);
        return node;
    }

    // push left children to stack
    private void pushLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    /*
    *
    * follow-up:
    * 写个BST的in-order iterator，要写的function有 next() 和 all(), all() return所有剩下的
    * */
    public List<TreeNode> all() {
        List<TreeNode> result = new ArrayList<>();
        while (hasNext()) {
            result.add(next());
        }
        return result;
    }

    /*
    *
    * follow-up:
    * 改成preorder 和 postorder traversal
    *
    * */

    // preorder traversal: 根-左-右
    // 144. Binary Tree Preorder Traversal
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        // 先根
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            // 再左
            if (node.left != null) stack.push(node.left);
            // 最后右
            if (node.right != null) stack.push(node.right);
        }
        return result;
    }

    // inorder traversal: 左-根-右
    // 94. Binary Tree Inorder Traversal
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            // 先左
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // 改变root! 再中
            root = stack.pop();
            result.add(root.val);
            // 最后右
            root = root.right;
        }
        return result;
    }

    // postorder traversal: 左-右-根
    // 145. Binary Tree Postorder Traversal
    // a clever solution: the opposite of preorder
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        if (root == null) return result;
        stack.push(root);
        while(!stack.empty()) {
            TreeNode node = stack.pop();
            // 先 push left 再 push right
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
            // push了左右之后反着加，相当于把把root加在最后【重点】
            result.addFirst(node.val);
        }
        return result;
    }
}
