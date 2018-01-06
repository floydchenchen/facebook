/*
*
* edge cases: empty string || empty dictionary
* dp is an array that contains booleans
* dp[i] is true if there is a word in the dictionary that ends at ith index of s AND dp is also true at the beginning of the word
*
* s = "leetcode", wordDict = ["leet", "code"]
* dp[3] is true because there is "leet" in the dictionary that ends at 3rd index of "leetcode"
* dp[7] is true because there is "code" in the dictionary that ends at the 7th index of "leetcode" AND dp[3] is true
* 相当于用boolean的true false value来划分空格
* 总结：二分的东西可以用boolean / 二进制来表示
*
*
*
* 给一个字符串 such as: "GoogleFacebookMicrosoft", 由字母构成，然后给一个字典，把给定的字符按照字典进行切割，然后输出分割后的一个解答，
* 例如：dict=['Google', 'Facebook', 'Microsoft', 'GoogleFace', 'bookMicsoft']
* 如果有多个解答，输出一个即可，对于这个例子显然有两个解答，"Google Facebook Microsoft"， "GoogleFace bookMicrosoft"。随便输出一个就行
* 我回答，递归可以做，然后给出了答案，分析了复杂度O(n^m)。这里复杂度分析卡了一下，不过还好
* */

package tag;

import java.util.*;

public class word_break {
    // DP, dp[i]:代表从上一个true的index开始，例如从dp[j]到dp[i]，是否能对应dictionary的一个词
    // time: O(n^2) space: O(n)
    public static boolean wordBreak(String s, List<String> wordDict) {
        int minLength = getMinLength(wordDict);
        if (s.isEmpty() || wordDict.isEmpty() || s.length() < minLength) return false;
        boolean[] dp = new boolean[s.length() + 1]; // because of the substring method
        dp[0] = true; // ""对应dictionary的""

        for (int i = minLength - 1; i < dp.length; i++) {
            for (int j = 0; j < i - minLength + 1; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    public static int getMinLength(List<String> wordDict) {
        int min = Integer.MAX_VALUE;
        for (String word :wordDict) {
            min = Math.min(min, word.length());
        }
        return min;
    }

    // 方法2：backtracking

    /*
    *
    * follow-up: 输出一组解
    * 思路：DP + 对DP的结果backtrack
    * time: O(n^2) + O(startPos[i] ^ startPos.size())
    * */
    public static List<String> wordBreak3(String s, List<String> wordDict) {
        int minLength = getMinLength(wordDict);
        List<String> result = new ArrayList<>();
        if (s.isEmpty() || wordDict.isEmpty() || s.length() < minLength) return result;
        // all valid startPos for each valid end pos
        List<Integer>[] startPos = new List[s.length() + 1];
        startPos[0] = new ArrayList<>();

        for (int i = minLength - 1; i <= s.length(); i++) {
            for (int j = 0; j < i - minLength + 1; j++) {
                String word = s.substring(j, i);
                if (startPos[j] != null && wordDict.contains(word)) {
                    if (startPos[i] == null) startPos[i] = new ArrayList<>();
                    startPos[i].add(j);
                }
            }
        }
        if (startPos[s.length()] == null) return result;
        // backtracking
        DFS(result, "", s.length(), s,startPos);
        return result;
    }

    private static void DFS(List<String> result, String temp, int end, String s, List<Integer>[] startPos) {
        if (end == 0) {
            result.add(temp.substring(1));
            return;
        }
        for (Integer start : startPos[end]) {
            String w = s.substring(start, end);
            DFS(result, " " + w + temp, start, s, startPos);
        }
    }


    public static void main(String[] args) {
        String[] words = {"Google", "Facebook", "Microsoft", "GoogleFace", "bookMicrosoft"};
        List<String> wordDict = Arrays.asList(words);
        System.out.println(wordBreak3("GoogleFacebookMicrosoft", wordDict));
    }
}
