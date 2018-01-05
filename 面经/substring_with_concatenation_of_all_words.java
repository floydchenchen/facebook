import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class substring_with_concatenation_of_all_words {
    public static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<Integer>();
        if (s == null || words == null || words.length == 0) return result;
        int wordLen = words[0].length(), strLen = s.length(), listLen = words.length; // length of each word

        // initialize the map for words
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        // loop through every char in s
        for (int i = 0; i <= strLen - wordLen * listLen; i++) {
            Map<String, Integer> seen = new HashMap<>();
            // check every word in words
            int j = 0;
            while (j < listLen) {
                String word = s.substring(i + j * wordLen, i + (j +1) * + wordLen);
                if (map.containsKey(word)) {
                    seen.put(word, seen.getOrDefault(word, 0) + 1);
                    if (seen.get(word) > map.getOrDefault(word, 0)) break;
                } else break;
                j++;
            }
            if (j == listLen) result.add(i);
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "barfoothefoobarman";
        String[] words = {"foo", "bar"};
        System.out.println(findSubstring(s, words));
    }
}
