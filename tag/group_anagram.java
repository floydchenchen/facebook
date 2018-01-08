/*
*
* 49. Group Anagrams
* https://leetcode.com/problems/group-anagrams/description/
*
* Given an array of strings, group anagrams together.
*
* For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"],
* Return:
* [
*   ["ate", "eat","tea"],
*   ["nat","tan"],
*   ["bat"]
* ]
*
* */

package tag;

import java.util.*;

public class group_anagram {
    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            if (!map.containsKey(key)) map.put(key, new ArrayList<String>());
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        String[] arr = {"eat","tea","tan","ate","nat","bat"};
        System.out.println(groupAnagrams(arr));
    }
}
