/*
*
* 282. Expression Add Operators
* https://leetcode.com/problems/expression-add-operators/
*
* Given a string that contains only digits 0-9 and a target value,
* return all possibilities to add binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.
*
* "123", 6 -> ["1+2+3", "1*2*3"]
* "232", 8 -> ["2*3+2", "2+3*2"]
* "105", 5 -> ["1*0+5","10-5"]
* "00", 0 -> ["0+0", "0-0", "0*0"]
* "3456237490", 9191 -> []
*
* */

package tag;

import java.util.ArrayList;
import java.util.List;

public class expression_add_operator {

    /*
    *
    * 简单版：只有加减
    * 给1个string “123456789”， 进行arithmetic oepration combination.
    * 如: 123 + 456 + 78 -9 是1种组合， -1 + 2 -3 +4 -5 - 67 + 89 也是1种(只加 + 或 -), 打印出所有结果等于 100 的组合
    *
    * */
    public static List<String> addOperators1(String num, int target) {
        List<String> list = new ArrayList<>();
        if(num == null || num.length() == 0) return list;
        StringBuilder sb = new StringBuilder();
        dfs1(list, sb, num, target, 0, 0);
        return list;
    }

    public static void dfs1(List<String> list, StringBuilder path, String num, int target, int pos, long eval){
        if(pos == num.length()){
            if(target == eval) list.add(path.toString());
            return;
        }
        // DFS on all numbers starting at position 'pos'
        for(int i = pos; i < num.length(); i++){
            // edge case: 0 cannot be leading number!!!!!!!!
            if(i != pos && num.charAt(pos) == '0') break;
            long cur = Long.parseLong(num.substring(pos, i + 1)); // 这里必须是pos
            int StringLength = path.length();
            // 如果是num的第一位数字，直接把它append到path上，继续从下一个数字开始DFS（backtracking）
            if(pos == 0){
                dfs1(list, path.append(cur), num, target, i + 1, cur);
                path.setLength(StringLength);
            }
            else{
                dfs1(list, path.append("+").append(cur), num, target, i + 1, eval + cur);
                path.setLength(StringLength);
                dfs1(list, path.append("-").append(cur), num, target,i + 1, eval - cur);
                path.setLength(StringLength);
            }
        }
    }


    // 跟简单版相比，多了*需要handle，所以在DFS加了一个multi的parameter
    // 考虑没有括号，所以乘法要先算，例如 4 + 8 * 2，需要先算 8 * 2
    // 注意DFS的input parameter中，eval的值是12，multi值是8；另外cur值是2，所以eval = eval - multi + multi * cur
    public static List<String> addOperators2(String num, int target) {
        List<String> list = new ArrayList<>();
        if(num == null || num.length() == 0) return list;
        StringBuilder sb = new StringBuilder();
        dfs2(list, sb, num, target, 0, 0, 0);
        return list;
    }

    public static void dfs2(List<String> list, StringBuilder path, String num, int target, int pos, long eval, long multi){
        if(pos == num.length()){
            if(target == eval) list.add(path.toString());
            return;
        }
        // DFS on all numbers starting at position 'pos'
        for(int i = pos; i < num.length(); i++){
            // edge case: 0 cannot be leading number
            if(i != pos && num.charAt(pos) == '0') break;
            long cur = Long.parseLong(num.substring(pos, i + 1)); // 这里必须是pos
            int StringLength = path.length();
            if(pos == 0){
                dfs2(list, path.append(cur), num, target, i + 1, cur, cur);
                path.setLength(StringLength);
            }
            else{
                dfs2(list, path.append("+").append(cur), num, target, i + 1, eval + cur , cur);
                path.setLength(StringLength);
                dfs2(list, path.append("-").append(cur), num, target,i + 1, eval -cur, -cur);
                path.setLength(StringLength);
                dfs2(list, path.append("*").append(cur), num, target, i + 1, eval - multi + multi * cur, multi * cur );
                path.setLength(StringLength);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(addOperators2("100", 1));
    }
}
