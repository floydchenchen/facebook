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
            Stack<Integer> stack = new Stack<Integer>();
            for (int col = 0; col <= n; col++) {
                // initilialize the "histogram"
                if (col < n) {
                    if (matrix[row][col] == '1') histogram[col] += 1;
                    else histogram[col] = 0;
                }

                if (stack.isEmpty() || histogram[col] >= histogram[stack.peek()]) {
                    stack.push(col);
                } else {
                    // 这里不能像Problem 84一样直接用col--，因为我们每次都会改变histogram[col]的值
                    // 如果maintain一个flag[] 感觉又比较麻烦
                    // 这里手动有一个while loop做计算之后，再手动把col push回stack，相当于还是从右边最短边开始
                    while (!stack.isEmpty() && histogram[col] < histogram[stack.peek()]) {
                        int top = stack.pop();
                        maxArea = Math.max(maxArea, histogram[top] * (stack.isEmpty() ? col : col - 1 - stack.peek()));
                    }
                    stack.push(col);

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

