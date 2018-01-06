/*
*
* 题目描述：
* '?' Matches any single character.
* '*' Matches any sequence of characters (including the empty sequence)
*
* 为啥wild card可以用O(n)来做？
* regular expression matching中的'*'必须是preceding，所以涉及到go back?
* wild card中的'*'可以match empty char
*
* */

package tag;

import java.util.Arrays;

public class wild_card_matching {
    // DP,
    // time: O(mn), m is the size of the text and n is the size of the pattern
    // space: O(n)
    public static boolean isMatch(String text, String pattern) {
        boolean[] dp = new boolean[pattern.length() + 1];
        // dp[0][0] text and pattern are both "" -> true
        dp[0] = true;
        boolean pre, cur;
        // 重要！考虑pattern是'\*\*\*'或者是'\*\*\*ab'的这种情况
        for (int j = 1; j <= pattern.length(); j++) {
            if (pattern.charAt(j-1) == '*') dp[j] = true;
            else break;
        }
        System.out.println(Arrays.toString(dp));
        for (int i = 1; i <= text.length(); i++) {
            pre = dp[0];
            dp[0] = false;
            for (int j = 1; j <= pattern.length(); j++) {
                cur = dp[j];
                if (pattern.charAt(j-1) == '*') {
                    // * 为空，text: [a]b, pattern: a[*], dp[i][j] = dp[i][j-1]
                    // * 不为空，text: b[a]b, pattern: [*]b, dp[i][j] = dp[i-1][j];
                    // 刚刚好，不需要额外的var帮忙, dp[j-1]即为dp[i][j-1]，而dp[j]还是存的上一个loop中的待更新的dp[i-1][j]
                    dp[j] = dp[j-1] || dp[j];
                } else {
                    // dp[i][j] = dp[i-1][j-1] && (s.charAt(i-1)==p.charAt(j-1) || p.charAt(j-1)=='?');
                    dp[j] = (pattern.charAt(j-1) == text.charAt(i-1) || pattern.charAt(j-1) == '?') && pre;
                }
                pre = cur;
            }
        }
        return dp[pattern.length()];
    }

    // two pointers, O(n) time, O(1) space
    boolean comparison(String str, String pattern) {
        // starIndex: 存starIndex所在的地方
        int s = 0, p = 0, match = 0, starIndex = -1;
        while (s < str.length()){
            // advancing both pointers
            if (p < pattern.length()  && (pattern.charAt(p) == '?' || str.charAt(s) == pattern.charAt(p))){
                s++;
                p++;
            }
            // * found, only advancing pattern pointer
            else if (p < pattern.length() && pattern.charAt(p) == '*'){
                starIndex = p;
                match = s;
                p++;
            }
            // last pattern pointer was *, advancing string pointer
            else if (starIndex != -1){
                p = starIndex + 1;
                match++;
                s = match;
            }
            //current pattern pointer is not star, last pattern pointer was not *
            //characters do not match
            else return false;
        }

        //check for remaining characters in pattern
        while (p < pattern.length() && pattern.charAt(p) == '*')
            p++;

        return p == pattern.length();
    }

}
