/*
*
* 334. Increasing Triplet Subsequence
* https://leetcode.com/problems/increasing-triplet-subsequence/
*
* Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.
* */

package tag;

public class increasing_triplet_subsequence {
    // two pointers
    // time: O(n), space: O(1)
    public boolean increasingTriplet(int[] nums) {
        int min = Integer.MAX_VALUE, secondMin = Integer.MAX_VALUE;
        for (int num : nums) {
            // <= 因为不能让secondMin = min
            if (num <= min) min = num;
            else if (num < secondMin) secondMin = num; // min < num < secondMin
            else if (num > secondMin) return true; // min < secondMin < num
        }
        return false;
    }
}
