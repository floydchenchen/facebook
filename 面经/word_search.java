public class word_search {
    public static boolean exist(char[][] board, String word) {
        char[] wordArray = word.toCharArray();
        // iteratively check every point on the board
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (exist(board, x, y, wordArray, 0)) return true;
            }
        }
        return false;
    }

//     i is the index of the word
//     3 edge cases:
//     1. if index reaches the end of the word, return true
//     2. if x or y reaches both end (0 & board.length && board[x].length) return false
//     3. if board[x][y] != word[i], return false
    private static boolean exist(char[][] board, int x, int y, char[] word, int i) {
        if (i == word.length) return true;
        if (x < 0 || y < 0 || x == board.length || y == board[x].length) return false;
        if (board[x][y] != word[i]) return false;
        for(int j = 0; j < board.length; j++) {
            for (int k = 0; k < board[0].length; k++) {
                System.out.print(board[j][k] + " ");
            }
            System.out.println();
        }
        System.out.println();
        board[x][y] = '#';
        boolean result = exist(board, x, y+1, word, i+1) || exist(board, x, y-1, word, i+1)
                || exist(board, x+1, y, word, i+1) || exist(board, x-1, y, word, i+1);
        return result;
    }

    public static void main(String[] args) {
//        int x = 7;
//        System.out.println(x ^= 256);
        char[][] board = {{'C','A','A'},{'A','A','A'},{'B','C','D'}};
        System.out.println(exist(board, "AAB"));
    }
}
