/*
*
* 217. Contains Duplicate
* https://leetcode.com/problems/contains-duplicate/description/
*
* */

package 非tag;

import java.util.HashSet;
import java.util.Set;

public class contains_duplicate {

    // 检查这个array是否有duplicates
    // hash set, time: O(n), space: O(n)
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.contains(num)) set.add(num);
            else return true;
        }
        return false;
    }

    // 给一个array, 然后给一个k, 让你check 连续的k个integer是否含有dulplicate
    public boolean containsDuplicateRangeK(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > k) set.remove(nums[i - k - 1]); // 在for loop开头的时候去除掉多的那一个
            if (!set.add(nums[i])) return true;
        }
        return false;
    }

}
