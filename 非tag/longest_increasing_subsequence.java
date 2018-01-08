/*
*
* 300. Longest Increasing Subsequence
* https://leetcode.com/problems/longest-increasing-subsequence/
*
* Given [10, 9, 2, 5, 3, 7, 101, 18],
* The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4.
* Note that there may be more than one LIS combination, it is only necessary for you to return the length.
* Your algorithm should run in O(n2) complexity.
*
* Follow up: Could you improve it to O(n log n) time complexity?
*
* */

package 非tag;

import java.util.Arrays;

public class longest_increasing_subsequence {

    // DP, binary search
    // time: O(nlgn), space: O(n)
    // dp[i]存的是长度为(i+1)的subsequence中的最后一个element（即最大element）的可能的最小值
    // 这样dp里的值实际上是连续增长的，所以可以用binary search
    public static int lengthOfLIS1(int[] nums) {
        int[] dp = new int[nums.length];
        int len = 0;
        // binary search on dp array
        for(int num : nums) {
            int start = 0, end = len;
            while (start < end) {
                int mid = start + (end - start) / 2;
                // 用num的值去更新dp里的值
                if (dp[mid] < num) start = mid + 1;
                else end = mid; // else if dp[mid] >= num，所以end不能是mid - 1，因为有可能dp[mid] == num
            }
            dp[start] = num;
            if (start == len) len++;
            System.out.println(Arrays.toString(dp));
        }

        return len;
    }

    // DP, 普通方法
    // time: O(n^2), space: O(n)
    public static int lengthOfLIS2(int[] nums) {
        // dp[i]: LIS from index 0 to index i
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int max = 1;
        for (int i = 1; i < nums.length; i++)
            for (int j = 0; j < i; j++)
                // 如果前小于后，说明是在increasing，而且仅当dp里存的前面大于后面（因为有可能后面的dp[i]存了大的值）
                if (nums[j] < nums[i] && dp[j] >= dp[i]) {
                    dp[i] = dp[j] + 1; // 更新后面 = 前面 + 1
                    max = Math.max(max, dp[i]);
                }
        return max;
    }

    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(lengthOfLIS1(nums));
    }
}
