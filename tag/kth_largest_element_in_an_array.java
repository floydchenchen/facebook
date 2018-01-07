/*
*
* 215. Kth Largest Element in an Array
* https://leetcode.com/problems/kth-largest-element-in-an-array/description/
*
* Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.
* For example, Given [3,2,1,5,6,4] and k = 2, return 5.
*
* */

package tag;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class kth_largest_element_in_an_array {
    // method 1: heap
    // time: O(nlogk)， 因为需要存k个值进去， space: O(n)
    public int findKthLargest1(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int val : nums) {
            minHeap.offer(val);
            if(minHeap.size() > k) minHeap.poll();
        }
        return minHeap.peek();
    }

    // method 2: sorting
    // time: O(nlgn), space: O(1)
    public int findKthLargest2(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        return nums[n - k];
    }

    // method 3: quick select
    // time: average O(n), worst O(n^2)
    // 时间分析: O(n) average, the problem is reduced to approximately half of its original size, giving the recursion
    // T(n) = T(n/2) + O(n).
    // O(n^2) worst case, the recursion may become T(n) = T(n - 1) + O(n)
    // space: O(1)
    // quick select
    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0) return Integer.MAX_VALUE;
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    // quick select: kth smallest
    public int quickSelect(int[] nums, int start, int end, int k) {
        if (start > end) return Integer.MAX_VALUE;

        int pivot = nums[end]; // Take A[end] as the pivot
        int leftIndex = start; // index for left partition
        for (int i = start; i < end; i++) {
            // Put numbers < pivot to pivot's left partition
            if (nums[i] <= pivot) swap(nums, leftIndex++, i);
        }
        // Finally, swap A[end] with A[leftIndex], to place the pivot in the middle
        swap(nums, leftIndex, end);

        // Found kth smallest number
        if (leftIndex == k) return nums[leftIndex];
            // Check right partition
        else if (leftIndex < k) return quickSelect(nums, leftIndex + 1, end, k);
            // Check left partition
        else return quickSelect(nums, start, leftIndex - 1, k);
    }

    void swap(int[] A, int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }


    /*
    *
    * follow-up: 当原始数据很大内存装不下的时候该怎么解？
    * https://www.jiuzhang.com/qa/5496/
    * 1. 最简单的是维护一个size为k的heap，而不是size为n
    * 2. 通过sorting, 外部排序，不占用内存
    * 3. 将问题reduce为find k largest/smallest from n arrays
    *   见: https://github.com/floydchenchen/facebook/blob/master/%E9%9D%A2%E7%BB%8F/find_k_smallest_from_n_sorted_arrays.java
    *
    * */
}
