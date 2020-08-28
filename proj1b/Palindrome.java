public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> queWord = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            queWord.addLast(word.charAt(i));
        }
        return queWord;
    }

    public boolean isPalindrome(String word) {
        /* ArrayDeque<Character> queWord = new ArrayDeque<>();
        *//* Get the deque of the word, but in reverse order. *//*
        for (int i = 0; i < word.length(); i++) {
            queWord.addFirst(word.charAt(i));
        }
        String reverse = "";
        for (int j = 0; j < word.length(); j++) {
            reverse += queWord.removeFirst();
        }
        return word.equals(reverse);*/
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

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1) {
            return true;
        } else if (word.length() == 2) {
            return cc.equalChars(word.charAt(0), word.charAt(1));
        } else {
            if (!cc.equalChars(word.charAt(0), word.charAt(word.length() - 1)) ) {
                return false;
            } else {
                return isPalindrome(word.substring(1, word.length() - 1), cc);
            }
        }
    }
}
