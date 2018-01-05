import java.util.Arrays;

public class max_sum_3_non_overlap_subarrays {
    public static int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        // translate the original array to sum array
        for (int i = 1; i < nums.length; i++) {
            nums[i] = nums[i] + nums[i-1];
        }

        // translate the original array to difference array (difference of sum_array[i+k] - sum_array[i])
        // which is also the mid array
        int[] mid = new int[nums.length - (k-1)];
        int length = mid.length;
        mid[0] = nums[k-1];
        for (int i = 1; i < length; i++) {
            mid[i] = nums[i+k-1] - nums[i-1];
        }

        // initialize the left array from left to right, based on the mid array
        int[] left = new int[length];
        left[0] = mid[0];
        for (int i = 1; i < length; i++) {
            if (mid[i] < left[i-1]) {
                left[i] = left[i-1];
            } else {
                left[i] = mid[i];
            }
        }

        // initialize the right array from right to left, based on the mid array
        int[] right = new int[length];
        right[length-1] = mid[length-1];
        for (int i = length - 2; i >= 0; i--) {
            // >=, from right to left, to make the index lexicographically smallest
            if (mid[i] < right[i+1]) {
                right[i] = right[i+1];
            } else {
                right[i] = mid[i];
            }
        }

        // find the solution!
        // middle interval is [i, i+k-1], where k <= i <= n-2k
        int max = Integer.MIN_VALUE;
        int[] result = new int[3];
        for (int i = k; i <= nums.length - 2*k; i++) {
            int sum = mid[i] + left[i-k] + right[i+k];
            if (max < sum) {
                max = sum;
                result[1] = i;
                result[2] = i+k;
                int leftIndex = i - k;
                while (leftIndex - 1 >= 0 && left[leftIndex] == left[leftIndex-1]) leftIndex--;
                result[0] = leftIndex;
                int rightIndex = i + k;
                while(rightIndex + 1 <= length - 1 && right[rightIndex] == right[rightIndex+1]) rightIndex++;
                result[2] = rightIndex;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {4,5,10,6,11,17,4,11,1,3};
        System.out.println(Arrays.toString(maxSumOfThreeSubarrays(nums,1)));
    }
}
