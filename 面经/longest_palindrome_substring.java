public class longest_palindrome_substring {
    public static String longestPalindrome(String s) {
        int n = s.length();
        String result = "";
        boolean[] dp = new boolean[n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= i; j--) {
                dp[j] = s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[j-1]);
                if(dp[j] && (result.equals("") || j - i + 1 > result.length())) {
                    result = s.substring(i, j+1);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("abcba"));
    }
}
