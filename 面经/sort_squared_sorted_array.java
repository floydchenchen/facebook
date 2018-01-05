package 面经;/*
*
* 360. Sort Transformed Array
* https://leetcode.com/problems/sort-transformed-array/
*
* Given a sorted array of integers nums and integer values a, b and c.
* Apply a quadratic function of the form f(x) = ax^2 + bx + c to each element x in the array.
*
* nums = [-4, -2, 2, 4], a = 1, b = 3, c = 5, 即把nums里的每个值代入f(x) = x^2 + 3x + 5中
* Result: [3, 9, 15, 33]
* nums = [-4, -2, 2, 4], a = -1, b = 3, c = 5
* Result: [-23, -5, 1, 7]
*
* The returned array must be in sorted order.
* Expected time complexity: O(n)
*
*
* 思路：a > 0 时res从右往左填充 f(x) 值大的那个，a < 0 时从左往右填充 f(x) 值小的那个。
* use two pointers i, j and do a merge-sort like process.
* depending on sign of a, you may want to start from the beginning or end of the transformed array.
* For a == 0 case, it does not matter what b‘s sign is.
* */

import java.util.Arrays;

public class sort_squared_sorted_array {
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        int n = nums.length;
        int[] sorted = new int[n];
        int i = 0, j = n - 1;
        int index = a >= 0 ? n - 1 : 0;
        while (i <= j) {
            if (a >= 0) {
                // 因为index--，所以先填大的value
                sorted[index--] = getValue(nums[i], a, b, c) >= getValue(nums[j], a, b, c) ?
                        getValue(nums[i++], a, b, c) : getValue(nums[j--], a, b, c);
            } else {
                // 因为index++，所以先填小的value
                sorted[index++] = getValue(nums[i], a, b, c) >= getValue(nums[j], a, b, c) ?
                        getValue(nums[j--], a, b, c) : getValue(nums[i++], a, b, c);
            }
        }
        return sorted;
    }

    private int getValue(int x, int a, int b, int c) {
        return a * x * x + b * x + c;
    }

/*
* 简单版：对sorted array的数字平方后排序
*
* */
    public static int[] sortSquaredSortedArray(int[] nums) {
        int i = 0, j = nums.length - 1, index = j;
        int[] result = new int[nums.length];
        while (i <= j) {
            if (nums[i] * nums[i] >= nums[j] * nums[j]) result[index--] = nums[i] * nums[i++];
            else result[index--] = nums[j] * nums[j--];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {-4,-1,0,1,3,4,5};
        System.out.println(Arrays.toString(sortSquaredSortedArray(nums)));
    }
}
