/*
* 问题描述：在没排序的数组里，找最长的等差数列（index不能变）
* https://www.geeksforgeeks.org/length-of-the-longest-arithmatic-progression-in-a-sorted-array/
*
* */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class longest_arithmetic_progression {
    // 第一个方法输出长度，DP，但是这个方法assume list is sorted
    public static int lenghtOfLongestAP(int nums[]) {
        if (nums == null) return 0;
        int n = nums.length;
        if (n <= 2)  return n;
        Arrays.sort(nums);
        // dp[i][j] stores LLAP with nums[i] and nums[j] as first two elements of AP.
        int[][] dp = new int[n][n];
        int length = 2;

        // Fill entries in last column as 2. There will always be
        // two elements in AP with last number of nums as second
        // element in AP
        for (int i = 0; i < n; i++) dp[i][n-1] = 2;
        printDP(dp);
        // Consider every element as second element of AP
        for (int j=n-2; j >= 1; j--) {
            // Search for [i and k] for j，相当于two pointers
            int i = j-1, k = j+1;
            while (i >= 0 && k <= n-1) {
                if (nums[i] + nums[k] < 2 * nums[j]) k++;
                // Before changing i, set dp[i][j] as 2
                else if (nums[i] + nums[k] > 2 * nums[j]) {
                    dp[i][j] = 2;
                    i--;
                }
                // nums[i] + nums[k] == 2 * nums[j]
                else {
                    // Found i and k for j, LLAP with i and j as first two
                    // elements is equal to LLAP with j and k as first two
                    // elements plus 1. L[j][k] must have been filled
                    // before as we run the loop from right side
                    dp[i][j] = dp[j][k] + 1;
                    length = Math.max(length, dp[i][j]);
                    i--; k++;
                }
                printDP(dp);
            }

            // If the loop was stopped due to k becoming more than
            // n-1, nums the remaining entties in column j as 2
            while (i >= 0) {
                dp[i][j] = 2;
                i--;
            }
        }
        return length;
    }

    public static void printDP(int[][] dp) {
        for (int[] row : dp) {
            for (int i : row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // 第二个方法找长度，unsorted list
    public static int findLongestArithmeticProgression(int[] nums) {
        if (nums == null) return 0;
        int n = nums.length;
        if (n <= 2) {
            return n;
        }
        int maxLen = 2;
        // dp[j][i]里面存的是以最后以i和j结尾的等差数列的长度
        int[][] dp = new int[n][n];
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) map.getOrDefault(nums[i], new ArrayList<>()).add(i);

        for (int i = 1; i < n; i++) {
            for (int secondLast = i - 1; secondLast >= 0; secondLast--) {
                int difference = nums[i] - nums[secondLast];
                // 等差数列中的上一个数
                int next = nums[secondLast] - difference;
                if (map.containsKey(next)) {
                    int nextIndex = -1;
                    for (int j = map.get(next).size() - 1; j >= 0; j--) {
                        if (map.get(next).get(j) < secondLast) {
                            nextIndex = map.get(next).get(j);
                            break;
                        }
                    }
                    // 找到了小于secondLast的nextIndex
                    if (nextIndex != -1) {
                        dp[secondLast][i] = dp[nextIndex][secondLast] + 1;
                        maxLen = Math.max(maxLen, dp[secondLast][i]);
                    }
                }
                if (dp[secondLast][i] == 0) dp[secondLast][i] = 2;
            }
        }
        return maxLen;
    }

    // 第三个方法print出等差数列
    public static List<Integer> printLongest(int[] nums) {
        List<Integer> result = new ArrayList<>();
        int n = nums.length;
        if (n <= 2) {
            for (int num : nums) {
                result.add(num);
            }
            return result;
        }
        int maxLen = 0;
        List<Integer>[][] length = new ArrayList[n][n];
        // map: value : list of indices
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) map.getOrDefault(nums[i], new ArrayList<>()).add(i);
        for (int i = 1; i < n; i++) {
            for (int secondLast = i - 1; secondLast >= 0; secondLast--) {
                int difference = nums[i] - nums[secondLast];
                int next = nums[secondLast] - difference;
                if (map.containsKey(next)) {
                    int nextIndex = -1;
                    for (int j = map.get(next).size() - 1; j >= 0; j--) {
                        if (map.get(next).get(j) < secondLast) {
                            nextIndex = map.get(next).get(j);
                            break;
                        }
                    }
                    if (nextIndex != -1) {
                        length[secondLast][i] = new ArrayList<Integer>(length[nextIndex][secondLast]);
                        length[secondLast][i].add(nums[i]);
                        if (maxLen <= length[secondLast][i].size()) {
                            result = length[secondLast][i];
                            maxLen = length[secondLast][i].size();
                        }
                    }
                }
                if (length[secondLast][i] == null) {
                    length[secondLast][i] = new ArrayList<>();
                    length[secondLast][i].add(nums[secondLast]);
                    length[secondLast][i].add(nums[i]);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1,3,7,6,5};
        System.out.println(findLongestArithmeticProgression(nums));
    }
}
