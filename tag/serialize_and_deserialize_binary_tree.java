/*
*
* 297. Serialize and Deserialize Binary Tree
* https://leetcode.com/problems/serialize-and-deserialize-binary-tree/description/
*
* */

package tag;

import java.util.*;

public class serialize_and_deserialize_binary_tree {

    public static void main (String[] args) throws java.lang.Exception {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        String ans = serialize(root);
        System.out.println(ans);

    }
    // BFS
    public static String serialize(TreeNode root) {
        if (root == null) return "";
        //建queue，把root放进queue和sb
        Queue<TreeNode> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        queue.add(root);
        sb.append(queue.peek().val);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            // no left child
            if (node.left == null) {
                sb.append(",").append("#");
            }
            // with leftchild
            else {
                queue.add(node.left);
                sb.append(",").append(node.left.val);
            }
            // no right child
            if (node.right == null) {
                sb.append(",").append("#");
            }
            // with right child
            else {
                queue.add(node.right);
                sb.append(",").append(node.right.val);
            }
        }
        // 删除sb最后的 "null"s and ","
        while(sb.charAt(sb.length() - 1) == ',' || sb.charAt(sb.length() - 1) == '#') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    // Decodes your encoded data to tree.
    // BFS 建树
    public static TreeNode deserialize(String data) {
        if (data.equals("")) return null;
        List<TreeNode> list = new ArrayList<TreeNode>();
        // string list代表着每个node
        String[] strings = data.split(",");

        // 新建root node，放进list中
        TreeNode root = new TreeNode(Integer.parseInt(strings[0]));
        list.add(root);

        int index = 0; // 记录目前所在list中的位置，代表目前的"parent"
        boolean left = true; // 记录左右child

        for (int i = 1 ; i < strings.length; i++) {
            if (!strings[i].equals("#")) { // 如果这个node不是null
                TreeNode node = new TreeNode(Integer.parseInt(strings[i]));
                if (left) list.get(index).left = node;
                else list.get(index).right = node;
                list.add(node);
            }
            if (!left) index++; // 如果现在的node是right child，那么我们这个node已经建完了，需要move到下一个node
            left = !left; // update flag
        }
        return root;
    }
}
