import java.util.*;

public class alien_dictionary {
    public static String alienOrder(String[] words) {
        StringBuilder sb = new StringBuilder();

        // build binary map, map<c, set>: the set of chars that occurs after c
        Map<Character, Set<Character>> map = new HashMap<>();
        // degree存的是这个char c有多少个prerequisite
        Map<Character, Integer> degree = new HashMap<>();
        if (words == null || words.length == 0) return sb.toString();
        // initialize the degree map
        for (String word : words) {
            for (char c: word.toCharArray()) {
                degree.put(c, 0);
            }
        }

        // compare two words
        for (int i = 0; i < words.length - 1; i++) {
            String x = words[i], y = words[i+1];
            // compare two characters
            int minLength = Math.min(x.length(), y.length());
            for (int j = 0; j < minLength; j++) {
                char c1 = x.charAt(j), c2 = y.charAt(j);
                // if c1 != c2, then c1 occurs before c2
                if (c1 != c2) {
                    map.putIfAbsent(c1, new HashSet<>());
                    if(!map.get(c1).contains(c2)) {
                        map.get(c1).add(c2);
                        degree.put(c2, degree.get(c2) + 1);
                    }
                    // 这个break很重要，例如words = ["za","zb","ca","cb"]
                    // x = "zb", y = "ca"，从"za", "zb"我们得到 a < b
                    // 从目前的x和y我们得到 z < c，如果不break继续比较的话
                    // 我们会得到 b < a，和前面contradict了
                    break;
                }
            }
        }

        // solve csp

        // first build and initialize the queue
        Queue<Character> queue = new LinkedList<>();
        for (char c : degree.keySet()) {
            // if not char occurs before c, add c to queue
            if (degree.get(c) == 0) queue.add(c);
        }

        // then use BFS to solve csp
        while (!queue.isEmpty()) {
            char c = queue.poll();
            sb.append(c);
            System.out.println("sb: " + sb.toString());
            if (map.containsKey(c)) {
                for (char c2 : map.get(c)) {
                    degree.put(c2, degree.get(c2) - 1);
                    if (degree.get(c2) == 0) queue.add(c2);
                }
            }
        }
        return sb.length() == degree.size() ? sb.toString() : "";
    }

    public static void main(String[] args) {
        String[] words = {"wrt","wrf","er","ett","rftt"};
        System.out.println(alienOrder(words));
    }
}
