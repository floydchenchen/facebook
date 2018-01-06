/*
*
* 17. Letter Combinations of a Phone Number
* Input:Digit string "23"
* Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
*
* String[] map = {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
*
*
* */

package tag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class letter_combination_of_a_phone_number {

    /* method 1: BFS
    *
    * 存一个map: index为数字，value对应chars
    * iteration中很重要的一点:
    *   while (queue.peek().length() == i)，这样保证BFS每次只看第i层
    *
    * time: O(m^n), n is the size of the string, m is the average length of strings in the map
    * space: O(m^n)
    *
    * */
    public static List<String> letterCombinations(String digits) {
        // queue implementation using linkedlist
        LinkedList<String> queue = new LinkedList<>();
        // base case
        if (digits == null || digits.length() == 0) return queue;
        // map的index对应的电话的数字
        String[] map = {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        // 首先在queue中加上一个""，这样i = 0时queue.peek().length() == 0
        queue.add("");
        for (int i = 0; i < digits.length(); i++) {
            int mapIndex = Character.getNumericValue(digits.charAt(i));
            // 让queue中的每一个element都保持i的长度（这样保证BFS到tree的i层的时候，只poll那一层的elements）
            while (queue.peek().length() == i) {
                String temp = queue.poll();
                for (char c : map[mapIndex].toCharArray()) {
                    queue.add(temp + c);
                }
            }
        }
        return queue;
    }

    /*
    *
    * method 2: DFS
    *
    * time: O(m^n), n is the size of the string, m is the average length of strings in the map
    * space: O(m^n)
    *
    * */
    private static final String[] map = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    public static List<String> letterCombinations2(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) return result;
        StringBuilder sb = new StringBuilder();
        DFS(sb, digits,0, result);
        return result;
    }
    private static void DFS(StringBuilder sb, String digits, int pos, List<String> result){
        // base case
        if (sb.length() >= digits.length()){
            result.add(sb.toString());
            return;
        }
        // 注意怎么从map里取！
        String s = map[digits.charAt(pos) - '0'];
        int len = sb.length();
        for (int i = 0 ; i < s.length(); i++){
            DFS(sb.append(s.charAt(i)), digits, pos + 1 , result);
            sb.setLength(len);
        }

        /*
        *
        * follow-up:
        * 第一个 follow up：如果今天给你一本字典，裡面有【几千万个单字】，你可以用任何数据结构储存这个字典，
        * 请问你怎麽从你的 letter combination 中找出 meaningful word? 举个例子，三个数字可能可以组成 “aaa”, “abc”, “dog"，
        * 那我们就只留下 “dog” ，因为只有 “dog” 是一个单词，楼主一开始提议用 set 储存，但是他说太 expensive ，后来改成用 trie ，
        * 然后找 prefix ，如果确定没有这个 prefix 就可以直接 pruning 了，面试官感觉很满意
        *
        * 第二个 follow up：如果今天不只要你找出一个单词的组成，连两个单词的组合都要找出来呢？
        * 比方说七个字的组成可以有 “element”(一个单词)，但也会有 “eatrice”(eat rice，两个单词) 这样的组成，能不能把这个也找出来呢？
        * 但问到这裡的时候时间已经剩很少，没什麽太多时间讨论，我一开始先提出可以用 word break 的想法判断是不是两个字典中的单词，
        * 但面试官说其实方法很简单，就是把 trie 的 tail 跟 root 相连，这样找完一个字之后就会接着回头找其他的字，
        * 结果也就会包含多个字的组合了，然后我提出了一些这个方法的好跟不好的地方，面试官也觉得不错，皆大欢喜之下结束．
        *
        * */


    }



    public static void main(String[] args) {
        List<String> list = letterCombinations2("345");
        System.out.println(list);
        System.out.println(list.size());
    }

}
