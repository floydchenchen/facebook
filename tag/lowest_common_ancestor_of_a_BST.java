/*
*
* 235. Lowest Common Ancestor of a Binary Search Tree
* https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/description/
* Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
*
* */

package tag;

import java.util.HashSet;
import java.util.Set;

public class lowest_common_ancestor_of_a_BST {

    /*
        method without parent pointer
    */

    // recursive solution
    // time: O(h) space: O(1)
    public static TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val < Math.min(p.val, q.val)) return lowestCommonAncestor1(root.right, p, q);
        if (root.val > Math.max(p.val, q.val)) return lowestCommonAncestor1(root.left, p, q);
        return root;
    }

    // iteration
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        // 如果大于0，说明p,q两个点在root的一边，这两结果只能两正或者两负
        while ((root.val - p.val) * (root.val - q.val) > 0) {
            root = p.val < root.val ? root.left : root.right;
        }
        return root;
    }

    /*
        methods with parent pointer
    */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Set<TreeNode> ancestors = new HashSet<>();
        while (p != null || q != null) {
            if (p != null) {
                // 加入p不成功，说明set里面已经有了p
                if (!ancestors.add(p))	return p;
//                p = p.parent;
            }
            if (q != null) {
                if (!ancestors.add(q))	return q;
//                q = q.parent;
            }
        }
        return null;
    }

    /*
    *
    * follow-up:
    * follow up是如果两个节点靠的很近怎么办，这样如果树的height太高，这样就需要把所有的ancestors都遍历一遍，
    * 只要边存边查就可以了，不用先全部遍历把所有ancestors都存到hashSet里，他说cool，然后就是问问题。
    * （注意考虑一下如果有一个节点是Null，或者两个节点不在同一个tree里怎么办）
    *
    * */
}
