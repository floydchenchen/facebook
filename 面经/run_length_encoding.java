package 面经;/*
* Run Length Encoding
* Given an input string, write a function that returns the Run Length Encoded string for the input string.
*
* For example, if the input string is “wwwwaaadexxxxxx”, then the function should return “w4a3d1e1x6”.
* 举例来说，一组资料串"AAAABBBCCDEEEE"，由4个A、3个B、2个C、1个D、4个E组成，经过变动长度编码法可将资料压缩为4A3B2C1D4E（由14个单位转成10个单位）
*
* time: O(n)
* */

public class run_length_encoding {
    public static String runLength(String s){
        if(s == null || s.length() == 0)    return "";
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 0; i < s.length(); i++) {
            while (i < s.length() - 1 && s.charAt(i) == s.charAt(i + 1)) {
                count++;
                i++;
            }
            sb.append(s.charAt(i)).append(count);
            count = 1;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(runLength("abca"));
    }
}
