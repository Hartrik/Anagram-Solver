package cz.hartrik.dictionary;

import java.util.ArrayList;
import java.util.Collection;

/**
 * {@link ArrayList} s maximální kapacitou.
 *
 * @version 2015-07-19
 * @author Patrik Harag
 * @param <T> typ
 */
public class ClosedArrayList<T> extends ArrayList<T> {

    private static final long serialVersionUID = 3453674986055897459L;

    private final int capacity;

    public ClosedArrayList(int capacity) {
        super(capacity);

        this.capacity = capacity;
    }

    @Override
    public boolean add(T e) {
        if (size() + 1 > capacity) throw new RuntimeException();

        return super.add(e);
    }

    @Override
    public void add(int index, T element) {
        if (size() + 1 > capacity) throw new RuntimeException();

        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends T> coll) {
        if (size() + coll.size() > capacity) throw new RuntimeException();

        return super.addAll(coll);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> coll) {
        if (size() + coll.size() > capacity) throw new RuntimeException();

        return super.addAll(index, coll);
    }

    public int capacity() {
        return capacity;
    }

}