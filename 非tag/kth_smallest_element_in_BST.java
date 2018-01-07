/*
* 230. Kth Smallest Element in a BST
* https://leetcode.com/problems/kth-smallest-element-in-a-bst/description/
*
* Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
*
* Note:
* You may assume k is always valid, 1 ≤ k ≤ BST's total elements.
*
* */

package 非tag;

public class kth_smallest_element_in_BST {
    // method 1: binary search
    // time: average O(n)
    public static int kthSmallest(TreeNode root, int k) {
        // 数左边
        int count = countNodes(root.left);
        if (k <= count) {
            return kthSmallest(root.left, k);
        } else if (k > count + 1) {
            return kthSmallest(root.right, k-1-count); // 1 is counted as current node
        }
        // if (k == count + 1)
        return root.val;
    }

    private static int countNodes(TreeNode n) {
        if (n == null) return 0;
        return 1 + countNodes(n.left) + countNodes(n.right);
    }

    /*
    *
    * Follow up:
    * What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently?
    * How would you optimize the kthSmallest routine?
    *
    * use binary search
    * */
}
