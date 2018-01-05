/*
*
* 给一个 words = ["a", "b", "c"], 一个 weights = [1, 2, 3] 然后写一个function按照对应的weight来生成word
* 比如如果生成600次，a 大致出现100次，b 大致200次， c大致300次.
* 做完之后他再让你生成n次再统计输出一下结果.

* */

import java.util.Random;

public class random_return_number_according_to_weight {
    private static int randomNumber(int[] weights, int[] words) {
        if (weights == null || weights.length == 0) return 0;
        int n = weights.length;
        for (int i = 1; i < n; i++)
            weights[i] += weights[i - 1];//[1,2,4,5,1,3] -> [1,3,7,12,13,16]
        Random rand = new Random();
        int target = rand.nextInt(weights[n - 1]);//num is from 0 to 15
        return binarySearch(weights, target, 0, weights.length - 1);
    }
    // binary search on weights: O(lgn)
    private static int binarySearch(int[] weights, int target, int start, int end) {
        if (weights[start] >= target) return start;
        int mid = start + (end - start) / 2;
        // only two elements
        if (mid < 1) return weights[start] >= target ? start : end;
        if (weights[mid - 1] < target && weights[mid] >= target) return mid;
        if (weights[mid - 1] > target) return binarySearch(weights, target, start, mid - 1);
        else return binarySearch(weights, target, mid + 1, end);
    }

    public static void main(String[] args) {
        int[] weights = {1,2,3,4,5,6,7,8};
        int[] words = {1,2,3,4,5,6,7,8};
        System.out.println(randomNumber(weights, words));
    }

}
