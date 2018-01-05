package 面经;/*
* 题目描述：Construct BST from preorder list [中->左->右]
* https://www.geeksforgeeks.org/construct-bst-from-given-preorder-traversa/
* for example, given a preorder traversal {10, 5, 1, 7, 40, 50}, construct a BST
*  then the output should be root of following tree.
*      10
*    /   \
*   5    40
*  / \    \
* 1  7    50
*
* 分析：
* recursive DFS
* time: O(n)
* space: O(n)
*
* 思路：
* recursive DFS
* 使用 MIN-MAX 思想
*
* */

public class construct_BST_from_preorder_list {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    int index = 0;
    public TreeNode constructBST(int[] preorder) {
        if (preorder == null || preorder.length == 0) return null;
        return recursive(preorder, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    // recursive method, DFS
    private TreeNode recursive(int[] preorder, int min, int max) {
        if (index >= preorder.length || preorder[index] <= min || preorder[index] >= max) return null;
        TreeNode root = new TreeNode(preorder[index++]);
        root.left = recursive(preorder, min, root.val);
        root.right = recursive(preorder, root.val, max);
        return root;
    }
}
