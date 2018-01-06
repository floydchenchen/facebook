/*
*
* 题目描述：
* '.' Matches any single character.
* '*' Matches zero or more of the [preceding] element.
*
* 思路：
* dp[i][j] means to text(i) and pattern(j), whether it matches or not.
*   if(pattern.charAt(j - 1) == "*") dp[0][j] = dp[0][j - 2]
*       why j - 2 here?
*       It's because * cannot work alone.
*
* method 1: DP, 2D array
*   time: O(mn)
*   space: O(mn)
* method 2: DP, 1D array
*   time: O(mn)
*   space: O(n)
*
*
* 1, If p.charAt(j) == s.charAt(i) : dp[i][j] = dp[i-1][j-1];
* 2, If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
* 3, If p.charAt(j) == '*':
*   here are two sub conditions:
*       if p.charAt(j-1) != s.charAt(i) : dp[i][j] = dp[i][j-2], n this case, a* only counts as empty
*       if p.charAt(i-1) == s.charAt(i) or p.charAt(i-1) == '.':
*
*           (1) dp[i][j] = dp[i-1][j], in this case, a* counts as multiple 'a'
*           a*"相当于"aa"或者更多，到这个位置能否match取决于text上个位置和pattern这个位置能否match
*           e.g. text = "[a]a", pattern = "a[*]"
*
*           (2) dp[i][j] = dp[i][j-1], in this case, a* counts as single 'a'
*           a*"相当于"a", 到这个位置能否match取决于pattern上个位置和text这个位置能否match
*           e.g. text = "[a]", pattern = "a[*]"
*
*           (3) dp[i][j] = dp[i][j-2], in this case, a* counts as empty
*           "a*"相当于""，到这个位置能否match取决于text的这个位置和pattern的上两个位置能否match
*           e.g. text = "[b]", pattern = "ba[*]"
*
*  参考：https://www.jianshu.com/p/c09c4a3fc14a
*
* */

package tag;

public class regular_expression_matching {
    // 以isMatch("aab", "c*a*b")为例子
    public static boolean isMatch(String text, String pattern) {
        // base case: check if either is empty
        if (text == null || pattern == null) {
            return false;
        }
        // new 2d dp table
        boolean[][] dp = new boolean[text.length()+1][pattern.length()+1];

        // base case 1: of dp, if both text and pattern are empty, return true
        dp[0][0] = true;
        // printDP(dp);
//        T F F F F F
//        F F F F F F
//        F F F F F F
//        F F F F F F

        // base case 2: if text is empty and pattern is *, return true
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '*' && dp[0][i-1]) {
                dp[0][i+1] = true;
            }
        }
        // printDP(dp);
//        T F T F T F
//        F F F F F F
//        F F F F F F
//        F F F F F F

        // dp
        // dp process
        for (int i = 1; i <= text.length(); i++) {
            for (int j = 1; j <= pattern.length(); j++) {
                // matching case 0: if char in pattern is '.'
                // matching case 1: 这个位置text和pattern能match
                // 到这个位置能否match取决于text和pattern的上个位置能否match
                if (pattern.charAt(j-1) == '.' || pattern.charAt(j-1) == text.charAt(i-1)) {
                    dp[i][j] = dp[i-1][j-1];
                }
                // matching case 2: if char in pattern is '*'
                if (pattern.charAt(j-1) == '*') {
                    // matching case 2.1: pattern上个位置不是 '.', 而且pattern上个位置和text这个位置不match
                    // e.g. text = "[a]", pattern = "c[*]", "c*" does not match "a",所以"c*"相当于""
                    // 到这个位置能否match取决于text的这个位置和pattern的上2个位置能否match
                    if (pattern.charAt(j-2) != text.charAt(i-1) && pattern.charAt(j-2) != '.') {
                        dp[i][j] = dp[i][j-2];
                    }
                    // matching case 2.2: pattern上个位置是'.' 或pattern上个位置和text这个位置match
                    else {
                        // dp[i][j] = dp[i][j-1],  e.g. text = "[a]", pattern = "a[*]"
                        // "a*"相当于"a", 到这个位置能否match取决于pattern上个位置和text这个位置能否match
                        // [*]这里相当于""

                        // dp[i][j] = dp[i-1][j], e.g. text = "a[a]", pattern = "a[*]"
                        // "a*"相当于"aa"或者更多，到这个位置能否match取决于text上个位置和pattern这个位置能否match
                        // [*]这里相当于[a]

                        // dp[i][j] = dp[i][j-2], e.g. text = "[b]", pattern = "a[*]"
                        // "a*"相当于""，到这个位置能否match取决于text的这个位置和pattern的上两个位置能否match

                        dp[i][j] = (dp[i][j-1] || dp[i][j-2] || dp[i-1][j]);
                    }
                }
            }
        }
        return dp[text.length()][pattern.length()];
    }

    /*
    * follow-up: DP空间优化
    *
    * */
    // 以isMatch("aab", "c*a*b")为例子
    public static boolean isMatch2(String text, String pattern) {
        // base case: check if either is empty
        if (text == null || pattern == null) {
            return false;
        }
        // optimization: 1d dp array
        boolean[] dp = new boolean[pattern.length()+1];
        // base case 1: of dp, if both text and pattern are empty, return true
        dp[0] = true;

        // base case 2: if text is empty and pattern is *, initialize the dp[] with true
        for (int i = 2; i <= pattern.length(); i++) {
            // first char cannot be '*'
            dp[i] = (pattern.charAt(i-1) == '*' && dp[i-2]);
        }

        // dp process
        boolean pre, cur; // pre = dp[i-1][j-1], cur = dp[i-1][j]
        for (int i = 1; i <= text.length(); i++) {
            pre = dp[0];
            dp[0] = false;
            char textCurrent = text.charAt(i-1);
            for (int j = 1; j <= pattern.length(); j++) {
                cur = dp[j];
                char patternCurrent = pattern.charAt(j-1);
                if (patternCurrent != '*') dp[j] = pre && (textCurrent == patternCurrent || patternCurrent == '.');
                else {
                    char patternPrevious = pattern.charAt(j-2);
                    dp[j] = dp[j-2] || (dp[j] && (patternPrevious == textCurrent || patternPrevious == '.'));
                }
                pre = cur;
            }
        }
        return dp[pattern.length()];
    }

    // 方法2：recursion time: O(mn), space: O(m/n)
    public boolean isMatch3(String s, String p) {
        if (p.isEmpty()) return s.isEmpty();

        // encounter star: match zero or match one only if . or char matched
        //  1. `a*` count as '' OR
        //  2. (first char matches or first pattern is '.') && isMatch3(s.substring(1), p)
        if (p.length() > 1 && p.charAt(1) == '*')
            return isMatch3(s, p.substring(2)) ||
                    ((!s.isEmpty() && (p.charAt(0) == '.' || s.charAt(0) == p.charAt(0))) && isMatch3(s.substring(1), p));

        // encounter dot: match one letter
        if (p.charAt(0) == '.')
            return !s.isEmpty() && isMatch3(s.substring(1), p.substring(1));

        // just normal char
        return !s.isEmpty() && s.charAt(0) == p.charAt(0) && isMatch3(s.substring(1), p.substring(1));
    }
}
