/*
*
* 76. Minimum Window Substring
* https://leetcode.com/problems/minimum-window-substring/
*
* Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
*
* example:
* S = "ADOBECODEBANC"
* T = "ABC"
* Minimum window is "BANC".
*
* two pointers: start and end to represent a window.
* Move end to find a valid window.
* When a valid window is found, move start to find a smaller window.
*
* 不要用hash map存pattern的char，用int[128]就行了，因为unicode 0~127就包含了基本的拉丁字母和符号了，hash map反而更复杂
* map里存的是window中待include的char的数量
* counter: the number of chars of pattern to be found in text
*
* (while end < text.length())从end = 0 到 text.length()扫描：
*   if 扫描的endChar对应map的值>0，将它的值-1, counter--
*   让map里对应的endChar的value-1，（map中对应的char如果是pattern的char，不会<0，如果不对应则会<0）
*   end++，相当于window变大，所以待include的数量减少
*   (while counter == 0)，说明在这个[start, end]区间内有一个valid window
*       update minLength与minStart
*       让map里对应的startChar的value+1，（如果map里对应的char是pattern的char，此时它的数量会>0）
*       if 该value > 0, counter++
*       start++，相当于window变小，所以待include的数量增加
*
* */

package tag;

public class minimum_window_substring {
    // time: O(n), space: O(1)
    public static String minWindow(String text, String pattern) {
        // 0 to 128 in unicode represents all basic latin and other characters
        // http://www.ssec.wisc.edu/~tomw/java/unicode.html
        // so we don't really need to use a hash map
        int[] map = new int[128]; // 待include的char数量
        for (char c : pattern.toCharArray()) {
            map[c]++;
        }
        // counter: the number of chars in pattern to be found in text
        int start = 0, end = 0, counter = pattern.length(), minStart = 0, minLength = Integer.MAX_VALUE;
        while (end < text.length()) { // end指针的loop
            // 如果map中有pattern的char，counter--
            if (map[text.charAt(end)] > 0) counter--;
            // 只要end扫过，都--，注意如果text.charAt(end)是pattern的一个char，map[text.charAt(end)]不会小于0
            map[text.charAt(end)]--;
            end++;
            // counter == 0 说明在这个[start, end]区间内有一个valid window
            while (counter == 0) { // start指针的loop
                // update minLength and minStart (存这两个指针因为需要text.substring())
                if (minLength > end - start) {
                    minLength = end - start;
                    minStart = start;
                }
                // start扫过，又把end扫过的--的++回去，因为current char离开窗口了，又变成了待include的char
                map[text.charAt(start)]++;
                // 如果text.charAt(start)是pattern的一个char，map[text.charAt(end)]此时>=1
                // 这时候说明我们少了一个可以match的char，所以counter++
                if (map[text.charAt(start)] > 0) counter++;
                start++;
            }
        }
        return minLength == Integer.MAX_VALUE ? "" : text.substring(minStart, minStart + minLength);
    }
}
