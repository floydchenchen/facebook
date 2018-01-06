/*
* 题目描述：
* Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
* For example:
* Given binary tree [3,9,20,null,null,15,7],
*    3
*   / \
*  9  20
*    /  \
*   15   7
*
* return its level order traversal as:
* [
*  [3],
*  [9,20],
*  [15,7]
* ]
* */


package tag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class binary_tree_level_order_traversal {
    // BFS, O(n) time, O(n) space
    public List<List<Integer>> levelOrderBFS(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        List<List<Integer>> list = new LinkedList<List<Integer>>();
        if (root == null) return list;

        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> temp = new LinkedList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
                temp.add(node.val);
            }
            list.add(temp);
        }
        return list;
    }

    // DFS, O(n) time, O(n) space
    public List<List<Integer>> levelOrderDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        DFS(result, root, 0);
        return result;
    }
    private void DFS(List<List<Integer>> result, TreeNode root, int level) {
        if (root == null) return;
        if (level == result.size()) result.add(new ArrayList<>());
        result.get(level).add(root.val);
        DFS(result, root.left, level + 1);
        DFS(result, root.right, level + 1);
    }
}
