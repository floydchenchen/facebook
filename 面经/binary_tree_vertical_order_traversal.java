/*
*
* 问题描述：
* https://leetcode.com/problems/binary-tree-vertical-order-traversal/description/
*
* 思路：
* calculate the range(width) of left and right child
* a queue of TreeNode
* 一个对应的 a queue of their column number (colQueue)
* BFS
*
* sample tree
*
*      3
*     /\
*    /  \
*    9   8
*   /\  /\
*  /  \/  \
* 4  01   7
*    /\
*   5 2
*
* expected result: [[4], [9, 5], [3, 0, 1], [8, 2], [7]]
* */

package 面经;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class binary_tree_vertical_order_traversal {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        int[] range = new int[] {0, 0};
        getRange(root, range, 0); // 以上面的tree为例，这时候range变成了[-2, 2]

        for (int i = range[0]; i <= range[1]; i++) {
            result.add(new ArrayList<Integer>());
        }

        // queue是对node的queue, colQueue是对应node所在的col的queue，这样保证node被加在对应的col上
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> colQueue = new LinkedList<>();

        queue.add(root);
        // 因为range的范围是[-2, 2]，而result list的index是[0,4]，所以-range[0]即为root所在的column
        colQueue.add(-range[0]);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            int col = colQueue.poll();

            result.get(col).add(node.val);

            if (node.left != null) {
                queue.add(node.left);
                colQueue.add(col - 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                colQueue.add(col + 1);
            }
        }
        return result;
    }

    public void getRange(TreeNode root, int[] range, int col) {
        if (root == null) {
            return;
        }
        range[0] = Math.min(range[0], col);
        range[1] = Math.max(range[1], col);

        getRange(root.left, range, col - 1);
        getRange(root.right, range, col + 1);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(0);
        root.left.right.right = new TreeNode(2);
        root.right = new TreeNode(8);
        root.right.left = new TreeNode(1);
        root.right.right = new TreeNode(7);
        root.right.left.left = new TreeNode(5);

        binary_tree_vertical_order_traversal b = new binary_tree_vertical_order_traversal();
        System.out.println(b.verticalOrder(root));
    }
}
