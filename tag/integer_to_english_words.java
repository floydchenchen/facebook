/*
*
* 273. Integer to English Words
* https://leetcode.com/problems/integer-to-english-words/?tab=Description
*
* */

package tag;

public class integer_to_english_words {

    /*
    *
    * 简单版：roman to integer 和 integer to roman
    *
    * 罗马数字对应数字：
    * I: 1
    * V: 5
    * X: 10
    * L: 50
    * C: 100
    * D: 500
    * M: 1000
    * 根据一、十、百、千来存四个list（相当于本系列题的一个table）
    *
    * */
    // integer to roman
    public static String intToRoman(int num) {
        String M[] = {"", "M", "MM", "MMM"};
        String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return M[num/1000] + C[(num%1000)/100] + X[(num%100)/10] + I[num%10];
    }

    // roman to integer
    public int romanToInt(String s) {
        if (s == null || s.length() == 0) return 0;
        int sum = 0;
        // because we compare arr[i] and arr[i+1], only need to second last element
        // 如果current char >= next char, sum+= current
        // 如果current char < next char, sum -= current
        for (int i = 0; i < s.length() - 1; i++) {
            int current = table(s.charAt(i));
            int next = table(s.charAt(i+1));
            if (current < next)  {
                sum -= current;
            } else {
                sum += current;
            }
        }
        // don't forget to add the last element to sum
        sum += table(s.charAt(s.length()-1));
        return sum;
    }
    private int table(char a){
        switch(a){
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }

    /*
    *
    * integer to english words
    *
    * 思路：
    * 像这个系列的题一样，得有一个table或者几个list去存数字到英文的值，
    * 本题中存三个list，注意不包含"zero"，因为“zero”是特殊情况，只有当数为0的时候才会用到
    *   小于二十: 1~19
    *   十: 10~90
    *   千: 1000, 100,000, 100,000,000
    *
    * 因为这个方法是O(1)的，所以不需要用StringBuilder
    * 在主方法里面检查thousands
    * 在helper方法里面检查小于二十，十和百（加上"Hundred"）
    *
    * */
    // 注意一定需要存空的String，以满足“0”
    private final String[] LESS_THAN_20 = {
            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private final String[] TENS = {
            "", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};

    public String numberToWords(int num) {
        if (num == 0) return "Zero";
        int i = 0;
        String words = "";

        // 在主方法里面check for thousands
        while (num > 0) {
            if (num % 1000 != 0) words = helper(num % 1000) + THOUSANDS[i] + " " + words;
            num /= 1000;
            i++;
        }
        return words.trim(); // important，一定要trim!不然最后会多一个" "
    }
    // 用helper method check for hundres, tens and ones
    private String helper(int num) {
        if (num == 0) return "";
        else if (num < 20) return LESS_THAN_20[num] + " ";
        else if (num < 100) return TENS[num / 10] + " " + helper(num % 10);
        else return LESS_THAN_20[num / 100] + " Hundred " + helper(num % 100);
    }
}
