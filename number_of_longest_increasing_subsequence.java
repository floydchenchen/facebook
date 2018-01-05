public class number_of_longest_increasing_subsequence {
    public static int findNumberOfLIS(int[] nums) {
        int n = nums.length, result = 0, max_len = 0;
        int[] longestLen =  new int[n], longestCount = new int[n];
        // 从0到n-1
        for (int i = 0; i < n; i++){
            longestLen[i] = longestCount[i] = 1;
            // 从0到i-1
            for (int j = 0; j < i; j++) {
                // 如果nums[i] > nums[j]，升序
                if (nums[i] > nums[j]) {
                    // found another 同样长度的LIS的 subsequence which ends with nums[i]
                    if (longestLen[i] == longestLen[j] + 1) longestCount[i] += longestCount[j];
                    if (longestLen[i] < longestLen[j] + 1) {
                        longestLen[i] = longestLen[j] + 1;
                        longestCount[i] = longestCount[j];
                    }
                }
            }
            if (max_len == longestLen[i]) result += longestCount[i];
            if (max_len < longestLen[i]) {
                max_len = longestLen[i];
                result = longestCount[i];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1,3,5,4,7};
        System.out.println(findNumberOfLIS(nums));
    }
}

