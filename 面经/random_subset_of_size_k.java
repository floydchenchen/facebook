package 面经;/*
* Random subset of size K
* 每个element最多出现一次
* */

import java.util.Arrays;
import java.util.Random;

public class random_subset_of_size_k {
    // reservoir sampling, applicable when the size is unknown
    public static int[] selectKItems(int[] nums, int k){
        Random r = new Random();
        int[] result = new int[k];
        // 先保留前k个
        for (int i = 0; i < k; i++)
            result[i] = nums[i];
        // k+1到nums.length时，随机生成一个0到i的数，如果j < k，就替换掉
        for (int i = k; i < nums.length; i++) {
            int j = r.nextInt(i + 1);
            if (j < k)
                result[j] = nums[i];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4};
        System.out.println(Arrays.toString(selectKItems(nums, 3)));
    }
}
