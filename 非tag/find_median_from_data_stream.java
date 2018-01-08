/*
*
* 295. Find Median from Data Stream
* https://leetcode.com/problems/find-median-from-data-stream/description/
*
* Design a data structure that supports the following two operations:
*   void addNum(int num) - Add a integer number from the data stream to the data structure.
*   double findMedian() - Return the median of all elements so far.
*
* 思路：
* data stream，直接就联想到了heap
*
* 用两个heap
*   min heap代表larger half
*   max heap代表smaller half
*   让smaller half(max)的size >= larger half(min)的size
*
* 找median的时候
*   if min.size == max.size, return从两个heap poll的平均值
*   否则return 从smaller half里peek的值
*
* */

package 非tag;

import java.util.PriorityQueue;

public class find_median_from_data_stream {
    // max heap's size is always >= min heap's size
    // max heap maintains smaller half; min heap maintains larger half
    PriorityQueue<Integer> min = new PriorityQueue<>();
    PriorityQueue<Integer> max = new PriorityQueue<>((a,b) -> b - a);

    // Adds a number into the data structure.
    // 先加到max(smaller half)去，再从max(smaller half)中poll出来的最大值，加到min(larger half)去
    // time: O(lgn)
    public void addNum(int num) {
        max.offer(num);
        min.offer(max.poll());
        // 如果操作过后max size < min size，从min(larger half)poll出来加到max(smaller half)中去
        if (max.size() < min.size()){
            max.offer(min.poll());
        }
    }

    // Returns the median of current data stream
    // time: O(1)
    public double findMedian() {
        if (max.size() == min.size()) return (max.peek() + min.peek()) /  2.0;
        else return max.peek();
    }

}
