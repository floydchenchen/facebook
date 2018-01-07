/*
* 208. Implement Trie (Prefix Tree)
* https://leetcode.com/problems/implement-trie-prefix-tree/description/
*
* Implement a trie with insert, search, and startsWith methods.
*
* Note:
* You may assume that all inputs are consist of lowercase letters a-z.
*
* */

package tag;

public class implement_trie {
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

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode wordStart = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (wordStart.children[c - 'a'] == null) {
                wordStart.children[c - 'a'] = new TrieNode(c);
            }
            wordStart = wordStart.children[c - 'a'];
        }
        wordStart.isWord = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode wordStart = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (wordStart.children[c - 'a'] == null) return false;
            wordStart = wordStart.children[c - 'a'];
        }
        return wordStart.isWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    // 判断在过程中会不遇到children list中的下一个char对应Null的TrieNode，如果是，return false
    public boolean startsWith(String prefix) {
        TrieNode wordStart = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (wordStart.children[c - 'a'] == null) return false;
            wordStart = wordStart.children[c - 'a'];
        }
        return true;
    }
}
