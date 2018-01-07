# 221. Maximal Square (medium)

##### 说明

* Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
* example:

1 0 1 0 0
1 0 **1 1** 1
1 1 **1 1** 1
1 0 0 1 0
return 4.

##### 分析

* method 1: DP
    * time: O(mn)
    * space: O(mn)
* method 2: DP
    * time O(mn)
    * space: O(max(m,n))

##### 思路

* DP
* method 1:
    * 根据`dp[i][j] = (Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1])) + 1;`比如下图的蓝色的2（这个位置`dp[i][j]`为2），因为因为左（`dp[i][j-1]`），上（`dp[i-1][j]`），左上（`dp[i-1][j-1]`）都是1（注意三个红色的圈）

![square](https://lh3.googleusercontent.com/bsgJo400bLNiMs23tzqHIlux5VaO0Acf5RSo9aQmeHyWm3HAfJoHN-PlqXXBDKtParT5RuzSfGfASw70lAElqxw7HwyY5apTFYpbElf6TdcgDVwpWQpaXvDHnlmGQOn4Y-4y5-08UjZV1U97sX-1G2UpDZqgzB85VBSRPN_5ovBrsBGBQWUkvvVYLVfXp25w3ZoX9PixWjxDNiSkqC9yKWQHSSjgzhxIVarJ_nzwp7Xn_5tw9rzp1bsr5FL3zF6BGi5g9eA_xjvfJpzmM77fseIE2SxcquxKj5_R22ES7g4qQkCUYgzdyG_7VF1Rd8sB_0Wx7glvCFsDsob0Zk1EYRcluntZL_8B-nPXkNIaM2eQ9VB2CqiVJR1L0_DzoYmqdyz_myRYIzNvkyuad0qEHX6U32KcYr6y4w7n2I8RhzP3TNhW_fkZdy9PpnG-1Mu0mJv-HYtoVEQG9MXEs55pq--8dsI9_SeIcPHKts7r_uhtB_vdlmSpWnNOr8x4IavJk0AeKm5Js557JoDnqI9lERUOXHBLdgJF8IgDFno7EWXk8Ep-B0geZwn3XOHEx9eGCzg6K8oPEOomFAPINmk_6o23Jka_v84EFqnK9lvB=w240-h216-no)

* method 2: 优化DP到O(n)
    * 注意到`dp[i][j] = (Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1])) + 1`，要更新`dp[i][j]`仅使用了三个其他数值，所以可以将2D dp变成1D：在`Math.min(...)`中用`dp[j]`与`dp[j-1]`来代表`dp[i-1][j]`与`dp[i-1][j-1]`，引入**temp1与temp2去存`dp[i][j-1]`**
    * 相比method 1，在inner loop的if条件后面，**多了一个else case**，将dp[j]，即method 1中的dp[i][j]变为0，因为原来的2D array 本身就被initialize成了0，而method 2中，相当于`dp[i][j]`一开始是有`dp[i-1][j]`的值

```java
class Solution {
    public int maximalSquare(char[][] matrix) {
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
}
```

method 2:

```java
class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0) return 0;
        // 用temp1, temp2去存dp[i][j-1] 
        int m = matrix.length, n = matrix[0].length, result = 0, temp1 = 0, temp2 = 0;
        int[] dp = new int[n+1];
        for (int i = 1; i <= m ; i++) {
            temp1 = 0;
            for (int j = 1; j <= n; j++) {
                temp2 = dp[j];// 相当于存dp[i][j]
                // check the upper left corner
                if (matrix[i-1][j-1] == '1') {
                    // Math.min中的dp[j]与dp[j-1]相当于dp[i-1][j]与dp[i-1][j-1]
                    dp[j] = Math.min(Math.min(dp[j], dp[j-1]), temp1) + 1;
                    result = Math.max(dp[j], result); // update result
                } else {
                    dp[j] = 0;
                }
                temp1 = temp2; // 在下一个loop的时候，temp1相当于dp[i][j-1]
            }
        }
        return result * result;
    }
}
```

