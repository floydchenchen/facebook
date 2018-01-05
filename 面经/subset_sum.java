public class subset_sum {
    public static int subsetSum(int[] nums, int s) {
        // an array that store the amount of sums
        int[] dp = new int[s + 1];
        dp[0] = 1;
        for (int n : nums)
            for (int i = s; i >= n; i--)
                dp[i] += dp[i - n];
        return dp[s];
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,1};
        System.out.println(subsetSum(nums,4));
    }
}
