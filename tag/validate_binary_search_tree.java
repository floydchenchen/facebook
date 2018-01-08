/*
*
* 98. Validate Binary Search Tree
* https://leetcode.com/problems/validate-binary-search-tree/description/
*
* Given a binary tree, determine if it is a valid binary search tree (BST).
*
* */

package tag;

import java.util.Stack;

public class validate_binary_search_tree {

    // recursive solution
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    // 这里用long,主要是为了过test case:[2147483647] (Integer.MAX_VALUE)...
    public boolean isValidBST(TreeNode root, long min, long max) {
        if (root == null) return true;
        if (root.val >= max || root.val <= min) return false;
        return isValidBST(root.left, min, root.val) && isValidBST(root.right, root.val, max);
    }

    // iterative method (inorder traversal)
    public boolean isValidBST2(TreeNode root) {
        if( root == null ) return true;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        while( root != null || !stack.isEmpty() ){
            while( root != null ){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if( pre != null && root.val <= pre.val ) return false;
            pre = root;
            root = root.right;
        }
        return true;
    }
}
