import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('y', 'z'));
        assertTrue(offByOne.equalChars('x', 'y'));
        assertTrue(offByOne.equalChars('&', '%'));
        assertTrue(offByOne.equalChars('a', 'b'));

        assertFalse(offByOne.equalChars('x', 'X'));
        assertFalse(offByOne.equalChars('x', 'x'));
        assertFalse(offByOne.equalChars('x', ' '));
    }
}
