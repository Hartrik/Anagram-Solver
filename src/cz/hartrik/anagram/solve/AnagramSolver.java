package cz.hartrik.anagram.solve;

import cz.hartrik.dictionary.IDictionary;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Řeší přesmyčky.
 *
 * @version 2015-08-02
 * @author Patrik Harag
 */
public class AnagramSolver {

    private final String anagram;
    private final int anagramLength;

    private boolean ignoreDiacritics;

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
     * @param dictionary slovník
     * @return množina slov odpovídající přesmyčce
     */
    public Set<String> search(IDictionary dictionary) {
        return search(dictionary.getPart(anagramLength));
    }

    /**
     * Řeší přesmyčku.
     *
     * @param collection kolekce slov
     * @return množina slov odpovídající přesmyčce
     */
    public Set<String> search(Collection<String> collection) {
        return search(collection.parallelStream());
    }

    /**
     * Řeší přesmyčku.
     *
     * @param dictionaryStream proud slov
     * @return množina slov odpovídající přesmyčce
     */
    public Set<String> search(Stream<String> dictionaryStream) {
        final char[] normalizedAnagram = normalized(anagram);

        return dictionaryStream
                .filter(word -> equals(normalizedAnagram, normalized(word)))
                .collect(Collectors.toSet());
    }

    /**
     * Pokud bude nastaveno na {@code true}, diakritika bude ignorována.
     *
     * @param ignoreDiacritics ignorovat diakritiku
     */
    public void setIgnoreDiacritics(boolean ignoreDiacritics) {
        this.ignoreDiacritics = ignoreDiacritics;
    }

    // --- pomocné metody

    protected final char[] normalized(String word) {
        final char[] charArray = (ignoreDiacritics)
                ? removeAccents(word).toCharArray()
                : word.toCharArray();

        Arrays.sort(charArray);
        return charArray;
    }

    protected final String removeAccents(String text) {
        return Normalizer.normalize(text, Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    protected final boolean equals(char[] a1, char[] a2) {
        if (anagramLength != a2.length)
            return false;

        for (int i = 0; i < anagramLength; i++)
            if (a1[i] != a2[i]) return false;

        return true;
    }

}