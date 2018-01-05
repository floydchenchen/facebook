import java.util.LinkedList;
import java.util.List;


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
public class binary_tree_vertical_order_traversal {
    List<List<Integer>> left = new LinkedList<>();
    List<List<Integer>> right = new LinkedList<List<Integer>>();
    LinkedList<Integer> zero = new LinkedList<Integer>();

    public List<List<Integer>> verticalOrder(TreeNode root) {
        helper(root, 0);
        left.add(zero);
        left.addAll(right);
        return left;
    }

    public void helper(TreeNode root, int index) {
        if (root == null) return;
        if (index == 0) {
            zero.add(root.val);
        }
        else if (index > 0) { // left nodes
            if (index >= left.size()) ((LinkedList)left).addFirst(new LinkedList<Integer>());
            left.get(left.size() - index).add(root.val);
            System.out.println("left index is: " + (index-1) + ", node is: " + root.val + ", left size is: " + left.size());
            for (int i = 0; i < left.size(); i++) {
                System.out.print(left.get(i).toString());
            }
            System.out.println();
        }
        else { //right nodes
            System.out.println("real right index: " + index + ", size of right: " + right.size());
            if (-index > right.size()) right.add(new LinkedList<Integer>());
            right.get(-(index+1)).add(root.val);
            System.out.println("right index is: " + (-index+1) + ", node is: " + root.val + ", right size is: " + right.size());
            for (int i = 0; i < right.size(); i++) {
                System.out.print(right.get(i).toString());
            }
            System.out.println();
        }
        helper(root.left, index + 1);
        helper(root.right, index - 1);
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
