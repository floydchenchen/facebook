/*
*
* 285. Inorder Successor in BST
* https://leetcode.com/problems/inorder-successor-in-bst/description/
*
* Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
* Note: If the given node has no in-order successor in the tree, return null.
*
* */

package tag;

public class inorder_successor_in_BST {

    /*
    * TreeNode没有parent指针
    *
    * 两种方法都是如果p.val < root.val（即p在root.left中）的话，那么让root = root.left，不然root = root.right
    * 如果p < root.val了，那我们离找到答案就不远了。
    * 因为p的successor永远大于p。（inorder successor）
    */
    // recursive method
    public TreeNode inorderSuccessor1(TreeNode root, TreeNode p) {
        if (root == null) return null;
        // 如果root <= p，【注意是小于等于】说明successor在right child中
        if (root.val <= p.val) {
            return inorderSuccessor1(root.right, p);
        } else { // root.val > p.val，说明successor在left child中，【或者successor是root】
            TreeNode left = inorderSuccessor1(root.left, p);
            // if no left child, then no successor
            return (left == null) ? root : left;
        }
    }

    // iterative
    // BST有一个不降序的in-order traversal
    public TreeNode inorderSuccessor2(TreeNode root, TreeNode p) {
        TreeNode succ = null;
        while (root != null) {
            if (p.val < root.val) { // 说明successor在left child中，【或者successor是root】
                succ = root;
                root = root.left;
            } else { // p.val >= root.val，【注意是小于等于】说明successor在right child中
                root = root.right;
            }
        }
        return succ;
    }

    // 补充 recursive method for predecessor
    public TreeNode predecessor(TreeNode root, TreeNode p) {
        if (root == null)
            return null;

        if (root.val >= p.val) {
            return predecessor(root.left, p);
        } else {
            TreeNode right = predecessor(root.right, p);
            return (right != null) ? right : root;
        }
    }

    /*
    *
    * 变种1:
    * TreeNode有parent指针
    *
    * 写完之后，面试官不太满意，觉得如果树很大的话，时间复杂度太高，然后交流了一下，发现面试官想要的答案是，TreeNode可以带parent指针的，
    * 输入只有一个TreeNode，没有root。所以一定要和面试官好好交流，写代码之前问清楚输入输出是什么才行。。
    *
    * BST举例:
    *            5
    *          /  \
    *         3   8
    *        /\  / \
    *       1 4 6  9
    *      /
    *     0
    *
    * 思路：
    * 1. 先检查right child，如果right child不空，答案是right child中最左的leaf【例如node是3】
    * 2.（因为这个node已经没有right child了）如果parent为空，return null，说明这个树只有一个node
    * 3. 如果node是个left child，return parent
    * 4. 如果node是right child，那么successor一直往上走，直到找到一个parent，这个parent不是right child
    *
    *
    * */
    public TreeNodeWithParent findNext(TreeNodeWithParent node) {
        if (node == null) {
            return null;
        }
        // 先检查right child，如果right child不空，答案是right child中最左的leaf
        if (node.right != null) {
            TreeNodeWithParent result = node.right;
            while (result.left != null) {
                result = result.left;
            }
            return result;
        }
        // 这个树只有一个node，没有successor
        else if (node.parent == null) {
            return null;
        }
        // left child, successor是parent
        else if (node.parent.left == node) {
            return node.parent;
        }
        // right child，
        else {
            TreeNodeWithParent result = node.parent;
            // 有parent，而且是right child
            while (result.parent != null && result.parent.right == result) {
                result = result.parent;
            }
            return result.parent;
        }
    }


    class TreeNodeWithParent {
        public TreeNodeWithParent parent;
        public TreeNodeWithParent left;
        public TreeNodeWithParent right;
        public int val;

        public TreeNodeWithParent(int val, TreeNodeWithParent parent) {
            this.val = val;
            this.parent = parent;
            this.left = null;
            this.right = null;
        }
    }


}
