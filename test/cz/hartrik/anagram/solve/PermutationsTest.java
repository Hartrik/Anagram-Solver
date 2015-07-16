package cz.hartrik.anagram.solve;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Patrik Harag
 */
public class PermutationsTest {

    @Test
    public void testPermutations() {
        assertEquals(604800, PermutationsGenerator.generate("lokomotiva").size());
    }

}
