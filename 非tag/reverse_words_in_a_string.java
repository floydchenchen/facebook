/*
* 151. Reverse Words in a String
* 题目描述：
* For example,
* Given s = "the sky is blue",
* return "blue is sky the".
*
* Clarification:
* What constitutes a word?
*   - A sequence of non-space characters constitutes a word.
* Could the input string contain leading or trailing spaces?
*   - Yes. However, your reversed string should not contain leading or trailing spaces.
* How about multiple spaces between two words?
*	- Reduce them to a single space in the reversed string.
*
* */


package 非tag;

public class reverse_words_in_a_string {
    // Solution：split + 倒序遍历append
    public static String reverseWords1(String s) {
        // "\\s+" matches sequence of one or more whitespace characters.
        String[] strs = s.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = strs.length - 1; i >= 0; i--) {
            sb.append(strs[i]).append(" ");
        }
        return sb.toString().trim();
    }

    // 186. Reverse Words in a String II
    // space should be O(1) & input
    // input string does not contain leading or trailing spaces and the words are always separated by a single space
    public void reverseWords2(char[] s) {
        // reverse the whole sentense
        reverse(s, 0, s.length - 1);
        int start = 0;
        // reverse each word (except the last)
        for (int i = 0; i < s.length; i++) {
            if (s[i] == ' ') {
                reverse(s, start, i - 1);
                start = i + 1;
            }
        }
        // reverse the last word
        reverse(s, start, s.length - 1);
    }
    private void reverse(char[] s, int i, int j) {
        while (i < j) {
            char tmp = s[i];
            s[i++] = s[j];
            s[j--] = tmp;
        }
    }

    public static void main(String[] args) {
        System.out.println(reverseWords1("the sky is blue"));
    }
}
