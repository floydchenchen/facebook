/*
*
* 161. One Edit Distance
* https://leetcode.com/problems/one-edit-distance/
* 题目描述：Given two strings S and T, determine if they are both one edit distance apart.
* One edit means remove/add/change 1 character.
*
* 思路：
* There're 3 possibilities to satisfy one edit distance apart:
* 1) Replace 1 char:
*      s: a B c
*      t: a D c
*
* 2) Delete 1 char from s:
*      s: a D  b c
*      t: a    b c
*
* 3) Delete 1 char from t
*      s: a   b c
*      t: a D b c
* */

package tag;

import java.util.Arrays;

public class one_edit_distance {
    // add, remove, change
    public boolean isOneEditDistance(String s, String t) {
        // 只需要iterate through the shorter string
        for (int i = 0; i < Math.min(s.length(), t.length()); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                // if same length, only change is possible
                if (s.length() == t.length()) {
                    return s.substring(i + 1).equals(t.substring(i + 1));
                }
                // s add one char to equal t
                else if (s.length() < t.length()) {
                    return s.substring(i).equals(t.substring(i + 1));
                }
                // s remove one char to equal t
                else {
                    return s.substring(i + 1).equals(t.substring(i));
                }
            }
        }
        //All previous chars are the same, the only possibility is deleting the end char in the longer one of s and t
        return Math.abs(s.length() - t.length()) == 1;
    }

    /*
    *
    * follow-up: 原题 但是不能用substring ====>>>   要一个字符一个字符比较
    *
    * */
    public static boolean isOneEditDistance2(char[] s, char[] t) {
        for (int i = 0; i < Math.min(s.length, t.length); i++) {
            if (s[i] != t[i]) {
                // if same length, only change is possible
                if (s.length == t.length) {
                    return equals(s, i+1, t, i+1);
                }
                // s add one char to equal t
                else if (s.length < t.length) {
                    return equals(s, i, t, i+1);
                }
                // s remove one char to equal t
                else {
                    return equals(s, i+1, t, i);
                }
            }
        }
        //All previous chars are the same, the only possibility is deleting the end char in the longer one of s and t
        return Math.abs(s.length - t.length) == 1;
    }

    private static boolean equals(char[] s, int i, char[] t, int j) {
        if (s.length - i != t.length - j) return false;
        for (int index = 0; index < s.length - i; index++) {
            if (s[i + index] != t[j + index]) return false;
        }
        return true;
    }


    /*
    *
    * 72. Edit Distance
    * Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2.
    * (each operation is counted as 1 step.)
    * 返回最少几步convert成第二个数
    *
    * 思路：
    * 二维DP。动态数组dp[word1.length+1][word2.length+1], dp[i][j]表示从word1前i个字符转换到word2前j个字符最少的步骤数。
    * 假设word1现在遍历到字符x(index: i)，word2遍历到字符y(index: j). 以下两种可能性：
    * 1. x==y，那么不用做任何编辑操作，所以dp[i][j] = dp[i-1][j-1]
	* 2. x != y
	*   (1) 在word1中删除x， 那么dp[i][j] = dp[i-1][j] +leng 1
	*   (2) 在word2删除y， 那么dp[i][j] = dp[i][j-1] + 1
	*   (3) 把word1中的x用y来替换，那么dp[i][j] = dp[i-1][j-1] + 1
	* 最少的步骤就是取这三个中的最小值。
	* 最后返回 dp[word1.length+1][word2.length+1] 即可。
    * */

    // time: O(mn), space: O(mn)
    // dp[i][j]: 从word1前i个字符转换到word2前j个字符最少的步骤数
    // 以word1:ab, word2:abc为例
    public static int minDistance1(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        // initialize, 相当于每次都删一个，删到0
        for (int i = 0; i < len1 + 1; i++)
            dp[i][0] = i;
        for (int j = 0; j < len2 + 1; j++)
            dp[0][j] = j;
        printDP(dp);
//        0 1 2 3
//        1 0 0 0
//        2 0 0 0
        for (int i = 1; i < len1 + 1; i++) {
            for (int j = 1; j < len2 + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else {
                    int replace = dp[i - 1][j - 1] + 1;
                    int deleteWord1 = dp[i - 1][j] + 1; // delete减少一个word1的字母，所以i-1
                    int deleteWord2 = dp[i][j - 1] + 1; // insert相当于减少一个word2的字母，所以j-1
                    dp[i][j] = Math.min(replace, Math.min(deleteWord1, deleteWord2));
                }
                printDP(dp);
            }
        }
        return dp[len1][len2];
    }

    /*
    *
    * 优化： 1D array
    *
    * */
    public static int minDistance2(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int[] dp = new int[len2 + 1];
        // initialize dp
        for (int j = 0; j <= len2; j++) dp[j] = j;
        System.out.println(Arrays.toString(dp));

        // dp process
        for (int i = 1; i <= len1; i++) {
            // corner: dp[i-1][j-1], pre: dp[i][j-1], dp[j] = dp[i-1][j]
            int pre = i, corner = i - 1;
            for(int j = 1; j <= len2; j++){
                // temp是存corner，或者dp[i-1][j-1]的值
                int temp = corner;
                // 对接下来的j+1, corner = dp[i-1][j]
                corner = dp[j];
                // 不操作：temp = temp, replace: temp += 1
                temp += (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1);
                // deleteWord1: dp[i-1][j] + 1
                int deleteWord1 = dp[j] + 1;
                // deleteWord2: dp[i][j-1] + 1
                int deleteWord2 = pre + 1;
                dp[j] = Math.min(temp, Math.min(deleteWord1, deleteWord2));
                pre = dp[j];
            }
            System.out.println(Arrays.toString(dp));
            dp[len2] = pre;
        }
        return dp[len2];
    }

    public static void printDP(int[][] dp) {
        for (int i[] : dp) {
            for (int j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
//        System.out.println(isOneEditDistance2("abc".toCharArray(), "adc".toCharArray()));
        System.out.println(minDistance2("ab", "abc"));
    }
}
