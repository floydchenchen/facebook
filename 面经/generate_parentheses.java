import java.util.ArrayList;
import java.util.List;

public class generate_parentheses {
    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0) return result;
        dfs(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private static void dfs(List<String> result, StringBuilder sb, int open, int close, int n) {
        // base case
        if (sb.length() == n * 2) {
            result.add(sb.toString());
            return;
        }

        int length = sb.length();

        if (open < n) {
            sb.append('(');
            dfs(result, sb, open + 1, close, n);
            sb.setLength(sb.length() - 1);
        }
        if (close < open) {
            sb.append(')');
            dfs(result, sb, open, close + 1, n);
            sb.setLength(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));
    }
}
