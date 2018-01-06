/*
*
* 题目描述：
* 173. Binary Search Tree Iterator
*
* Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.
* Calling next() will return the next smallest number in the BST.
* Note: next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
*
*
*
* */

package tag;

import java.util.Stack;

public class binary_search_tree_iterator {

    Stack<TreeNode> stack = new Stack<TreeNode>();
    public binary_search_tree_iterator(TreeNode root) {
        TreeNode node = root;
        if (node == null) return;
        stack.push(node);
        while(node.left != null){
            node = node.left;
            stack.push(node);
        }
    }


    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.empty();
    }

    /** @return the next smallest number */
    public int next() {
        TreeNode node = stack.pop();
        int result = node.val;
        // traverse through right child
        if (node.right != null) {
            node = node.right;
            while (node != null) {
                stack.push(node);
                if (node.left != null) node = node.left;
                else break;
            }
        }
        return result;
    }

}
