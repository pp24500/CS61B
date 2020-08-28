import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();
    static CharacterComparator offByThree = new OffByN(3);

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
        Deque d2 = palindrome.wordToDeque("");
        String empty = "";
        for (int i = 0; i < "".length(); i++) {
            empty += d2.removeFirst();
        }
        assertEquals("", empty);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("reverse"));
        assertFalse(palindrome.isPalindrome("re"));
        assertFalse(palindrome.isPalindrome("rere"));

        assertTrue(palindrome.isPalindrome("cAc"));
        assertTrue(palindrome.isPalindrome("cc"));
        assertTrue(palindrome.isPalindrome("c"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("DAAD"));

        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertTrue(palindrome.isPalindrome("ab", offByOne));
        assertTrue(palindrome.isPalindrome("acb", offByOne));
        assertTrue(palindrome.isPalindrome("a", offByOne));
        assertTrue(palindrome.isPalindrome("zxyy", offByOne));
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome("axdcyb", offByOne));

        assertFalse(palindrome.isPalindrome("aaaa", offByOne));
        assertFalse(palindrome.isPalindrome("aaAB", offByOne));

        assertTrue(palindrome.isPalindrome("a", offByThree));
        assertTrue(palindrome.isPalindrome("", offByThree));
        assertTrue(palindrome.isPalindrome("ad", offByThree));
        assertTrue(palindrome.isPalindrome("abed", offByThree));
        assertTrue(palindrome.isPalindrome("awtzd", offByThree));

        assertFalse(palindrome.isPalindrome("ab", offByThree));
        assertFalse(palindrome.isPalindrome("xty", offByThree));
        assertFalse(palindrome.isPalindrome("xady", offByThree));
        assertFalse(palindrome.isPalindrome("vafcy", offByThree));
    }
}