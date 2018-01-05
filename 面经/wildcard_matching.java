import java.util.Arrays;

public class wildcard_matching {
    public static boolean isMatch(String text, String pattern) {
        boolean[] dp = new boolean[pattern.length() + 1];
        // dp[0][0] text and pattern are both "" -> true
        dp[0] = true;
        boolean pre, cur;
        // 重要！
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

    public static void main(String[] args) {
        System.out.println(isMatch("aa","****f"));
    }
}
