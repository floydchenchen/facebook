/*
*
* 340. Longest Substring with At Most K Distinct Characters
* https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/
*
* Given a string, find the length of the longest substring T that contains at most k distinct characters.
* For example, Given s = “eceba” and k = 2,
* T is "ece" which its length is 3.
*
* */

package 非tag;

import java.util.HashMap;
import java.util.Map;

public class longest_substring_with_k_distinct_characters {
    // time: O(n), space: O(1)，因为char的数量是 <= 128的
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        // map: char -> char的数量
        Map<Character, Integer> map = new HashMap<>();
        // counter: 有多少个不一样的char
        int start = 0, end = 0, counter = 0, length = 0;
        while (end < s.length()) {
            char c1 = s.charAt(end);
            // 把当前的char放到map中
            map.put(c1, map.getOrDefault(c1, 0) + 1);
            if (map.get(c1) == 1) counter++; // new char
            end++;
            while (counter > k) {
                char c2 = s.charAt(start);
                map.put(c2, map.get(c2) - 1);
                if (map.get(c2) == 0) counter--;
                start++;
            }
            length = Math.max(length, end - start);
        }
        return length;
    }

    /*
    *
    * 变种：返回string
    *
    * */
    public static String longestSubstringKDistinct(String s, int k) {
        String result = "";
        // map: char -> char的数量
        Map<Character, Integer> map = new HashMap<>();
        // counter: 有多少个不一样的char
        int start = 0, end = 0, counter = 0, length = 0;
        while (end < s.length()) {
            char c1 = s.charAt(end);
            // 把当前的char放到map中
            map.put(c1, map.getOrDefault(c1, 0) + 1);
            if (map.get(c1) == 1) counter++; // new char
            end++;
            while (counter > k) {
                char c2 = s.charAt(start);
                map.put(c2, map.get(c2) - 1);
                if (map.get(c2) == 0) counter--;
                start++;
            }
            if (end - start > length) {
                result = s.substring(start, end);
                length = end - start;
            }
        }
        return result;
    }

    /*
    *    简单版
    * */

    // longest substring with at most 2 distinct characters
    // 只用把k distinct中的 while (counter > k)改成 counter > 2
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int start = 0, end = 0, counter = 0, length = 0;
        while (end < s.length()) {
            char c1 = s.charAt(end);
            map.put(c1, map.getOrDefault(c1, 0) + 1);
            if (map.get(c1) == 1) counter++; // new char
            end++;
            while (counter > 2) {
                char c2 = s.charAt(start);
                map.put(c2, map.get(c2) - 1);
                if (map.get(c2) == 0) counter--;
                start++;
            }
            length = Math.max(length, end - start);
        }
        return length;
    }

    // longest substring without repeating characters
    // 只用把k distinct中的 while (counter > k)改成 counter > 1
    public static int lengthOfLongestSubstringWithoutRepeatingChars(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int start = 0, end = 0, counter = 0, length = 0;
        while (end < s.length()) {
            char c1 = s.charAt(end);
            map.put(c1, map.getOrDefault(c1, 0) + 1);
            if (map.get(c1) == 1) counter++; // new char
            end++;
            while (counter > 1) {
                char c2 = s.charAt(start);
                map.put(c2, map.get(c2) - 1);
                if (map.get(c2) == 0) counter--;
                start++;
            }
            length = Math.max(length, end - start);
        }
        return length;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstringWithoutRepeatingChars("ecebba"));
    }
}
