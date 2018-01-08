/*
*
* 33. Search in Rotated Sorted Array
* https://leetcode.com/problems/search-in-rotated-sorted-array/description/
*
* Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
* (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2), pivot is 4
* You are given a target value to search. If found in the array return its index, otherwise return -1.
* You may assume no duplicate exists in the array.
*
* */

package tag;

public class search_in_rotated_sorted_array {
    // binary search
    // time: O(lgn), space: O(1)
    // break the problem into left and right sorted array. Every left element > right element
    // 分别对左边和右边的array进行binary search
    public static int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        int start = 0, end = nums.length - 1;

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // if mid is in left array，因为左边都大于右边，然后nums[mid]比左边最小的大
            if (nums[start] <= nums[mid]) {
                // left: start ... target ... mid ... end_l
                if (nums[mid] >= target && nums[start] <= target) end = mid;
                    // left: start  ... mid ... target ... end_l
                else start = mid;
            }
            // if mid is in right array，同理nums[mid]比右边最大的小
            else if (nums[mid] <= nums[end]) {
                // right: start_r  ... mid ... target ... end
                if (nums[mid] < target && nums[end] >= target) start = mid;
                    // right: start_r ... target ... mid ... end
                else end = mid;
            }
        }
        if (nums[start] == target) return start;
        if (nums[end] == target) return end;
        return -1;
    }

    /*
    *
    * follow-up:
    * What if duplicates are allowed?
    * Would this affect the run-time complexity? How and why?
    * */

    public static boolean search2(int[] nums, int target) {
        if (nums == null || nums.length == 0) return false;
        int start  = 0, end = nums.length - 1;
        while(start + 1 < end){
            int mid = start + (end - start) / 2;
            if(nums[mid] == target) return true;
            // 有序，如果nums[start] < nums[end]，说明它们中间的区间是有序的，直接进行binary search
            if (nums[start] < nums[end]) {
                if (target > nums[mid]) start = mid;
                if (target < nums[mid]) end = mid;
            }
            else if(nums[start] > nums[end]) {
                // 两段
                if (nums[mid] >= nums[start]) { // mid 在左边
                    // start ... mid ... l_end
                    if (target > nums[mid] || target < nums[start]) {
                        // start ... mid ... target ... l_end
                        // 或者 start ... mid ... l_end ... target
                        start = mid;
                    } else {
                        // start ... target ... mid ... l_end
                        end = mid;
                    }
                } else { // mid在右边
                    // start ... l_end ... r_start ... mid ... end
                    if (target < nums[mid] || target > nums[end]) {
                        // start ... l_end ... r_start ... target ... mid ... end
                        // 或者 start ... target ... l_end ... r_start ... mid ... end
                        end = mid;
                    } else {
                        // start ... l_end ... r_start  ... mid ... target ... end
                        start = mid;
                    }
                }
            }
            else {
                start++;
            }
//            System.out.println("start end target" + start + end + target);
        }
        if (nums[start] == target || nums[end] == target) return true;
        return false;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,0};
        System.out.println(search2(nums,3));
    }
}
