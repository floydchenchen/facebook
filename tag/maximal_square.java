/*
*
* 221. Maximal Square
* https://leetcode.com/problems/maximal-square/description/
*
* 题目描述：
* Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
*
* */


package tag;

import java.util.Stack;

public class maximal_square {

    /*
    * method 1: 2D DP, time: O(mn), space: O(mn)
    *
    * dp[i][j]代表着【【【【【从左上角到右下角(从0~i,从0~j)，以dp[i][j]为左下角的最大正方形】】】】】
    *
    *
    * dp[i][j] = (Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1])) + 1
    * 见 https://github.com/floydchenchen/facebook/blob/master/DOC/221_maximal_square.md
    * 每个地方的数字都代表着以这个地方为右下角的最大边长，所以必须要左、左上、上方都至少m-1的时候，
    * dp[i][j]才能达到(m-1) + 1 = m
    *
    * */
    public int maximalSquare1(char[][] matrix) {
        if (matrix.length == 0) return 0;
        int m = matrix.length, n = matrix[0].length, result = 0;
        int[][] dp = new int[m+1][n+1];
        for (int i = 1; i <= m ; i++) {
            for (int j = 1; j <= n; j++) {
                // check the upper left corner
                if (matrix[i-1][j-1] == '1') {
                    dp[i][j] = (Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1])) + 1;
                    result = Math.max(dp[i][j], result); // update result
                }
            }
        }
        return result * result;
    }

    /*
    *
    * method 2: 1D DP, time: O(mn), space: O(max(m,n))，优化为1D array
    * 注意到dp[i][j] = (Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1])) + 1，要更新dp[i][j]仅使用了三个其他数值，
    * 所以可以将2D dp变成1D：在Math.min(...)中用【【【dp[j]与dp[j-1]来代表dp[i-1][j]与dp[i-1][j-1]】】】
    * 引入corner与pre去存dp[i][j-1]. 相比method 1，在inner loop的if条件后面，多了一个else case，将dp[j]，即method 1中的dp[i][j]变为0，
    * 因为原来的2D array 本身就被initialize成了0，而method 2中，相当于dp[i][j]一开始是有dp[i-1][j]的值
    *
    * */
    public static int maximalSquare2(char[][] matrix) {
        if (matrix.length == 0) return 0;
        int m = matrix.length, n = matrix[0].length, result = 0, corner = 0, pre = 0;
        int[] dp = new int[n+1];
        for (int i = 1; i <= m ; i++) {
            corner = 0; // 相当于dp[i-1][0]
            for (int j = 1; j <= n; j++) {
                pre = dp[j]; // 相当于dp[i][j-1]
                // check the upper left corner
                if (matrix[i-1][j-1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j], dp[j-1]), pre) + 1;
                    result = Math.max(dp[j], result); // update result
                } else {
                    dp[j] = 0;
                }
                corner = pre; //此时corner相当于dp[i-1][j-1]
            }
        }
        return result * result;
    }

    public static void main(String[] args) {
        char[][] a = {"11100".toCharArray(), "11111".toCharArray(), "11111".toCharArray(), "10010".toCharArray()};
        System.out.println(maximalSquare2(a));
    }
}
