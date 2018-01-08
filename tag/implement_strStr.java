/*
*
* 28. Implement strStr()
* https://leetcode.com/problems/implement-strstr/description/
*
* Implement strStr().
* Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
*
* Example 1:
*   Input: haystack = "hello", needle = "ll"
*   Output: 2
*
* Example 2:
*   Input: haystack = "aaaaa", needle = "bba"
*   Output: -1
*
* string matching的题
* */

package tag;

import java.util.*;

public class implement_strStr {
    // time: O(hn), space: O(1)
    public static int strStr1(String haystack, String needle) {
        int h = haystack.length(), n = needle.length();
        // 很重要！如果needle是empty string，那么他从一开始就可以match
        if (n == 0) return 0;
        // i: haystack pointer, 只需要达到两者只差就可以了
        for (int i = 0; i <= h - n; i++) {
            // j: needle pointer, needle[j]和haystack[i+j]必须match
            // for loop中间condition的意思：如果不满足条件，直接break
            for (int j = 0; j < n && needle.charAt(j) == haystack.charAt(i + j); j++) {
                if (j == needle.length() - 1) return i;
            }
        }
        return -1;
    }


    /*
    *
    * 变种1：
    * find the first index in haystack that starts with an anagram[相同字母异序词] of needle
    * assume only lowercase letters in strings
    *
    * strstrp("hello", ''ell") returns 1
    * strstrp("hello",  "lle") returns 1
    * strstrp("hello",  "wor") returns -1
    *
    * 思路：
    * 先把needle中的每个char对应的个数放到map中
    * 每次循环新建一个tempMap = (HashMap)map.clone();
    * 每次match成功，update tempMap, counter++;
    * 最后如果counter == n, return i
    * */
    public static int strStr2(String haystack, String needle) {
        int h = haystack.length(), n = needle.length(), counter = 0;
        if (n == 0) return 0;
        // 先把needle中的每个char对应的个数放到map中
        HashMap<Character, Integer> map = new HashMap<>(), tempMap = null;
        for (char c : needle.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i <= h - n; i++) {
            // 每次循环新建一个tempMap = (HashMap)map.clone(), 把counter归零
            tempMap = (HashMap)map.clone();
            counter = 0;
            while (counter < n) {
                char c = haystack.charAt(i + counter);
                if (tempMap.containsKey(c) && tempMap.get(c) > 0) {
                    tempMap.put(c, tempMap.get(c) - 1);
                    counter++;
                }
                else break;
            }
            if (counter == n) return i;
        }
        return -1;
    }

    /*
    *
    * 变种2：2D haystack
    *
    * */
//    public static int strStr2D(List<List<Character>> haystack, String needle) {
//        int total = 0;
//        for (int row = 0; row < haystack.size(); row++) {
//            for (int col = 0; col < haystack.get(row).size(); col++) {
//                int k = 0;
//                int posRow = row;
//                int posCol = col;
//                while ((posRow < haystack.size() && posCol < haystack.get(posRow).size()) && k < needle.length() && haystack.get(posRow).get(posCol) == needle.charAt(k)) {
//                    k++;
//                    posCol++;
//                    if (posCol == haystack.get(posRow).size()) {
//                        posCol = 0;
//                        posRow++;
//                    }
//                }
//                if (k == needle.length()) {
//                    return total + col - 1;
//                }
//            }
//            total += haystack.get(row).size();
//        }
//        return -1;
//    }


    public static void main(String[] args) {
        System.out.println(strStr2("hello", "lll"));
//        char[] c1 = new char[]{'a','a','a'};
//        List<Character> list1 = new ArrayList(Arrays.asList(c1));
//        char[] c2 = new char[]{'b','b','b'};
//        List<Character> list2 = new ArrayList(Arrays.asList(c2));
//        char[] c3 = new char[]{'c','c','c'};
//        List<Character> list3 = new ArrayList(Arrays.asList(c3));
//        List<List<Character>> haystack = new ArrayList<>();
//        haystack.add(list1);
//        haystack.add(list2);
//        haystack.add(list3);
//        System.out.println(strStr2D(haystack, "abc"));
    }
}
