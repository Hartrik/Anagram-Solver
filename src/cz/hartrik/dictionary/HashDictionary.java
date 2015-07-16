package cz.hartrik.dictionary;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Třída implenentující slovník.
 * 
 * @version 2015-02-02
 * @author Patrik Harag
 */
public class HashDictionary implements IDictionary {

    public static final int MAX_LENGTH = 20;
    
    @SuppressWarnings("unchecked")
    protected final Set<String>[] dictionaries = new Set[MAX_LENGTH + 1];
    protected final String name;

    public HashDictionary(String name) {
        this.name = name;
        
        for (int i = 0; i < dictionaries.length; i++)
            dictionaries[i] = new LinkedHashSet<>();
    }

    @Override
    public Set<String> getPart(int length) {
        int i = (length > MAX_LENGTH || length <= 0) ? 0 : length;
        return dictionaries[i];
    }

    @Override
    public Set<String> getPart(String word) {
        return getPart(word.length());
    }
    
    @Override
    public void add(String word) {
        getPart(word.length()).add(word);
    }

    @Override
    public void addAll(Collection<String> words) {
        for (String word : words) add(word);
    }

    @Override
    public boolean contains(String word) {
        return getPart(word.length()).contains(word);
    }
    
    @Override
    public long size() {
        return Arrays.stream(dictionaries).mapToInt(e -> e.size()).sum();
    }
    
    @Override
    public String toString() {
        return "[" + getClass().getName() + "] "
                + IntStream.range(0, dictionaries.length)
                    .mapToObj(i -> i + "=" + dictionaries[i].size())
                    .collect(Collectors.joining(", "));
    }

    @Override
    public String name() {
        return name;
    }
    
}