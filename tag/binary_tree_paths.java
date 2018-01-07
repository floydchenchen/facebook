/*
*
* 257. Binary Tree Paths
* https://leetcode.com/problems/binary-tree-paths/description/
*
* */

package tag;

import java.util.*;

public class binary_tree_paths {
    /* method 1: recursive DFS
    *  time: O(nlgn)
    * 1. 如果所有的node在一条线上，时间复杂度。O(n^2)
    * 2. full binary tree 时间复杂度。O(nlogn)
    * 3. 优化时间复杂度：StringBuilder
    * */
    public static List<String> binaryTreePaths1(TreeNode root) {
        List<String> result = new ArrayList<>();
        dfs(result, root, "");
        return result;
    }
    private static void dfs(List<String> result, TreeNode root, String tmp) {
        if (root == null) return;
        if (root.left == null && root.right == null) result.add(tmp + root.val);
        dfs(result, root.left, tmp + root.val + "->");
        dfs(result, root.right, tmp + root.val + "->");
    }

    /*
    *
    * method 2: StringBuilder with recursive DFS
    *
    * */
    public static List<String> binaryTreePaths2(TreeNode root) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        dfs2(result, root, sb);
        return result;
    }

    private static void dfs2(List<String> result, TreeNode root, StringBuilder sb){
        if (root == null) return;
        int len = sb.length();
        sb.append(root.val);
        if (root.left == null && root.right == null) result.add(sb.toString());
        else {
            sb.append("->");
            dfs2(result, root.left, sb);
            dfs2(result, root.right, sb);
        }
        sb.setLength(len);
    }

    /*
    *
    * method 3: iterative BFS
    * use queue to store ArrayList<TreeNode>, which is the path
    *
    * 这样其实queue里面存了非常多的重复的Node, space 不是特别有效率
    * */
    public static List<String> binaryTreePaths3(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        // a queue of list of TreeNodes to represent [相当于a queue of paths]
        Queue<List<TreeNode>> queue = new LinkedList<>();
        List<TreeNode> rootList = new ArrayList<>();
        rootList.add(root);
        queue.offer(rootList);

        while (!queue.isEmpty()) {
            List<TreeNode> path = queue.poll();
            TreeNode curr = path.get(path.size() - 1);
            // 如果最后一个TreeNode curr是leaf
            if (curr.left == null && curr.right == null) {
                StringBuilder sb = new StringBuilder();
                for (TreeNode node : path) sb.append(node.val).append("->");
                sb.setLength(sb.length() - 2);// 去掉最后的"->"
                result.add(sb.toString());
            }
            if (curr.left != null) {
                // newPath直接copy旧的path!
                List<TreeNode> newPath = new ArrayList<>(path);
                newPath.add(curr.left);
                queue.offer(newPath);
            }
            if (curr.right != null) {
                List<TreeNode> newPath = new ArrayList<>(path);
                newPath.add(curr.right);
                queue.offer(newPath);
            }
        }
        return result;
    }

    /*
    *
    * 变种
    * 比如有一个 root to leaf path 是 1 2 5 2，target 是2，那么这个 path 就应该打印成 1 1 2 5 1 2 5 2。
    * 也就是
    *
    * 思路：每次遇到 2 就把前面的路径重新 append 一下: 1 (1 2) 5 (1 2 5 2).
    * */
    public List<String> binaryTreePaths4(TreeNode root,int target) {
        List<String> res = new ArrayList<>();
        if (root == null)   return res;
        helper(res, root, "", "", target);
        return res;
    }
    public void helper(List<String> res, TreeNode root, String s, String target_s, int target){
        if (root == null)   return;
        if (root.left == null && root.right == null)
            if (root.val == target)
                res.add(target_s + s + root.val);
            else    res.add(target_s + root.val);
        if (root.val == target) {
            helper(res, root.left, s + root.val + "->", target_s + s + root.val + "->", target);
            helper(res, root.right, s + root.val + "->", target_s + s + root.val + "->", target);
        } else {
            helper(res, root.left, s + root.val + "->", target_s + root.val + "->", target);
            helper(res, root.right, s + root.val + "->", target_s + root.val + "->", target);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        root.left = node2;
        root.right = node3;
        root.left.left = node4;
        root.left.right = node5;
        root.left.right.right = node6;

        System.out.println(binaryTreePaths2(root));
    }
}
