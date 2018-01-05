/*
*
* 思路：
* 统计左右括号能删的个数，进行DFS。每个node（字母）有两个children：使用或者不使用，所以分别对两个child进行DFS
*
* 新建 removeLeft counter 与 removeRight counter
* 遇到 '(' 就 removeLeft++
* 遇到 ')'，如果 removeLeft不为0，removeLeft--；removeLeft为0，removeRight++
* 通过这样的方式计算出需要remove 的 '(' 或 ')' 数量
* open参数：即现在正在计算的左括号减右括号数量
*
* 优化思路
* for example: s = ()), we can remove s[1] or s[2] but the result is the same ().
* Thus, we restrict ourself to remove the first ) in a series of concecutive ')'s.
* 引入一个新的参数叫 char lastSkipped，即上一个跳过的character。如果当前的char和lastSkipped相同，就直接跳过当前(prune)。
* 当节点为'('
*   如果removeLeft > 0，说明需要remove left，就选择”不使用“这个child
*   如果lastSkipped == current，为了避免重复，跳过current.
* 当节点为')'
*   如果removeRight > 0，说明需要remove right，就选择”不使用“这个child
*   如果lastSkipped == current 或者已经不open了（number_of_leftAdded_minus_rightAdded = 0），就跳过current
* */

import java.util.ArrayList;
import java.util.List;

public class remove_invalid_parentheses_hard {
    public static List<String> removeInvalidParentheses(String input) {
        // caculation of removeLeft and removeRight
        int removeLeft = 0, removeRight = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                removeLeft++;
            } else if (input.charAt(i) == ')') {
                if (removeLeft != 0) {
                    removeLeft--;
                } else {
                    removeRight++;
                }
            }
        }
        List<String> result = new ArrayList<>();
        dfs(input, 0, result, new StringBuilder(), removeLeft, removeRight, 0, '@');
        return result;
    }

    /**
     * @param input: the input string
     * @param i: current index
     * @param result: the result list to return
     * @param temp: the string to be added to the result list
     * @param removeLeft: the number of '(' to be removed
     * @param removeRight: the number of ')' to be removed
     * @param open: number of leftAdded - number of rightAdded
     * @param lastSkipped: the character that ls last skipped
     */
    public static void dfs(String input, int i, List<String> result, StringBuilder temp, int removeLeft, int removeRight, int open, char lastSkipped) {
        if (removeLeft < 0 || removeRight < 0 || open < 0) {
            return;
        }
        if (i == input.length()) {
            if (removeLeft == 0 && removeRight == 0 && open == 0) {
                result.add(temp.toString());
            }
            return;
        }

        char currentChar = input.charAt(i);
        int length = temp.length();

        if (currentChar == '(') {
            if (removeLeft > 0)
                dfs(input, i + 1, result, temp, removeLeft - 1, removeRight, open, '(');            // not use (

            if (lastSkipped != '(')
                dfs(input, i + 1, result, temp.append(currentChar), removeLeft, removeRight, open + 1, '@');       // use (

        } else if (currentChar == ')') {
            if (removeRight > 0)
                dfs(input, i + 1, result, temp, removeLeft, removeRight - 1, open, ')');                // not use  )

            if (open > 0 && (lastSkipped != ')'))
                dfs(input, i + 1, result, temp.append(currentChar), removeLeft, removeRight, open - 1, '@');        // use )

        } else {  // for other characters besides '(' and ')', just add them to the new string
            dfs(input, i + 1, result, temp.append(currentChar), removeLeft, removeRight, open, '@');
        }

        temp.setLength(length);
    }
}
