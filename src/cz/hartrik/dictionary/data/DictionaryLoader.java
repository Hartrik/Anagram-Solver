package cz.hartrik.dictionary.data;

import cz.hartrik.common.io.Resources;
import cz.hartrik.dictionary.FakeDictionary;
import cz.hartrik.dictionary.HashDictionaryLazy;
import cz.hartrik.dictionary.IDictionary;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * @version 2015-02-04
 * @author Patrik Harag
 */
public class DictionaryLoader {
    
    static final String CZ_NAME = "Český slovník";
    static final String EN_NAME = "Anglický slovník";
    static final String CZ_PACKAGE = "/cz/hartrik/dictionary/data/cz/";
    static final String EN_PACKAGE = "/cz/hartrik/dictionary/data/en/";
    static final String CZ_FORMAT = "czech_%02d.txt";
    static final String EN_FORMAT = "english_%02d.txt";
    
    // slovníky
    
    public static IDictionary createCzDictionary() {
        return createDictionary(CZ_NAME, CZ_PACKAGE, CZ_FORMAT);
    }
    
    public static IDictionary createEnDictionary() {
        return createDictionary(EN_NAME, EN_PACKAGE, EN_FORMAT);
    }
    
    private static IDictionary createDictionary(
            String name, String pkg, String format) {
        
        return new HashDictionaryLazy(name) {

            @Override
            protected void loadPart(int i) {
                final Set<String> dictionary = dictionaries[i];
                    
                Resources.lines(pkg + String.format(format, i))
                        .filter(Objects::nonNull)
                        .forEach(dictionary::add);
            }
        };
    }
    
    // "falešné" slovníky
    
    public static IDictionary createFakeCzDictionary() {
        return createFakeDictionary(CZ_NAME, CZ_PACKAGE, "czech_structure.txt");
    }
    
    public static IDictionary createFakeEnDictionary() {
        return createFakeDictionary(EN_NAME, EN_PACKAGE, "english_structure.txt");
    }
    
    private static IDictionary createFakeDictionary(
            String dictionaryName, String pkg, String fileName) {
        
        // vezme ze souboru první řádku, která něco obsahuje
        String line = Resources.lines(pkg + fileName)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(l -> !l.isEmpty())
                .findFirst()
                    .orElseThrow(RuntimeException::new);

        // z řádky vyparsuje pole čísel
        int[] sizes = Arrays.stream(line.split(" "))
                .map(String::trim)
                .filter(l -> !l.isEmpty())
                .mapToInt(Integer::valueOf)
                .toArray();

        return new FakeDictionary(dictionaryName, sizes);
    }
    
}