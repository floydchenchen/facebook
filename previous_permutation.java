/*
*
* Previous Permutation
*
* 2764135 （找最后一个逆序41）
* 2764135 （找4后面比4小的最后一个数3）
* 2763145 （交换3,4的位置）
* 2763541 （把3后面的1,4,5反转）
* */

public class previous_permutation {

/*
* 31. Next Permutation
* https://leetcode.com/problems/next-permutation/
*
* 1. 2763541 （找最后一个正序35）
* 2. 2763541 （找3后面比3大的最后一个数4）
* 3. 2764531 （交换3,4的位置）
* 4. 2764135 （把4后面的5,3,1反转）
*
* */
    // leetcode 31, next permutation
    public void nextPermutation(int[] nums) {
        if (nums.length < 2)    return;
        int firstSmaller = nums.length - 2;
        // 1. 找从右往左第一个正序 / first smaller
        while (firstSmaller >= 0) {
            if (nums[firstSmaller] < nums[firstSmaller + 1])    break;
            firstSmaller--;
        }
        // 如果完全是逆序，那么让这个数变成正序[3,2,1 -> 1,2,3]
        if (firstSmaller == -1) {
            reverse(nums, 0, nums.length - 1);
            return;
        }
        // 2. 找firstSmaller右边第从右往左第一个比它大的数
        int firstLarger = nums.length - 1;
        while (firstLarger > firstSmaller) {
            if (nums[firstLarger] > nums[firstSmaller])    break;
            firstLarger--;
        }
        // 3. 交换两个数
        swap(nums, firstSmaller, firstLarger);
        // 4. 把firstSmaller之后的数reverse order
        reverse(nums, firstSmaller + 1, nums.length - 1);
        return;
    }
    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    private void reverse(int[] nums, int start, int end) {
        for (int i = start; i <= (start + end) / 2; i++)
            swap(nums, i, start + end - i);
    }

/*
* previous permutation
*
*
* 2764135 （找最后一个逆序41）

* 2763145 （交换3,4的位置）
* 2763541 （把3后面的1,4,5反转）
*
* */

    public void previousPermuation(int[] nums) {
        if (nums.length < 2)    return;
        int firstLarger = nums.length - 2;
        while (firstLarger >= 0) {
            if (nums[firstLarger] > nums[firstLarger + 1])    break;
            firstLarger--;
        }
        if (firstLarger == -1) {
            reverse(nums, 0, nums.length - 1);
            return;
        }
        int firstSmaller = nums.length - 1;
        while (firstSmaller > firstLarger) {
            if (nums[firstSmaller] < nums[firstLarger])    break;
            firstSmaller--;
        }
        swap(nums, firstLarger, firstSmaller);
        reverse(nums, firstLarger + 1, nums.length - 1);
    }
}
