package cz.hartrik.dictionary;

import java.util.Set;

/**
 * Slovník, který je načítán po jednotlivých částech podle toho, jak je
 * používán.
 * 
 * @version 2015-02-02
 * @author Patrik Harag
 */
public abstract class HashDictionaryLazy extends HashDictionary {

    private final boolean[] loaded = new boolean[MAX_LENGTH + 1];

    public HashDictionaryLazy(String name) {
        super(name);
    }
    
    @Override
    public Set<String> getPart(int length) {
        int i = (length > MAX_LENGTH || length <= 0) ? 0 : length;
        
        synchronized (this) {
            if (!loaded[i]) {
                loadPart(i);
                loaded[i] = true;
            }
        }
        
        return dictionaries[i];
    }
    
    /**
     * Načte celý slovník.
     */
    public void loadAllParts() {
        for (int i = 0; i <= MAX_LENGTH; i++) {
            loadPart(i);
        }
    }
    
    /**
     * Načte určitou část slovníku.
     * 
     * @param i část slovníku, který má být načtena (od 0 do 20 včetně)
     */
    protected abstract void loadPart(int i);
    
}