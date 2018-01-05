package 面经;/*
* 题目描述：
* Given two binary search tree respectively, find first pair of non-matching leaves.
*
* 思路：先建树，然后再找leaf
*
* 分析：
* time: O(n)
* space: O(h)
*
* */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class find_first_different_node_by_preorder_traversal {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    static int result = Integer.MIN_VALUE;
    public static int findFirstFromBST(TreeNode root1, TreeNode root2){
        if( root1 != null || root2 != null ){
            if( root1.val != root2.val ) result = root1.val;
            findFirstFromBST(root1.left, root2.left);
            findFirstFromBST(root1.right, root2.right);
        }
        return result;
    }

    // 对于array representation的BST
    // binary tree简单。只要遇到[val, null, null]或者[val, null, 出界]或者[val, 出界]的pattern就知道val是叶子。
    public static int[] findFirstFromBST(int[] preorder1, int[] preorder2) {
        int m = preorder1.length, n = preorder2.length, i = 0, j = 0;
        while (true) {
            while (i < m) {
                if (i + 2 < n && preorder1[i+1] == -1 && preorder1[i+2] == -1) break;
                if (i + 2 == n && preorder1[i+1] == -1) break;
                if (i + 1 == n) break;
                i++;
            }
            while (j < n) {
                if (j + 2 < n && preorder2[j+1] == -1 && preorder2[j+2] == -1) break;
                if (j + 2 == n && preorder2[j+1] == -1) break;
                if (j + 1 == n) break;
                j++;
            }
            if (i >= m || j >= n) return new int[]{-1, -1};
            if (preorder1[i] != preorder2[j]) return new int[] {preorder1[i], preorder2[j]};
            i++;
            j++;
        }
    }


/*
* follow-up:
* Given two pre-order traversal arrays of two binary search tree respectively, find first pair of non-matching leaves.
* preorder: 左-中-右
* */
    // 方法1：先建树，再找leaf, time: O(n), space: O(n)
    private static int index;
    public static int[] findFirstFromPreorder(int[] pre1, int[] pre2){
        TreeNode root1 = constructBST(pre1);
        TreeNode root2 = constructBST(pre2);
        List<TreeNode> leaves1 = new ArrayList<>();
        List<TreeNode> leaves2 = new ArrayList<>();
        getLeaves(leaves1, root1);
        getLeaves(leaves2, root2);
        int[] result = new int[2];
        for (int i = 0; i < Math.min(leaves1.size(), leaves2.size()); i++)
            if (leaves1.get(i).val != leaves2.get(i).val) {
                result[0] = leaves1.get(i).val;
                result[1] = leaves2.get(i).val;
                return result;
            }
        return result;
    }

    private static void getLeaves(List<TreeNode> result, TreeNode root) {
        if (root == null)   return;
        if (root.left == null && root.right == null) {
            result.add(root);
            return;
        }
        getLeaves(result, root.left);
        getLeaves(result, root.right);
    }

    // 这个方法是“Construct BST from given preorder traversal”的O(n)解法，使用 MIN-MAX 思想，此题还有O(n^2)解法。
    // 参见 http://www.geeksforgeeks.org/construct-bst-from-given-preorder-traversa/
    private static TreeNode constructBST(int[] pre) {
        index = 0;
        return recursive(pre, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private static TreeNode recursive(int[] pre, int min, int max) {
        if (index >= pre.length)  return null;
        if (pre[index] <= min || pre[index] >= max)     return null;
        TreeNode root = new TreeNode(pre[index++]);
        root.left = recursive(pre, min, root.val);
        root.right = recursive(pre, root.val, max);
        return root;
    }

    // 方法2: stack, time:
/* 然后参考 lc 255. Verify Preorder Sequence in Binary Search Tree中，用Stack辅助，进行iterative建树。
* 其实这个Stack的使用，跟大家最熟悉的iterative的Binary Tree Inorder Traverse有类似之处，
* 这个栈都是只记录左孩子，如果要向右走，需要有pop stack的操作。
*
* 逻辑：
* 如果新节点小于栈顶，新节点做原栈顶元素的左孩子，原栈顶元素不可能是叶子，新节点成为潜在的叶子。
* 如果新节点大于栈顶元素，
*       1. 如果只pop一次，就能做右孩子，表明新节点接在原栈顶元素右边，原栈顶元素不可能是叶子，新节点成为潜在的叶子。
*       2. 如果pop2次或以上，才能做右孩子，表明原栈顶元素就是叶子。
* */
    public static int[] findFirstNonMatchingLeavesInBST(int[] preorder1, int[] preorder2) {
        Stack<Integer> s1 = new Stack<Integer>();
        Stack<Integer> s2 = new Stack<Integer>();
        int m = preorder1.length, n = preorder2.length;
        int i = 0, j = 0;
        int leaf1 = 0, leaf2 = 0;
        while(true) {
            while(i < m) {
                int num = preorder1[i];
                int num_pop = 0;
                while(!s1.isEmpty() && num > s1.peek()) {
                    s1.pop();
                    num_pop++;
                }
                // 说明原栈顶元素是叶子，退出循环进行比较
                if(num_pop >= 2) break;
                s1.push(num);
                leaf1 = num;
                i++;
            }
            while(j < n) {
                int num = preorder2[j];
                int num_pop = 0;
                while(!s2.isEmpty() && num > s2.peek()) {
                    s2.pop();
                    num_pop++;
                }
                // 说明原栈顶元素是叶子，退出循环进行比较
                if(num_pop >= 2) break;
                s2.push(num);
                leaf2 = num;
                j++;
            }
            if(leaf1 != leaf2) {
                return new int[]{leaf1, leaf2};
            }
            if(i >= m || j >= n) {
                return new int[]{-1, -1};
            }
        }
    }

/*
* follow-up 2:
* If they are general binary trees instead of BSTs, could you solve it? give out your reason.
*
* 分析：
* time: O(n)
*
* 思路：
* 只要遇到[val, null, null]或者[val, null, 出界]或者[val, 出界]的pattern就知道val是叶子。
* */
    public int[] findNotMatchingLeaf(String pre1, String pre2){
        int[] res = new int[2];
        String[] str1 = pre1.split("null, null");
        String[] str2 = pre2.split("null, null");
        for (int i = 0;i < Math.min(str1.length, str2.length); i++){
            char c1 = str1[i].charAt(str1[i].length() - 2);
            char c2 = str2[i].charAt(str2[i].length() - 2); //逗号
            if(c1 != c2){
                res[0] = c1 - '0';
                res[1] = c2 - '0';
                return res;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr1 = {4,6,7,8,9,11,12};
        int[] arr2 = {4,6,7,8,10,11,12};
        System.out.println(Arrays.toString(findFirstNonMatchingLeavesInBST(arr1, arr2)));
    }
}

