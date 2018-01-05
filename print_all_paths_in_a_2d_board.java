import java.util.ArrayList;
import java.util.List;

public class print_all_paths_in_a_2d_board {
    public static List<String> printAllPath(char[][] board){
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        dfs(board,res,sb,0,0);

        return res;

    }

    public static void dfs(char[][] b, List<String> result, StringBuilder sb, int x, int y){
        int len = sb.length();
        sb.append(b[y][x]);

        if( x == b[0].length - 1 && y == b.length - 1){
            result.add(sb.toString());
        } else{
            if( x + 1 < b[0].length) dfs(b,result,sb,x+1,y);
            if( y + 1 < b.length ) dfs(b,result,sb,x,y+1);
        }
        sb.setLength(len);
    }

    public static void main (String[] args) throws java.lang.Exception {
        char[][] test = {{'a','b','c'},{'e','f','g'}};
        List<String> res = printAllPath(test);
        System.out.println("xxx");
        for(String s : res) {
            System.out.println(s);
        }

    }
}
