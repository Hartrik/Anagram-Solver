package cz.hartrik.anagram.solve;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Řeší přesmyčky.
 *
 * @version 2015-02-03
 * @author Patrik Harag
 */
public class AnagramSolver {
    
    private final String anagram;
    private final int anagramLength;
    
    private Set<String> results = new ConcurrentSkipListSet<>();

    /**
     * Vytvoří novou instanci.
     * 
     * @param anagram přesmyčka
     */
    public AnagramSolver(String anagram) {
        this.anagram = anagram;
        this.anagramLength = anagram.length();
    }
    
    /**
     * Řeší přesmyčku.
     * 
     * @param collection kolekce slov o stejné délce jako přesmyčka
     */
    public void search(Collection<String> collection) {
        search(collection.parallelStream());
    }
    
    /**
     * Řeší přesmyčku.
     * 
     * @param dictionaryStream proud slov o stejné délce, jako má přesmyčka
     */
    public void search(Stream<String> dictionaryStream) {
        final char[] normalizedAnagram = normalized(anagram);
        
        dictionaryStream
                .filter(word -> equals(normalizedAnagram, normalized(word)))
                .collect(Collectors.toCollection(() -> results));
    }

    public Set<String> getResults() {
        Set<String> ret = results;
        results = new LinkedHashSet<>();
        return ret;
    }
    
    // --- pomocné metody
    
    protected final char[] normalized(String word) {
        final char[] charArray = word.toCharArray();
        Arrays.sort(charArray);
        return charArray;
    }
    
    protected final boolean equals(char[] a1, char[] a2) {
        if (anagramLength != a2.length)
            return false;

        for (int i = 0; i < anagramLength; i++)
            if (a1[i] != a2[i]) return false;

        return true;
    }
    
}