import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class remove_invalid_parentheses_301 {
    public static List<String> removeInvalidParentheses(String input) {
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
                dfs(input, i + 1, result, temp, removeLeft - 1, removeRight, open, '(');		    // not use (

            if (lastSkipped != '(')
                dfs(input, i + 1, result, temp.append(currentChar), removeLeft, removeRight, open + 1, '@');       // use (

        } else if (currentChar == ')') {
            if (removeRight > 0)
                dfs(input, i + 1, result, temp, removeLeft, removeRight - 1, open, ')');	            // not use  )

            if (open > 0 && (lastSkipped != ')'))
                dfs(input, i + 1, result, temp.append(currentChar), removeLeft, removeRight, open - 1, '@');  	    // use )

        } else {  // for other characters besides '(' and ')', just add them to the new string
            dfs(input, i + 1, result, temp.append(currentChar), removeLeft, removeRight, open, '@');
        }

        temp.setLength(length);
    }

    public static void main(String[] args) {
        System.out.println(removeInvalidParentheses("(a)())()"));
    }
}
