/*
*
* 题目描述：
* Given an array with n objects colored red, white or blue, sort them so that objects of the same color are adjacent,
* with the colors in the order red, white and blue.
* Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
*
* 思路
* 2 pointer: 把2往后放，把0往前放，不管1
* one pass
* O(1) space
*
* */

package tag;

import java.util.Arrays;

public class sort_colors {
    // two pointer solution, one pass, O(1) space
    public void sortColors(int[] nums) {
        // 用start记录0，end记录2
        int start = 0, end = nums.length - 1, index = 0;
        // 注意是<=
        while (index <= end) {
            if (nums[index] == 0) {
                nums[index] = nums[start];
                nums[start] = 0;
                start++;
            }
            else if (nums[index] == 2) {
                nums[index] = nums[end];
                nums[end] = 2;
                end--;
                // since the value of nums[end] is not guaranteed (could be like 2)
                // and index is before end，所以需要index--，重新扫这一个位置
                // 这里有可能会被问！
                index--;
            }
            index++;
        }
    }

    /*
    *
    * follow-up: sort k colors
    *
    * 如果这个时候有K个category， 应该怎么办？
    * 顺着上一题的思路，我的想法是将（0,1，。。。，k-1） category 分成（0）--> L, (1, k-2) -->M, (k-1) --> H，
    * 然后相同的思想继续call之前的function，
    * 然后reduce为 （1，k-2）的range，重复之前的事情
    * 之前的sortCategory也可以处理只有两种Category的case，不用担心， 直接call
    *
    * time: roughly O(n), since every iteration the range between start and end has shrink by 2n/k,
    * (assume colors are quite average), if the average number of each colors is close to 1, then the
    * time would be near O(n^2), we could just sort the colors to make the time O(nlgn)
    *
    * space: O(1)
    *
    * */

    // 用两个全局变量来存下一次call helper method的start和end的位置，注意要先在主方法中改一下end的值
    static int start = 0, end = Integer.MAX_VALUE;
    public static void sortKColors(int[] nums, int k) {
        end = nums.length - 1;
        int color1 = 0, color2 = k - 1;
        while (start < end) {
            helper(nums, color1, color2);
            color1++;
            color2--;
        }
    }
    private static void helper(int[] nums, int color1, int color2) {
        int index = start;
        // 注意是<=
        while (index < end) {
            if (nums[index] == color1) {
                nums[index] = nums[start];
                nums[start] = color1;
                start++;
            }
            else if (nums[index] == color2) {
                nums[index] = nums[end];
                nums[end] = color2;
                end--;
                // since the value of nums[end] is not guaranteed (could be like 2)
                // and index is before end，所以需要index--，重新扫这一个位置
                // 这里有可能会被问！
                index--;
            }
            index++;
        }
    }

    public static void main(String[] args) {
        int[] nums = {3,1,2,0,3,2,0,0,3,1,3,2,0};
        sortKColors(nums, 4);
        System.out.println(Arrays.toString(nums));
    }
}
