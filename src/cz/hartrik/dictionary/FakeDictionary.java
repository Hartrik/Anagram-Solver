package cz.hartrik.dictionary;

import cz.hartrik.common.FakeCollection;
import java.util.Collection;
import java.util.stream.IntStream;

/**
 * Podává informace o slovníku i bez jeho načtení.
 *
 * @version 2015-02-02
 * @author Patrik Harag
 */
public class FakeDictionary implements IDictionary {
    
    private final String name;
    private final int[] sizes;

    public FakeDictionary(String name, int[] sizes) {
        this.name = name;
        this.sizes = sizes;
    }
    
    @Override
    public String name() {
        return name;
    }

    @Override
    public void add(String word) { }

    @Override
    public void addAll(Collection<String> words) { }

    @Override
    public boolean contains(String word) {
        return false;
    }

    @Override
    public long size() {
        return IntStream.of(sizes).sum();
    }

    @Override
    public Collection<String> getPart(int length) {
        return new FakeCollection<>(sizes[length > 20 ? 0 : length]);
    }
    
}