/*
*
* 84. Largest Rectangle in Histogram
* https://leetcode.com/problems/largest-rectangle-in-histogram/description/
* */

package 非tag;

import java.util.Stack;

public class largest_rectangle_in_histogram {
    // 当前柱子，怎么才算是组成了最大直方图面积呢？
    // 答：每个方柱计算只需要计算自己的最大直方图就可以了，
    // 不需要考虑比自己低的方柱，因为比自己低的方柱是由比自己低的方柱负责的！
    // 目标：找左边第一个的比它小，右边第一个比它小的
    // 为了更好的处理最后一个bar的情况，我们在实际中会插入一个高度为0的bar，这样就能pop出最后一个bar并计算了
    public static int largestRectangleArea(int[] histogram) {
        int len = histogram.length;
        Stack<Integer> stack = new Stack<Integer>();
        int maxArea = 0;
        for (int i = 0; i <= len; i++){
            int height = (i == len ? 0 : histogram[i]);
            // 如果有新的高度，push到stack上去
            if (stack.isEmpty() || height >= histogram[stack.peek()]){
                stack.push(i);
            } else {
                // 没有新高度，说明找到右边短边，这时候pop出来做计算
                int top = stack.pop();
                maxArea = Math.max(maxArea, histogram[top] * (stack.isEmpty() ? i : i - 1 - stack.peek()));
                i--; // 从右边最短边开始计算，利用右边最短边i与stack.peek()一直计算到最左边，来算面积
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        int[] histogram = {2,1,5,6,2,3};
        System.out.println(largestRectangleArea(histogram));
    }
}
