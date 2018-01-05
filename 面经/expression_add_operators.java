import java.util.ArrayList;
import java.util.List;

public class expression_add_operators {
    public static List<String> addOperators(String num, int target) {
        List<String> list = new ArrayList<>();
        if(num == null || num.length() == 0) return list;
        StringBuilder sb = new StringBuilder();
        dfs(list, sb, num, target, 0, 0, 0);
        return list;
    }

    /**
     * @param list: the result list
     * @param path: act like the tempList
     * @param num:
     * @param target:
     * @param index:
     * @param eval:
     * @param multi:
     * */
    public static void dfs(List<String> list, StringBuilder path, String num, int target, int index, long eval, long multi){
        if(index == num.length()){
            if(target == eval)
                list.add(path.toString());
            return;
        }
        for(int i = index; i < num.length(); i++){
            // edge case:
            if(i != index && num.charAt(index) == '0') break;
            long cur = Long.parseLong(num.substring(index, i + 1)); // 这里必须是index
            int StringLength = path.length();
            if(index == 0){
                dfs(list, path.append(cur), num, target, i + 1, cur, cur);
                path.setLength(StringLength);
            }
            else{
                dfs(list, path.append("+").append(cur), num, target, i + 1, eval + cur , cur);
                path.setLength(StringLength);
                dfs(list, path.append("-").append(cur), num, target,i + 1, eval -cur, -cur);
                path.setLength(StringLength);
                dfs(list, path.append("*").append(cur), num, target, i + 1, eval - multi + multi * cur, multi * cur );
                path.setLength(StringLength);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(addOperators("105", 5));
    }
}