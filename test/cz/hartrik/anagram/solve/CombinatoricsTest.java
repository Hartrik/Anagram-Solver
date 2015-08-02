package cz.hartrik.anagram.solve;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @version 2015-08-02
 * @author Patrik Harag
 */
public class CombinatoricsTest {

    @Test
    public void testPermutations() {
        assertThat(Combinatorics.permutations("abc"), equalTo(6L));
    }

    @Test
    public void testPermutationsWithRepetition() {
        assertThat(Combinatorics.permutations("aaabc"), equalTo(20L));
        assertThat(Combinatorics.permutations("aabbcc"), equalTo(90L));
    }

    @Test
    public void testPermutationsExact() {
        assertThat(Combinatorics.permutationsExact("1234567890abcdefghij"), is(not(-1L)));
        assertThat(Combinatorics.permutationsExact("1234567890abcdefghijk"), equalTo(-1L));
    }

}