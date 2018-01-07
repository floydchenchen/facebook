/*
*
* 67. Add Binary
* https://leetcode.com/problems/add-binary/
*
* 题目描述：Given two binary strings, return their sum (also a binary string).
* For example,
* a = "11"
* b = "1"
* Return "100".
*
* */

package tag;

public class add_binary {

    // binary sum
    public static String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        // 注意加的时候从右往左加
        int i = a.length() - 1, j = b.length() - 1, sum = 0;
        while (i >= 0 || j >= 0) {
            if (i >= 0) sum += (a.charAt(i--) - '0');
            if (j >= 0) sum += (b.charAt(j--) - '0');
            // %2就是这一位加的结果
            sb.append(sum % 2);
            // /2就是carry
            sum /= 2;
        }
        // 最高位，别忘了判断最后剩下的carry是否是1
        if (sum == 1) sb.append(sum);
        return sb.reverse().toString();
    }

    /*
    *
    * follow-up:
    * add binary的话，假设是4进制，n进制应该怎么做，最好的做法是用一个sum variable来记录没两位的和，除以n进制和对n进制求和来找到每一位的数值
    *
    * n进制这一位的结果就是 sum % 4, carry就是 sum / 4
    *
    * */

}
