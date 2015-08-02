package cz.hartrik.anagram.solve;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @version 2015-08-02
 * @author Patrik Harag
 */
public class PermutationsTest {

    @Test
    public void testExact() {
        List<String> expected =
                Arrays.asList("123", "132", "213", "231", "312", "321");

        Set<String> permutations = PermutationsGenerator.generate("123");

        assertThat(permutations.size(), equalTo(expected.size()));
        assertThat(permutations.containsAll(expected), equalTo(true));
    }

    @Test
    public void testLongWord() {
        assertEquals(604800, PermutationsGenerator.generate("lokomotiva").size());
    }

}