package cz.hartrik.anagram.solve;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @version 2015-01-27
 * @author Patrik Harag
 */
public final class PermutationsGenerator {

    private PermutationsGenerator() { }
    
    public static Set<String> generate(String string) {
        final Set<String> hashSet = new LinkedHashSet<>();

        permutations("", string, hashSet);
        return hashSet;
    }
    
    private static void permutations(String actual, String remaining,
            Set<String> output){

        final int lenght = remaining.length();

        if (lenght == 0) {
            output.add(actual);

        } else {
            for (int i = 0; i < lenght; i++) {
                String a = actual + remaining.charAt(i);
                String r = remaining.substring(0, i)
                         + remaining.substring(i + 1, lenght);

                permutations(a, r, output);
            }
        }
    }

}