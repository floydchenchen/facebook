package tag;

/*
* 普通版本：two pointers
* two pointers
* 一个指针指向loop的current index(i),一个指针指向第一个0的index(j)
* 如果nums[i]不是0，swap nums[i]与nums[j]，j++
* */

import java.util.Arrays;

public class move_zeroes {
    public static void moveZeroes1(int[] nums) {
        int j = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != 0) {
                int temp = nums[j];
                nums[j] = nums[i];
                nums[i] = temp;
                j++;
            }
        }
    }

    /*
    * follow-up: minimize writes
    *
    * 代码只需要返回最后有效数组的长度，有效长度之外的数字是什么无所谓，原先input里面的数字不一定要保持原来的相对顺序。
    * 1.不用保持非零元素的相对顺序
    * 2.不用把0移到右边
    * 思路：把右边的非0元素移动到左边的0元素位置。这样就可以minimize writes.
    * 不需要去swap
    *
    * */
    public static int moveZeroesWithMinWrites(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            while (left < right && nums[left] != 0)		left++;
            while (left < right && nums[right] == 0)	right--;
            if (left < right)	nums[left++] = nums[right--];
        }
        System.out.println(Arrays.toString(nums));
        return left;
    }

    public static void main(String[] args) {
        int[] nums = {1,0,2,0,3,4,5,0,6,0};
        System.out.println(moveZeroesWithMinWrites(nums));
    }
}

