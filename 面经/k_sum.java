package 面经;

import java.util.*;

public class k_sum {
    // 最简单的two sum, hash map
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                result[0] = map.get(target - nums[i]);
                result[1] = i;
                return result;
            }
            map.put(nums[i], i);
        }
        return result;
    }

    // three sum, O(n^2)

    // iteratively apply two-sum for each element in nums array
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            // 如果i = 0,或者i和前一个不一样
            if (i == 0 || (i > 0 && nums[i] != nums[i-1])) {
                //通过low和high找除开nums[i]的其他两个值使得他们加起来等于0
                int low = i + 1, high = nums.length - 1, sum = -nums[i];
                while (low < high) {
                    // 这个if改变了
                    // Arrays.asList
                    if (nums[low] + nums[high] == sum) {
                        list.add(Arrays.asList(nums[i], nums[low], nums[high]));
                        // skip duplicates
                        while (low < high && nums[low] == nums[low + 1]) low++;
                        while (low < high && nums[high] == nums[high - 1]) high--;
                        low++;
                        high--;
                    } else if (nums[low] + nums[high] < sum) {
                        low++;
                    } else high--;

                }
            }
        }
        return list;
    }
/*
* follow-up: 怎么不用先排序的方法依然可以用O(n^2)的时间复杂度解决three sum的问题
* 就用HashMap存<num, index>就行了，然后用两层loop，在Map里找target - num1 - num2的值并且index > i1 && index > 2
* */

/*
*
* follow-up: k sum
* */

/*
* DP的方法
* f[i][j][t]表示前i个元素里面取j个元素之和为t有多少个方案
* 初始化：ksum[i][0][0] =1(i: 0 ~ n)，即前i个元素里面取0个元素使其和为0的方法只有1种，那就是什么都不取
* 意思就是：
* （1）我们可以把当前A[i - 1]这个值包括进来，所以需要加上dp[i - 1][j - 1][t - A[i - 1]]（前提是t - A[i - 1]要大于0）
* （2）我们可以不选择A[i - 1]这个值，这种情况就是dp[i - 1][j][t]，也就是说直接在前i-1个值里选择一些值加到target.* */
    public int  kSum1(int A[], int k, int target) {
        if (target < 0) return 0;
        int n = A.length;
        int[][][] dp = new int[n+1][k+1][target+1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                for (int t = 0; t <= target; t++) {
                    // 初始化：ksum[i][0][0] =1(i: 0 ~ n)，即前i个元素里面取0个元素使其和为0的方法只有1种，那就是什么都不取
                    if (j == 0 && t == 0) dp[i][j][t] = 0;
                    else if (i != 0 && j != 0 && t != 0) {
                        dp[i][j][t] = dp[i-1][j][t];
                        if (t - A[i-1] >= 0) dp[i][j][t] += dp[i - 1][j - 1][t - A[i-1]];
                    }
                }
            }
        }
        return dp[n][k][target];
    }

    // 优化space
    public int  kSum2(int A[], int k, int target) {
        if (target < 0) return 0;
        int n = A.length;
        int[][] dp = new int[k+1][target+1];
        // 初始化：only one solution for empty set
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int t = target; t > 0; t--) {
                for (int j = 1; t <= k; j++) {
                    if (t - A[i - 1] >= 0) dp[j][t] += dp[j-1][t - A[i - 1]];
                }
            }
        }
        return dp[k][target];
    }

    // 方法2: backtracking，time: O(2^n)
    static ArrayList<Integer> result = new ArrayList<Integer>();
    static boolean flag = false;
    public static ArrayList<Integer> kSum3(int A[], int k, int target) {
        backtrack(result, A, k, target, 0);
        return result;
    }

    public static void backtrack(ArrayList<Integer> path, int[] A, int k, int remain, int index) {
        if (path.size() == k) {
            if (remain == 0) flag = true;
            return;
        }
        for (int i=index; i<A.length; i++) {
            path.add(A[i]);
            backtrack(path, A, k, remain-A[i], i+1);
            if (flag) break;
            path.remove(path.size()-1);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1,3,6,4,7,4,3,2,5};
        System.out.println(kSum3(arr, 2, 6));
    }
}
