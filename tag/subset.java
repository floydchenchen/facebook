package tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class subset {

    /*
    *
    * 简单版: subsets: input set没有duplicates
    * Given a set of distinct integers, nums, return all possible subsets (the power set).
    * The solution set must not contain duplicate subsets.
    *
    * */
    public static List<List<Integer>> subsets1(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack1(list, new ArrayList<Integer>(), nums, 0);
        return list;
    }

    private static void backtrack1(List<List<Integer>> list, List<Integer> temp, int[] input, int start) {
        list.add(new ArrayList<Integer>(temp)); // !注意这里！不是add(temp),因为最早还没有initialize!
        for (int i = start; i < input.length; i++) {
            temp.add(input[i]);
            backtrack1(list, temp, input, i+1);
            temp.remove(temp.size()-1); //remove last, the process of backtrack
        }
    }

    /*
    *
    * subsets II: input set有duplicates
    * Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).
    * The solution set must not contain duplicate subsets.
    *
    * time: O(2^n)
    * space: O(n) for stack, O(2^n * n) for result
    * */
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        Arrays.sort(nums);
        backtrack2(list, new ArrayList<Integer>(), nums, 0);
        return list;
    }

    // pruning is needed compared to subset I
    private static void backtrack2(List<List<Integer>> list, List<Integer> temp, int[] input, int start) {
        list.add(new ArrayList<Integer>(temp));
        for (int i = start; i < input.length; i++) {
            // i > start!!!!!!
            if (i > start && input[i] == input[i-1]) continue; // skip duplicates
            temp.add(input[i]);
            backtrack2(list, temp, input, i+1);
            temp.remove(temp.size()-1);  // remove last, the process of backtracking

        }
    }


    // subset II iterative method
    // 1,2,2,3
    // res = (),| (1),| (2),(1,2),| (2,2),(1,2,2),| (3),(1,3),(2,3),(2,2,3),(1,2,2,3)
    public static List<List<Integer>> subsetsWithDup2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        result.add(new ArrayList<>());
        int size = 0, start = 0;
        for (int i = 0; i < nums.length; i++) { // num to insert
            start = (i > 0 && nums[i] == nums[i - 1]) ? size : 0; // prev result size
            size = result.size(); // current result size
            for (int j = start; j < size; j++) { // set to be inserted into
                List<Integer> tmp = new ArrayList<>(result.get(j)); // important!
                tmp.add(nums[i]);
                result.add(tmp);
            }
        }
        return result;
    }

    /*
    *
    * follow-up: 有个class，有个方法next(), 每次调用next()输出subsets中的下一个
    *
    *
    * */
    public List<Integer> next(List<List<Integer>> subset) {
        if (subset == null) return null;
        List<Integer> result = subset.get(0);
        subset.remove(0);
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,2,3};
        System.out.println(subsetsWithDup2(nums));
    }

}
