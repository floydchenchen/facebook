package tag;

public class valid_palindrome {
    // method 1: reverse，思路类似isValidParentheses
    // use regex to remove non-alphanumeric char
    // use StringBuffer to reverse string and with original string
    public boolean isPalindrome1(String s) {
        String actual = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        String rev = new StringBuffer(actual).reverse().toString();
        return actual.equals(rev);
    }

    // method 2: two pointers
    public boolean isPalindrome(String s) {
        // base cases
        if(s == null) return false;
        if(s.isEmpty()) return true;
        // two pointers
        int i = 0, j = s.length() - 1;
        while(i < j) {
            //Skip over non letters and digits
            while(!Character.isLetterOrDigit(s.charAt(i)) && i < j) i++;
            while(!Character.isLetterOrDigit(s.charAt(j)) && i < j) j--;

            //Convert characters to lowercase  before comparing
            String start = String.valueOf(s.charAt(i)).toLowerCase();
            String end =  String.valueOf(s.charAt(j)).toLowerCase();

            //If at any point they are not the same, it is not a palindrome;
            if(!start.equals(end)) return false;
            i++;
            j--;
        }
        return true;
    }

    /*
    *
    * valid palindrome II
    *
    * Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.
    *
    * */
    public boolean validPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j && s.charAt(i) == s.charAt(j)) {
            i++; j--;
        }

        if (i >= j) return true;

        if (isPalin(s, i + 1, j) || isPalin(s, i, j - 1)) return true;
        return false;
    }

    private boolean isPalin(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i) == s.charAt(j)) {
                i++; j--;
            }
            else return false;
        }
        return true;
    }
}
