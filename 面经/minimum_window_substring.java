import java.util.HashMap;
import java.util.Map;

public class minimum_window_substring {
    public static String minWindow(String text, String pattern) {
        if(pattern.length() > text.length()) return "";
        // initialize the map
        Map<Character, Integer> map = new HashMap<>();
        for (char c : pattern.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int start = 0, end = 0, counter = map.size(), minStart = 0, minLength = Integer.MAX_VALUE;
        while (end < text.length()) {
            char c1 = text.charAt(end);
            if(map.containsKey(c1)) {
                map.put(c1, map.get(c1) - 1);
                if (map.get(c1) == 0) counter--;
            }
            end++;

            while (counter == 0) {
                char c2 = text.charAt(start);
                // update minLength and minStart
                if (minLength > end - start) {
                    minLength = end - start;
                    minStart = start;
                }

                if(map.containsKey(c2)) {
                    map.put(c2, map.get(c2) + 1);
                    if(map.get(c2) > 0) counter++;
                }
                start++;
            }
        }
        return minLength == Integer.MAX_VALUE ? "" : text.substring(minStart, minStart + minLength);
    }

    public static void main(String[] args) {
        String pattern = "A";
        String text = "BABbbbbbaB";
        System.out.println(minWindow(text, pattern));
    }
}
