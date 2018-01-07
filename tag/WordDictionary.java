/*
*
* 211. Add and Search Word - Data structure design
* https://leetcode.com/problems/add-and-search-word-data-structure-design/description/
*
* Design a data structure that supports the following two operations:
*   void addWord(word)
*   bool search(word)
*
* search(word) can search a literal word or a regular expression string containing only letters a-z or '.'
* A '.' means it can represent any one letter.
*
* You may assume that all words are consist of lowercase letters a-z.
*
* 1. 因为支持regular expression，所以存的时候一个TrieNode存一个字母
* 2. 又因为有大量的word会被存进去，所以通过拆分的方式节省内存
* 3. 总之，每一个Node代表一个字母，然后这个node有26个TrieNode的分支，代表下一个字母，同时
* 这个TrieNode中还有一个Boolean值代表从root到此处是否是一个单词
* 4. 有一个''作为root
*
* 基本上我们建的树被称为"前缀树", prefix tree?
*
* */

package tag;

public class WordDictionary {

    class TrieNode {
        public char val;
        public boolean isWord;
        public TrieNode[] children = new TrieNode[26];
        public TrieNode() {}
        TrieNode(char c){
            this.val = c;
        }
    }

    private TrieNode root = new TrieNode();

    /** Adds a word into the data structure. */
    // O(n) time, O(n) space
    public void addWord(String word) {
        TrieNode wordStart = root;
        for (char c : word.toCharArray()) {
            // 别忘了用 - 'a'来获取children list的index
            // 如果此处为Null，新建一个TrieNode(c)
            if (wordStart.children[c - 'a'] == null) {
                wordStart.children[c - 'a'] = new TrieNode(c);
            }
            // 类似node = node.next
            wordStart = wordStart.children[c - 'a'];
        }
        wordStart.isWord = true;
    }

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    // O(26^'.') 26 to the number of . in the regex
    public boolean search(String word) {
        return regexSearch(word.toCharArray(), 0, root);
    }

    public boolean regexSearch(char[] chars, int index, TrieNode node) {
        if (index == chars.length) return node.isWord;
        char c = chars[index];
        // 判断此处的char是否是'.'去match任意字母
        if (c != '.') {
            // 同样别忘了 - 'a'，同时必须满足这个地方的TrieNode不为空！
            if (node.children[c - 'a'] != null) {
                return regexSearch(chars, index+1, node.children[c - 'a']);
            }
        }
        else { // c == '.'
            for (int i = 0; i < node.children.length; i++) {
                if (node.children[i] != null) {
                    // 这里不能直接return true, 因为这样会break出这个for loop，而需要用if... return true的方式
                    if (regexSearch(chars, index+1, node.children[i])) return true;
                }
            }
        }
        return false;
    }

    /*
    *
    * follow-up:
    * 如果模糊查询只有"...book"和“book...”这两种模式怎么处理，回答，只要建立顺序Trie树，反序Trie树，就可以了
    * 也就是一个postfix tree，相对于prefix tree
    * */

    /*
    *
    * follow-up: space和time的trade-off
    * 一是建树时不把'.'作为一个字符，而在搜索时碰到'.'时搜索所有儿子节点。另外一种是建树时把'.'加入到每个节点的儿子节点中，
    * 把所有包含'.'的字符串也存在Trie Tree中。两种做法的区别在于，前者空间复杂度低，每次add时间复杂度是字符串长度，
    * 每次search时间复杂度是O(26^'.'的个数)；后者空间复杂度高，每次add时间复杂度是2的字符串长度次方，每次search时间复杂度是字符串长度。
    *
    * */
}
