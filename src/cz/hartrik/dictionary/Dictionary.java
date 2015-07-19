package cz.hartrik.dictionary;

import java.util.Collection;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Slovník, který k uložení dat umožňuje použít libovolnou kolekci.
 *
 * @version 2015-07-19
 * @author Patrik Harag
 * @param <T> typ kolekce použitý na uložení slovníku
 */
public class Dictionary<T extends Collection<String>>
        implements IDictionary {

    public static final int MAX_LENGTH = 20;

    protected final ClosedArrayList<T> dictionaries;
    protected final String name;

   /**
    * Vytvoří novou instanci slovníku.
    *
    * @param name jméno slovníku
    * @param function funkce vracející prázdnou kolekci pro uložení slov s
    *                 určitou délkou
    */
    public Dictionary(String name, IntFunction<T> function) {
        this.name = name;
        this.dictionaries = new ClosedArrayList<>(MAX_LENGTH + 1);

        for (int i = 0; i < dictionaries.capacity(); i++)
            dictionaries.add(function.apply(i));
    }

    // IDictionary

    @Override
    public T getPart(int length) {
        int i = (length > MAX_LENGTH || length <= 0) ? 0 : length;
        return dictionaries.get(i);
    }

    @Override
    public T getPart(String word) {
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
        return dictionaries.stream().mapToInt(e -> e.size()).sum();
    }

    @Override
    public String name() {
        return name;
    }

    // Object

    @Override
    public String toString() {
        return "[" + getClass().getName() + "] "
                + IntStream.range(0, dictionaries.capacity())
                    .mapToObj(i -> i + "=" + dictionaries.get(i).size())
                    .collect(Collectors.joining(", "));
    }

}