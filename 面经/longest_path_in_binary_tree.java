package 面经;/*
* 题目描述：
*
* Longest Path in Binary Tree
* 给个2叉树， 找出从1个叶节点到另1个叶节点最长的路径，返回路径长度
* */

import java.util.ArrayList;
import java.util.List;

public class longest_path_in_binary_tree {
    public static class TreeNode {
        int val;
        TreeNode left; // prev
        TreeNode right; // next
        TreeNode(int x) { val = x; }
    }
    private int max = 0;
    // 返回最长长度的话，一定是从叶节点到叶节点，否则，就往叶节点延伸，可以取得更长的长度。
    public int longestPath(TreeNode root) {
        dfs(root);
        return max;
    }
    private int dfs(TreeNode root) {
        if (root == null)	return 0;
        int left = dfs(root.left);
        int right = dfs(root.right);
        max = Math.max(max, left + right + 1);
        return Math.max(left, right) + 1;
    }

/*
*
* 124. Binary Tree Maximum Path Sum
* Given a binary tree, find the maximum path sum.
* For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along
* the parent-child connections. The path must contain at least one node and does not need to go through the root.
* Given the below binary tree,
*
*       1
*      / \
*     2   3
*      \
*      -1
*      /
*     2
* Return 7.
* */

    private int max1 = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        dfs1(root);
        return max1;
    }
    private int dfs1(TreeNode root) {
        if (root == null)   return 0;
        int left = Math.max(0, dfs(root.left));
        int right = Math.max(0, dfs(root.right));
        max1 = Math.max(max1, left + right + root.val);
        return Math.max(left, right) + root.val;
    }

    // print the path
    public class Solution {
        public List<String> binaryTreePaths(TreeNode root) {
            List<String> res = new ArrayList<>();
            if( root != null ) print(root,"" , res);
            return res;
        }
        private void print(TreeNode node, String s, List<String> res){
            if(node.left == null && node.right == null ){
                res.add( s + node.val);
            }
            if(node.left != null){
                String str = s + node.val + "->";
                print(node.left, str , res);
            }
            if(node.right != null){
                String str = s + node.val + "->";
                print(node.right, str ,res);
            }
        }
    }

}
