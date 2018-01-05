package 面经;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class subarray_sum {
    //output the number of subarray Sum equals to k
    public static int subarraySum(int[] nums, int k) {
        int sum = 0, result = 0;
        // make use of the sum_list
        // the key is the sum at index i, value is the accumulated # that could get to k
        Map<Integer, Integer> sumList = new HashMap<>();
        sumList.put(0,1);

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sumList.containsKey(sum - k)) {
                result += sumList.get(sum - k);
            }
            // getOrDefault 学习一下
            sumList.put(sum, sumList.getOrDefault(sum, 0) + 1);
            System.out.println("result: " + result);
        }
        return result;
    }

    // 209. minimum size of subarray whose sum >= s
    // search if a window of size k exists that satisfy the condition
    public int minSubArrayLen(int s, int[] nums) {
        int start = 1, end = nums.length, min = 0;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (windowExist(mid, nums, s)) {
                end = mid - 1;
                min = mid;
            } else start = mid + 1;
        }
        return min;
    }

    // windowExists把主方程里的mid当成size来check
    private boolean windowExist(int size, int[] nums, int s) {
        int sum = 0;
        if (size == 0) return false;
        for (int i = 0; i < nums.length; i++) {
            if (i >= size) sum -= nums[i - size];
            sum += nums[i];
            if (sum >= s) return true;
        }
        return false;
    }


    // 325 maximum size subarray sum equals k
    public int maxSubArrayLen(int[] nums, int k) {
        int sum = 0, max = 0;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            // 相当于sum list
            sum += nums[i];
            if (sum == k) max = i + 1; // 不要忘了这一步，而且是i+1!
            if (map.containsKey(sum - k)) {
                max = Math.max(max, i - map.get(sum - k));
            }
            if (!map.containsKey(sum)) { // element有可能是0!
                map.put(sum, i);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] nums = {1,1,1};
        System.out.println(subarraySum(nums, 2));
    }
}
