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

public class maximal_square {

    /*
    * method 1: 2D DP, time: O(mn), space: O(mn)
    *
    * dp[i][j]代表着从左上角到右下角(从0~i,从0~j)最大的正方形的边长是多少
    * dp[i][j] = (Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1])) + 1
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

    // method 2: 1D DP, time: O(mn), space: O(max(m,n))，优化为1D array
    public static int maximalSquare2(char[][] matrix) {
        if (matrix.length == 0) return 0;
        int m = matrix.length, n = matrix[0].length, result = 0, temp1 = 0, temp2 = 0;
        int[] dp = new int[n+1];
        for (int i = 1; i <= m ; i++) {
            temp1 = 0;
            for (int j = 1; j <= n; j++) {
                temp2 = dp[j];
                // check the upper left corner
                if (matrix[i-1][j-1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j], dp[j-1]), temp1) + 1;
                    result = Math.max(dp[j], result); // update result
                } else {
                    dp[j] = 0;
                }
                temp1 = temp2;
                for (int p = 0; p < dp.length; p++) {
                    System.out.print(dp[p] + " ");
                }
                System.out.println();
                System.out.println("temp1: " + temp1);
                System.out.println();
            }
        }
        return result * result;
    }


    public static void main(String[] args) {
        char[][] a = {"11100".toCharArray(), "11111".toCharArray(), "11111".toCharArray(), "10010".toCharArray()};
        System.out.println(maximalSquare2(a));
    }
}
