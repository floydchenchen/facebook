/*
* 简单版：只输出第一个valid的
*
* time: O(n), 2 pass
* space: O(n): string builder
* 思路：只要遇到open < 0 就remove，完了之后reverse再来一次。
* */

public class remove_invalid_parentheses_easy {

    public static String removeInvalidParentheses(String s) {
        int open = 0;
        StringBuilder sb = new StringBuilder();
        // 第一遍删除多余的')'
        for (char c: s.toCharArray()) {
            if (c == '(') open++;
            else if (c == ')') open--;
            if (open >= 0) sb.append(c);
            else open = 0;
        }
        s = sb.reverse().toString();
        sb = new StringBuilder();
        open = 0;
        // 第二篇删除多余的'('
        for (char c: s.toCharArray()) {
            if (c == '(') open--;
            else if (c == ')') open++;
            if (open >= 0) sb.append(c);
            else open = 0;
        }
        return sb.reverse().toString();

/*
* 困难版：输出所有的valid parentheses
*
* 思路：leetcode
* */
    }

    public static void main(String[] args) {
        System.out.println(removeInvalidParentheses(")((32(%)("));
    }
}
