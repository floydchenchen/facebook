import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class sliding_window_maximum {
    // 注意是往queue里加array的index，使得queue单调递减（queue中的index即为window可能的max值）
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (k < 1 || k > nums.length) return new int[0];
        int[] result = new int[nums.length - k + 1];
        int index = 0;
        // 这个新建的queue相当于一个单调递减的queue（PQ?）
        Deque<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < nums.length; i++) {
            // 如果queue中第一个值不在窗口范围中了，remove之；i-k+1是window的左边界
            while (!queue.isEmpty() && queue.peekFirst() < i - k + 1) queue.removeFirst();
            // 如果新的值大于queue尾部的一些值，删除它们（它们比新加的值还小，就不可能变成max了，类似于pruning）
            while (!queue.isEmpty() && nums[i] > nums[queue.peekLast()]) queue.removeLast();
            queue.addLast(i);
            if (i + 1 - k >= 0) result[index++] = nums[queue.peekFirst()];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1,3,-1,-3,5,3,6,7};
        System.out.println(Arrays.toString(maxSlidingWindow(nums, 3)));
    }
}
