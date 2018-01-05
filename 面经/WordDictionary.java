class TrieNode {
    public char val;
    public boolean isWord;
    public TrieNode[] children = new TrieNode[26];
    public TrieNode() {}
    TrieNode(char c){
        this.val = c;
    }
}

public class WordDictionary {

    private TrieNode root = new TrieNode(' ');

    /** Adds a word into the data structure. */
    public void addWord(String word) {
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

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return regexSearch(word.toCharArray(), 0, root);
    }

    public boolean regexSearch(char[] chars, int index, TrieNode node) {
        if (index == chars.length) return node.isWord;
        char c = chars[index];
        if (c != '.') {
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

    public static void main(String[] args) {
        WordDictionary w = new WordDictionary();
        w.addWord("at");
        w.addWord("and");
        w.addWord("an");
        w.addWord("add");
        w.addWord("bat");
        System.out.println(w.search(".at"));
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */