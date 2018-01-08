/*
*
* 38. Count and Say
* https://leetcode.com/problems/count-and-say
*
* The count-and-say sequence is the sequence of integers with the first five terms as following:
*
* 1.     1, one 1 -> 11
* 2.     11, two 1s -> 21
* 3.     21, one 2, one 1 -> 1211
* 4.     1211, one 1, one 2, two 1's -> 111221
* 5.     111221
*
* Given an integer n, generate the nth term of the count-and-say sequence.
*
* */

package tag;

public class count_and_say {

    // recursion method
    // time: O(n^2), space: O(n)
    public static String countAndSay1(int n) {
        return helper("1", 1, n);
    }

    public static String helper(String input, int start, int end) {
        if (end < 1) return "";
        if (start == end) {
            return input;
        }
        StringBuilder output = new StringBuilder();
        char[] chars = input.toCharArray();
        // 用来记录count
        int count = 1;
        char c = chars[0];
        // 从1而不是0开始搜
        for (int i = 1; i < chars.length; i++) {
            if (c == chars[i]) {
                count++;
            } else {// char[i]和c不同，直接append之前的，同时更新c和count
                output.append(count).append(c);
                c = chars[i];
                count = 1;
            }
        }
        output.append(count).append(c);
        return helper(output.toString(), start + 1, end);
    }

    // iterative method
    public String countAndSay2(int n) {
        StringBuilder sb = new StringBuilder("1");
        StringBuilder pre = new StringBuilder(); // 用pre来存之前的sb

        for (int i = 1 ; i < n; i++){
            pre = sb;
            sb = new StringBuilder();
            int count = 1;
            char c = pre.charAt(0);

            for (int j = 1; j < pre.length(); j++){
                if (pre.charAt(j) != c){
                    sb.append(count).append(c);
                    count  = 1;
                    c = pre.charAt(j);
                } else count++;
            }
            sb.append(count).append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(countAndSay1(3));
    }

}
