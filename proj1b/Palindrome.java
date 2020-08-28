/**
 * This class is a tool to check the properties of text.
 * @author Ziyu Cheng
 */
public class Palindrome {
    /**
     * Transform a string into a deque.
     * @param word string to be transformed
     * @return deque of the string
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> queWord = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            queWord.addLast(word.charAt(i));
        }
        return queWord;
    }

    /**
     * Test whether a string of word is a palindrome.
     * @param word the string to be tested
     * @return true if it is, false otherwise
     */
    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        } else if (word.length() == 2) {
            return word.charAt(0) == word.charAt(1);
        } else {
            if (word.charAt(0) != word.charAt(word.length() - 1)) {
                return false;
            } else {
                return isPalindrome(word.substring(1, word.length() - 1));
            }
        }
    }

    /**
     * Generalized palindrome testing, we can define our own char equality.
     * @param word the string to be tested
     * @param cc the comparator in which defines char equality
     * @return true if it is, false otherwise
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1) {
            return true;
        } else if (word.length() == 2) {
            return cc.equalChars(word.charAt(0), word.charAt(1));
        } else {
            if (!cc.equalChars(word.charAt(0),
                    word.charAt(word.length() - 1))) {
                return false;
            } else {
                return isPalindrome(word.substring(1, word.length() - 1), cc);
            }
        }
    }
}
