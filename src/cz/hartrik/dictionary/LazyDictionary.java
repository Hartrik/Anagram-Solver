package cz.hartrik.dictionary;

import java.util.Collection;
import java.util.function.IntFunction;

/**
 * Slovník, který je načítaný po jednotlivých částech tak, jak je potřeba.
 *
 * @version 2015-07-19
 * @author Patrik Harag
 * @param <T> typ kolekce
 */
public abstract class LazyDictionary<T extends Collection<String>>
        extends Dictionary<T> implements ILazyDictionary {

    protected final boolean[] loaded = new boolean[MAX_LENGTH + 1];

    public LazyDictionary(String name, IntFunction<T> function) {
        super(name, function);
    }

    @Override
    public void loadPart(int i) {
        if (!loaded[i]) {
            loadPartImpl(i);
            loaded[i] = true;
        }
    }

    protected abstract void loadPartImpl(int i);

    @Override
    public T getPart(int length) {
        int i = (length > MAX_LENGTH || length <= 0) ? 0 : length;

        synchronized (this) {
            if (!loaded[i]) {
                loadPart(i);
                loaded[i] = true;
            }
        }

        return dictionaries.get(i);
    }

    @Override
    public void loadAllParts() {
        for (int i = 0; i <= MAX_LENGTH; i++) {
            loadPart(i);
        }
    }

}