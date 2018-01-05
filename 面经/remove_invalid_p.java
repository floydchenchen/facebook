import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class remove_invalid_p {
    public static String removeInvalidParentheses(String s) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for(char c: s.toCharArray()) {
            if(c == '(') i ++;
            else if(c == ')') i --;
            if(i >= 0) sb.append(c);
            else i = 0;
        }
        s = sb.reverse().toString();
        sb = new StringBuilder();
        i = 0;
        for(char c: s.toCharArray()) {
            if(c == '(') i --;
            else if(c == ')') i ++;
            if(i >= 0) sb.append(c);
            else i = 0;
        }
        return sb.reverse().toString();
    }

//    public static String remove(String s) {
//
//    }

    public static void main(String[] args) {
        System.out.println(removeInvalidParentheses(")(()(fea)9(f))"));
    }
}
