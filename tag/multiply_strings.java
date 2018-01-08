/*
*
* 43. Multiply Strings
* https://leetcode.com/problems/multiply-strings/description/
*
* Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2
*
* 具体分析见 DOC/43. multiply strings
*
* 思路：
* num1[i] * num2[j] will be placed at indices [i + j, i + j + 1]
*
* */

package tag;

public class multiply_strings {
    // time: O(mn)
    public static String multiply(String num1, String num2) {
        int m = num1.length(), n = num2.length();
        int[] pos = new int[m + n];

        for(int i = m - 1; i >= 0; i--) {
            for(int j = n - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int index1 = i + j, index2 = i + j + 1;
                // temp sum of unit digit of new mul and old value in pos[index2]
                int sum = mul + pos[index2];

                pos[index1] += sum / 10; // tenth digit of every mul
                pos[index2] = (sum) % 10; // unit digit of every mul
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int p : pos) {
            // !(sb.length() == 0 && p == 0) to make sure we can get rid of the 0 at the beginning
            if(!(sb.length() == 0 && p == 0)) sb.append(p);
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
