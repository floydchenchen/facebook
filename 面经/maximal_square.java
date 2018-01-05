public class maximal_square {
    public static int maximalSquare(char[][] a) {
        if(a.length == 0) return 0;
        int m = a.length, n = a[0].length, result = 0;
        int[][] b = new int[m+1][n+1];
        for (int i = 1 ; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if(a[i-1][j-1] == '1') {
                    b[i][j] = Math.min(Math.min(b[i][j-1] , b[i-1][j-1]), b[i-1][j]) + 1;
                    result = Math.max(b[i][j], result); // update result
                }
                System.out.println("b[i][j]: (" + i + "," + j +")");
                for (int p = 0; p <= m; p++) {
                    for (int q = 0; q<= n; q++) {
                        System.out.print(b[p][q] + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
        return result*result;
    }

//    public static int maximalSquare(char[][] matrix) {
//        if (matrix.length == 0) return 0;
//        int m = matrix.length, n = matrix[0].length, result = 0, temp1 = 0, temp2 = 0;
//        int[] dp = new int[n+1];
//        for (int i = 1; i <= m ; i++) {
//            temp1 = 0;
//            for (int j = 1; j <= n; j++) {
//                temp2 = dp[j];
//                // check the upper left corner
//                if (matrix[i-1][j-1] == '1') {
//                    dp[j] = Math.min(Math.min(dp[j], dp[j-1]), temp1) + 1;
//                    result = Math.max(dp[j], result); // update result
//                } else {
//                    dp[j] = 0;
//                }
//                temp1 = temp2;
//                for (int p = 0; p < dp.length; p++) {
//                    System.out.print(dp[p] + " ");
//                }
//                System.out.println();
//                System.out.println("temp1: " + temp1);
//                System.out.println();
//            }
//        }
//        return result * result;
//    }


    public static void main(String[] args) {
        char[][] a = {"11100".toCharArray(), "11111".toCharArray(), "11111".toCharArray(), "10010".toCharArray()};
        System.out.println(maximalSquare(a));
    }
}
