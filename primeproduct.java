import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class primeproduct {

    public static List<Integer> primeProducts(int[] nums) {
        List<Integer> list = new ArrayList<>();
        // to do
        backtrack(list, nums, 0, 1);
        return list;
    }

    private static void backtrack(List<Integer> list, int[] nums, int start, int temp) {
        if (temp != 1) list.add(temp);
        for (int i = start; i < nums.length; i++) {
            temp *= nums[i];
            backtrack(list, nums, start+1, temp);
            temp /= nums[i];
        }
    }

    public static void main(String[] args) {
        int[] nums = {2,3};
        List<Integer> l = primeProducts(nums);
        System.out.println(l);
    }
}