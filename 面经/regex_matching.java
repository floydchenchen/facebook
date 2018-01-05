public class regex_matching {
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
        printDP(dp);
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
        printDP(dp);
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
                        // dp[i][j] = dp[i][j-1], e.g. text = "[a]b", pattern = "a[*]",
                        // "a*"相当于"a", 到这个位置能否match取决于pattern上个位置和text这个位置能否match

                        // dp[i][j] = dp[i-1][j], e.g. text = "[a]ab", pattern = "a[*]"
                        // "a*"相当于"aa"或者更多，到这个位置能否match取决于text上个位置和pattern这个位置能否match

                        // dp[i][j] = dp[i][j-2], e.g. text = "a[b]c", pattern = "a[*]"
                        // "a*"相当于""，到这个位置能否match取决于text的这个位置和pattern的上两个位置能否match

                        dp[i][j] = (dp[i][j-1] || dp[i][j-2] || dp[i-1][j]);
                    }
                }
                printDP(dp);
            }
        }
        return dp[text.length()][pattern.length()];
    }

    public static void printDP(boolean[][] dp) {
        for (int i = 0 ; i < dp.length; i++) {
            for (int j = 0 ; j < dp[0].length; j++) {
                if (dp[i][j] == true) System.out.print("T ");
                else System.out.print("F ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println(isMatch("aab", "c*a*b"));
    }
}
