import java.util.ArrayList;
import java.util.List;

public class text_justification {
    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        if(words.length == 0 || maxWidth == 0) {
            result.add(""); //for some reason OJ expects list with empty string for empty array input
            return result;
        }

        for(int i = 0, w; i < words.length; i = w) {
            int len = -1; //We need to skip the space for last word hence start len = -1
            //check how many words fit into the line
            for(w = i; w < words.length && len + words[w].length() + 1 <= maxWidth; w++) {
                len += words[w].length() + 1; // 1 extra for the space
            }

            //calculate the number of extra spaces that can be equally distributed
            //also calculate number of extra spaces that need to be added to first few
            //words till we fill the line width
            //For example line width is 20 we have three words of 3 4 2 4 length
            //[our_,life_,is_,good_,_,_,_,_,] ==> [our_,_,_,life_,_,_is_,_,good]
            //   Note _, indicates space
            //Count number of empty spaces at end of line:= width-len = 20-(15) = 5
            //These five spaces need to be equally distributed between 4-1 = 3 gaps
            //n words will have n-1 gaps between them
            // 5 / 3 = 1 extra space between each word (in addition to default 1 space,
            //                                          total space count = 2)
            // 5 % 3 = 2 extra spaces between first three words as shown above

            int evenlyDistributedSpaces = 1; //If we don't enter loop at line # 37 then we need to have default value
            int extraSpaces = 0;
            int numOfGapsBwWords = w-i-1; //w is already ponting to next index and -1 since
            //n words have n-1 gaps between them

            //Moreover we don't need to do this computation if we reached the last word
            //of array or there is only one word that can be accommodate on the line
            //then we don't need to do any justify text. In both cases text can be left,
            //left-aligned

            if(w != i+1 && w != words.length) {
                //additional 1 for the default one space between words
                evenlyDistributedSpaces = ((maxWidth-len) / numOfGapsBwWords) + 1;
                extraSpaces = (maxWidth-len)%numOfGapsBwWords;
            }

            StringBuilder sb = new StringBuilder(words[i]);
            for(int j = i+1; j < w; j++) {
                for(int s = 0; s < evenlyDistributedSpaces; s++) {
                    sb.append(' ');
                }
                if(extraSpaces > 0) {
                    sb.append(' ');
                    extraSpaces--;
                }
                sb.append(words[j]);
            }

            //Handle the above two cases we skipped, where there is only one word on line
            //or we reached end of word array. Last line should remain left aligned.
            int remaining = maxWidth-sb.length();
            while(remaining > 0) {
                sb.append(' ');
                remaining--;
            }
            result.add(sb.toString());
        }
        return result;
    }

    public static void main(String[] args) {
        String[] words = {"This", "is", "an", "example", "of", "text", "justification."};
        System.out.println(fullJustify(words, 16).toString());
    }
}
