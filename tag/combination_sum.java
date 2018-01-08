/*
*
* combination sum IV
* https://leetcode.com/problems/combination-sum-iv/description/
*
* */

package tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class combination_sum {
    /*
    * combination sum I
    * solution set 中 candidates 可以重复使用
    * remain: target减去nums中的元素还剩什么
    * int start, 在backtrack的时候，start = i, 因为可以在solution中re-use元素
    *
    * time: O(n2^n)
    * */
    public List<List<Integer>> combinationSum1(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack1(list, new ArrayList<>(), nums, target, 0);
        return list;
    }
    public void backtrack1(List<List<Integer>> list, List<Integer> tempList, int [] nums, int remain, int start) {
        if (remain < 0) return;
        if (remain == 0) list.add(new ArrayList(tempList));
        for (int i = start; i < nums.length; i++) {
            tempList.add(nums[i]);
            backtrack1(list, tempList, nums, remain - nums[i], i); // start = i, because we can re-use
            tempList.remove(tempList.size() - 1);
        }
    }

    /*
    * combination sum II
    * solution set 中 candidates 不能重复使用
    * int start, 在backtrack的时候，start = i+1, 因为可以在solution中不可以重复使用元素
    * for-loop中多了一行去skip duplicates: if (i > start && nums[i] == nums[i-1]) continue
    *
    * time: O(n2^n)
    * */
    public List<List<Integer>> combinationSum2(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack2(list, new ArrayList<>(), nums , target, 0);
        return list;
    }
    public void backtrack2(List<List<Integer>> list, List<Integer> tempList, int [] nums, int remain, int start) {
        if (remain < 0) return;
        if (remain == 0) list.add(new ArrayList(tempList));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i-1]) continue; // skip duplicates !!
            tempList.add(nums[i]);
            backtrack2(list, tempList, nums, remain - nums[i], i+1); // i + 1!!!
            tempList.remove(tempList.size() - 1);
        }
    }

    /*
    * combination sum III
    * given only number from 1 to 9（即没有input set），candidates可以重复使用
    * 加入了一个新的k值规定每个solution set的size
    * 因为没有input set，所以通过 if (tempList.size() == k && target == 0) 判断，把tempList加入list并且return
    *
    * time: O(n2^n)
    * */
    public List<List<Integer>> combinationSum3(int k, int target) {
        List<List<Integer>> list = new ArrayList<>();
        backtrack3(list, new ArrayList<>(), k, target, 1); // 因为没有0，所以start从1开始
        return list;
    }

    public void backtrack3(List<List<Integer>> list, List<Integer> tempList, int k, int remain, int start) {
        if (tempList.size() == k && remain == 0) { // 通过这个条件判断return与加入list
            list.add(new ArrayList(tempList));
            return;
        }
        for (int i = start; i <= 9; i++) { //到9就结束
            tempList.add(i);
            backtrack3(list, tempList, k, remain - i, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

    /*
    * combination sum IV
    * find the number of possible combinations that add up to a positive integer target.
    * 只需要求有多少个，考虑用DP
    * */

    // 超时的recursive解法
    public int combinationSum4Recursive(int[] nums, int target) {
        if (target == 0) return 1;
        int result = 0;
        for (int num : nums) {
            if (target >= num) result += combinationSum4Recursive(nums, target - num);
        }
        return result;
    }

    // top-down DP
    // dp[i] 到i（0 ~ target）的时候有多少个不同的combination加起来等于i
    private static int[] dp;
    public static int combinationSum4DPTopDown(int[] nums, int target) {
        dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1; // one way to get zero, by using 0
        return helper(nums, target);
    }
    // top-down DP solution
    // 举例：comb[4] = comb[4-1] + comb[4-2] + comb[4-3] = comb[3] + comb[2] + comb[1]
    // 即n个的combination数量 = 前面所有的从1到n-1的combination数量之和
    // 这个方法完全通过target和nums[i]的关系去一个一个算dp[i]，所以比较慢
    private static int helper(int[] nums, int target) {
        // 判断dp[target]是否有计算过
        if (dp[target] != -1) return dp[target]; // base case, 递归什么时候退出
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target >= nums[i]) result += helper(nums, target - nums[i]);
        }
        printDP(dp);
        dp[target] = result;
        return result;
    }

    // bottom-up DP
    // dp[i] 到i（0 ~ target）的时候有多少个不同的combination加起来等于i
    // time: O(kn), where k is the target and n is the size of the array
    // space: O(k), the size of the target
    // 这个方法直接在算的时候利用了之前积累的dp[i - nums[j]]的值去算，所以快
    public static int combinationSum4BottomUp(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1; // one way to get zero, by using 0
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i - nums[j] >= 0) dp[i] += dp[i - nums[j]];
            }
            printDP(dp);
        }
        return dp[target];
    }

    private static void printDP(int[] dp) {
        for(int i : dp) System.out.print(i + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3};
        System.out.println(combinationSum4BottomUp(nums, 4));
    }
}
