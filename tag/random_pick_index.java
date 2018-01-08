/*
*
* 398. Random Pick Index
* https://leetcode.com/problems/random-pick-index/description/
*
* Given an array of integers with possible duplicates, randomly 【output the index】 of a given target number.
* You can assume that the given target number must exist in the array.
*
* */
package tag;

import java.util.Random;

public class random_pick_index {
    int[] nums;
    Random random;

    // constructor
    public random_pick_index(int[] nums) {
        this.nums = nums;
        this.random = new Random();
    }
    // 方法：reservoir sampling
    public int pick(int target) {
        int result = -1;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != target)
                continue;
            if (random.nextInt(++count) == 0)
                result = i;
        }

        return result;
    }

    /*
    *
    * 变形版：randomly return one of the maximal elements indices
    * 注意此题需要先找到cur max使其作为target同时更新res和count值。
    * 在求cur max时注意用"if (max == Integer.MIN_VALUE || nums[i] > max)"，因为数组中可能有Integer.MIN_VALUE
    *
    * */
    public class Solution {
        int[] nums;
        Random random;
        public Solution(int[] nums) {
            this.nums = nums;
            random = new Random();
        }
        public int pick(int target) {
            int max = Integer.MIN_VALUE;
            int res = -1, count = 0;
            for (int i = 0; i < nums.length; i++)
                if (max == Integer.MIN_VALUE || nums[i] > max) { //1st time meet the cur max, update res & count
                    max = nums[i];
                    res = i;
                    count = 1;
                } else if (nums[i] == max) {
                    if (random.nextInt(++count) == 0) //later meet max, randomly pick
                        res = i;
                }
            return res;
        }
    }

}
