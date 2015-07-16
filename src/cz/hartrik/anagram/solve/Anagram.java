package cz.hartrik.anagram.solve;

/**
 * Přesmyčka.
 *
 * @version 2015-02-03
 * @author Patrik Harag
 */
public class Anagram {
    
    public static String normalize(String anagram) {
        return anagram
//                .replaceAll("\\s+","")
                .trim()
                .toLowerCase();
    }
    
}