/*
*
* 494. Target Sum
* https://leetcode.com/problems/target-sum/
*
* Find out how many ways to assign symbols to make sum of integers equal to target S.
*
* example:
* Input: nums is [1, 1, 1, 1, 1], S is 3.
* Output: 5
* Explanation:
*
* -1+1+1+1+1 = 3
* +1-1+1+1+1 = 3
* +1+1-1+1+1 = 3
* +1+1+1-1+1 = 3
* +1+1+1+1-1 = 3
*
* There are 5 ways to assign symbols to make the sum of nums be target 3.
*
* 思路：
* 用DP思想将问题转化成：
* Find a subset of nums that need to be positive, and the rest of them negative, such that the sum is equal to target
*
* then (P is subset of positives, N is subset of negatives（N里面本身的元素是正的）)
* e.g. P = [1,3,5], N = [2,4], target = 3
* 注意到：sum(P) + sum(N) = sum(nums)
* sum(P) - sum(N) = target
* sum(P) + sum(N) + sum(P) - sum(N) = target + sum(P) + sum(N)
* 2 * sum(P) = target + sum(nums)
*
* 所以问题被转化成了：
* Find a subset P of nums such that sum(P) = (target + sum(nums)) / 2 ==> combination sum
*
* */

package tag;

import java.util.Arrays;

public class target_sum {
    // 方法：combination sum + dp
    // the problem has been translated to find subset P s.t. "sum(P) = (target + sum(nums)) / 2"
    public static int findTargetSumWays(int[] nums, int target) {
        // find sum(nums)
        int sum = 0;
        // sum all nums
        for (int num : nums) {
            sum += num;
        }
        // corner cases: sum < target
        // also from the the translated problem we can see target + sum is even
        if (sum < target || (target + sum) % 2 != 0) return 0;
        else return subsetSum(nums, (target + sum) / 2);
    }
    // subset sum
    public static int subsetSum(int[] nums, int s) {
        // dp[j][i]: number of ways to get a sum of i by using nums[0] ~ nums[j]
        int[] dp = new int[s + 1];
        dp[0] = 1;
        for (int n : nums) {
            for (int i = s; i >= n; i--) {
                dp[i] += dp[i - n];
            }
            System.out.println(Arrays.toString(dp));
        }
        return dp[s];
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 1, 1};
        System.out.println(findTargetSumWays(nums, 3));
    }

    //2D subset sum
    public int subsetSum2D(int[] nums, int S) {
        int[][] dp = new int[nums.length+1][S+1];
        dp[0][0] = 1;

        for(int i=1; i<=nums.length; i++){
            if(nums[i-1]==0)
                dp[i][0] = dp[i-1][0] * 2;
            else
                dp[i][0] = dp[i-1][0];
        }

        for(int i=1; i<=nums.length; i++){
            for(int j=1; j<=S; j++){
                if(j>=nums[i-1])
                    dp[i][j] = dp[i-1][j-nums[i-1]] + dp[i-1][j];
                else
                    dp[i][j] = dp[i-1][j];
            }
        }

        return dp[nums.length][S];
    }
}
