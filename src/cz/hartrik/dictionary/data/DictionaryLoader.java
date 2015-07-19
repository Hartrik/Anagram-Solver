package cz.hartrik.dictionary.data;

import cz.hartrik.common.io.Resources;
import cz.hartrik.dictionary.ClosedArrayList;
import cz.hartrik.dictionary.FakeDictionary;
import cz.hartrik.dictionary.IDictionary;
import cz.hartrik.dictionary.LazyDictionary;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * @version 2015-07-19
 * @author Patrik Harag
 */
public class DictionaryLoader {

    private DictionaryLoader() {}

    static final String CZ_NAME = "Český slovník";
    static final String EN_NAME = "Anglický slovník";
    static final String CZ_PACKAGE = "/cz/hartrik/dictionary/data/cz/";
    static final String EN_PACKAGE = "/cz/hartrik/dictionary/data/en/";
    static final String CZ_FORMAT = "czech_%02d.txt";
    static final String EN_FORMAT = "english_%02d.txt";
    static final String CZ_STRUCT = "czech_structure.txt";
    static final String EN_STRUCT = "english_structure.txt";

    // slovníky

    public static IDictionary createCzDictionary() {
        return createDictionary(CZ_NAME, CZ_PACKAGE, CZ_FORMAT, CZ_STRUCT);
    }

    public static IDictionary createEnDictionary() {
        return createDictionary(EN_NAME, EN_PACKAGE, EN_FORMAT, EN_STRUCT);
    }

    private static IDictionary createDictionary(
            String name, String pkg, String fileFormat, String structFileName) {

        IDictionary struct = createFakeDictionary(name, pkg, structFileName);

        return new LazyDictionary<ClosedArrayList<String>>(
                name,
                (i) -> new ClosedArrayList<>(struct.getPart(i).size())) {

            @Override
            public void loadPartImpl(int i) {
                Collection<String> dictionary = dictionaries.get(i);

                Resources.lines(pkg + String.format(fileFormat, i))
                        .filter(Objects::nonNull)
                        .forEach(dictionary::add);
            }
        };
    }

    // "falešné" slovníky

    public static IDictionary createFakeCzDictionary() {
        return createFakeDictionary(CZ_NAME, CZ_PACKAGE, CZ_STRUCT);
    }

    public static IDictionary createFakeEnDictionary() {
        return createFakeDictionary(EN_NAME, EN_PACKAGE, EN_STRUCT);
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