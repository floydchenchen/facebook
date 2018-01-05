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

    public static void main(String[] args) {
        int[] nums = {1,0,87,0,7,90,7,0,7,0};
        moveZeroes1(nums);
        System.out.println(Arrays.toString(nums));
    }
}

