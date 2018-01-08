/*
*
* 91. Decode Ways
* https://leetcode.com/problems/decode-ways/
*
* 思路：
* dp[i]: 到input string第i个char的时候又几种decode ways
* 假设String是12121，把它分解为【121】+ “21”，和【1212】 + "1"
* 121是dp[i-2]，1212是dp[i-1]
* */

package tag;

import java.util.ArrayList;
import java.util.List;

public class decode_ways {
    // corner cases
    // XY可以解码的条件是：9 < XY <= 26
    // Y可以单独解码的条件是：Y != '0'
    // DP: time: O(n), space: O(m)
    public int numDecodings(String s) {
        int n = s.length();
        if (s == null || n == 0) return 0;
        int[] dp = new int[n+1]; // includes one more ""
        dp[0] = 1; // '' could be decoded as ''
        dp[1] = s.charAt(0) == '0' ? 0 : 1; // 0 has 0 way to encode
        for (int i = 2; i <= n; i++) {
            int x = Integer.valueOf(s.substring(i-1, i));
            int xy = Integer.valueOf(s.substring(i-2, i));
            if (x != 0) dp[i] += dp[i-1];
            if (xy >= 10 && xy <= 26) dp[i] += dp[i-2];
        }
        return dp[n];
    }
    /*
    *
    * follow-up: 优化为constant space
    *
    * */
    // method 2: 把space从O(n)优化为O(1)
    // corner cases
    // XY可以解码的条件是：9 < XY <= 26
    // X可以单独解码的条件是：X != '0'
    public int numDecodings2(String s) {
        int n = s.length();
        // base case
        if (s == null || n == 0 || s.charAt(0) == '0') return 0;
        // DP
        int prev1 = 1, prev2 = 1, cur = 1;
        // int[] dp = new int[n+1]; // includes one more ""
        // dp[0] = 1; // use 1 to make the rest DP work
        // dp[1] = s.charAt(0) == '0' ? 0 : 1; // 0 has 0 way to encode
        for (int i = 2; i <= n; i++) {
            cur = 0;
            int x = Integer.valueOf(s.substring(i-1, i));
            int xy = Integer.valueOf(s.substring(i-2, i));
            // if (x != 0) dp[i] += dp[i-1];
            if (x != 0) cur += prev1;
            // if (xy >= 10 && xy <= 26) dp[i] += dp[i-2];
            if (xy >= 10 && xy <= 26) cur += prev2;
            prev2 = prev1;
            prev1 = cur;
        }
        return cur;
    }

    /*
    *
    * folow-up: 如果给的value不连续，比如a:78, b:539, ..., 怎么办。。我说可以 backtracking，然后简单说了一下怎么搞。。
    *
    * */

    /*
    *
    * 变种：Return all decode ways
    *
    * */
    public List<String> numDecodings3(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() == 0)    return res;
        dfs(res, s, new StringBuilder(), 0);
        return res;
    }
    private void dfs(List<String> res, String s, StringBuilder sb, int i) {
        if (i == s.length()) {//if the whole string has been decoded, return 1
            res.add(sb.toString());
            return;
        }
        if (s.charAt(i) == '0')    return;
        int length = sb.length();
        int x = Integer.valueOf(s.substring(i, i + 1));
        dfs(res, s, sb.append((char)('A' + x - 1)), i + 1);
        sb.setLength(length);
        if (i < s.length() - 1 && Integer.valueOf(s.substring(i, i + 2)) <= 26) {
            int xy = Integer.valueOf(s.substring(i, i + 2));
            dfs(res, s, sb.append((char)('A' + xy - 1)), i + 2);
            sb.setLength(length);
        }
    }


    /*
    *
    * follow-up: 如果可以是星号（match 1-9）
    *
    * */

    // DP, space: O(n)
    public int numDecodings4(String s) {
        // use long to avoid overflow
        int n = s.length();
        if (s == null || n == 0 || s.charAt(0) == '0') return 0;
        long[] dp = new long[n+1];

        // initialization for dp
        dp[0] = 1;
        dp[1] = s.charAt(0) == '*' ? 9 : 1;

        // bottom up dp
        for (int i = 2; i <= n; i++) {
            char x = s.charAt(i-2); // first char
            char y = s.charAt(i-1); // second char

            //for dp[i-1], or x, x != 0
            if (y == '*') dp[i] = 9 * dp[i-1];
            else if (y != '0') dp[i] += dp[i-1];

            // for dp[i-2], or xy, 10 <= xy <= 26
            if (x == '*') {
                // 11 ~ 19 & 21 ~ 26 (* cannot be 0)
                if (y == '*') dp[i] += (9 + 6) * dp[i-2];
                    // [1]'y' or [2]'y'
                else if (y <= '6') dp[i] += 2 * dp[i-2];
                    // [1]'y'
                else dp[i] += dp[i-2];
            }
            else if (x == '1' || x == '2') {
                if (y == '*') {
                    // [1]1 ~ [1]9
                    if (x == '1') dp[i] += 9 * dp[i-2];
                        // [2]1 ~ [2]6
                    else dp[i] += 6 * dp[i-2];
                    // x == '1' or '2', y != '*' -> 10 <= xy <= 26
                } else if ((x - '0') * 10 + (y - '0') <= 26) dp[i] += dp[i-2];
            }
            dp[i] %= 1000000007;
        }
        // don't forget to convert long to int
        return (int)dp[n];
    }

    // DP, space: O(1)
    public int numDecodings5(String s) {
        // use long to avoid overflow
        int n = s.length();
        if (s == null || n == 0 || s.charAt(0) == '0') return 0;
        // long[] dp = new long[n+1];

        // initialization for dp, cur = dp[i], prev1 = dp[i-1], prev2 = dp[i-2]
        long prev2 = 1, prev1 = s.charAt(0) == '*' ? 9 : 1;
        long cur = prev1;

        // bottom up dp
        for (int i = 2; i <= n; i++) {
            char x = s.charAt(i-2); // first char
            char y = s.charAt(i-1); // second char
            cur = 0;

            //for dp[i-1], or x, x != 0
            if (y == '*') cur = 9 * prev1;
            else if (y != '0') cur += prev1;

            // for dp[i-2], or xy, 10 <= xy <= 26
            if (x == '*') {
                // 11 ~ 19 & 21 ~ 26 (* cannot be 0)
                if (y == '*') cur += (9 + 6) * prev2;
                    // [1]'y' or [2]'y'
                else if (y <= '6') cur += 2 * prev2;
                    // [1]'y'
                else cur += prev2;
            }
            else if (x == '1' || x == '2') {
                if (y == '*') {
                    // [1]1 ~ [1]9
                    if (x == '1') cur += 9 * prev2;
                        // [2]1 ~ [2]6
                    else cur += 6 * prev2;
                    // x == '1' or '2', y != '*' -> 10 <= xy <= 26
                } else if ((x - '0') * 10 + (y - '0') <= 26) cur += prev2;
            }
            cur %= 1000000007;
            prev2 = prev1;
            prev1 = cur;
        }
        // don't forget to convert long to int
        return (int)cur;
    }
}
