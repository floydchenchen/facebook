import java.util.Arrays;

public class combination_sum_iv {
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1; // one way to get zero, by using 0
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i - nums[j] >= 0) dp[i] += dp[i - nums[j]];
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        int[] nums = {2,3,6,7};
        combination_sum_iv c = new combination_sum_iv();
        System.out.println(c.combinationSum4(nums, 7));
    }
}
