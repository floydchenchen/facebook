/*
*
* maximal square变种题
* 85. Maximal Rectangle
* https://leetcode.com/problems/maximal-rectangle/description/
*
* DP太难了。。
*
* 用Problem 84的histogram的解法
*
* 和84不一样的地方：
* 这个是一行一行计算maxArea，而84只有一样，所以runtime从O(n)变成了O(mn)
* 而且最后不能用row--，因为这样会改变histogram[col]，所以用一个while loop来计算maxArea，最后再手动把col push回heightStack。
*
*
* histogram的思路
*
* heightStack
* 当前柱子，怎么才算是组成了最大直方图面积呢？
* 每个方柱计算只需要计算自己的最大直方图就可以了，不需要考虑比自己低的方柱，因为比自己低的方柱的最大直方图是由比自己低的方柱负责的！
* 目标：找左边第一个的比它小，右边第一个比它小的
* 为了更好的处理最后一个bar的情况（最后一个bar右边高度为0），我们在实际中会插入一个高度为0的bar，这样就能pop出最后一个bar并计算了
* 如果有新的高度，push到heightStack上去，否则pop出最高的高度来做计算，利用i--来维持右边最短边，
* 利用被pop了只有的heightStack的heightStack.peek()直到找到左边最短边，这时计算的面积为：pop出来的目前最高height，乘以i - 1 - heightStack.peek()
*
*
* */

package tag;

import java.util.Arrays;
import java.util.Stack;

public class maximal_rectangle {
    public static int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int m = matrix.length, n = matrix[0].length, maxArea = 0;
        // 同Problem 84中largest rectangle in histogram一样，加一个column去考虑最后一个bar的情况
        int[] histogram = new int[n+1];

        for (int row = 0; row < m; row++) {
            // stack存的是height的index
            Stack<Integer> heightStack = new Stack<>();
            // 为了更好的处理最后一个bar的情况（最后一个bar右边高度为0），我们在实际中会插入一个高度为0的bar，这样就能pop出最后一个bar并计算了
            // 所以col <= n
            // "因为我们每次都会改变histogram[col]的值"
            for (int col = 0; col <= n; col++) {
                // initialize the "histogram"
                // 下面说的"因为我们每次都会改变histogram[col]的值"
                // 所以我们不能再次跑一遍这个for loop，只能用while loop 一直pop 出最高的height的index
                if (col < n) {
                    if (matrix[row][col] == '1') histogram[col] += 1;
                    else histogram[col] = 0;
                }
                // 如果有新的高度或者heightStack空了，push这个height的index到heightStack上去
                if (heightStack.isEmpty() || histogram[col] >= histogram[heightStack.peek()]) {
                    heightStack.push(col);
                } else {
                    // 没有新高度，说明找到右边短边
                    // 这里不能像Problem 84一样直接用col--，【【因为我们每次都会改变histogram[col]的值】】
                    // 如果maintain一个flag[] 感觉又比较麻烦
                    // 这里手动用一个while loop做计算之后，再手动把col push回heightStack，相当于还是从右边最短边开始
                    while (!heightStack.isEmpty() && histogram[col] < histogram[heightStack.peek()]) {
                        int top = heightStack.pop();
                        // histogram[top]是高度，col是右边index，col - 1 - heightStack.peek()是左边index
                        maxArea = Math.max(maxArea, histogram[top] * (heightStack.isEmpty() ? col : col - 1 - heightStack.peek()));
                    }
                    // 我们还需要col来做接下来的运算
                    heightStack.push(col);

                }
                System.out.println("row: " + row + ", " + Arrays.toString(histogram));
                System.out.println("maxArea: " + maxArea);
                System.out.println();
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        char[][] matrix = {"10100".toCharArray(), "10111".toCharArray(), "11111".toCharArray(), "10010".toCharArray()};
        System.out.println(maximalRectangle(matrix));
    }
}

