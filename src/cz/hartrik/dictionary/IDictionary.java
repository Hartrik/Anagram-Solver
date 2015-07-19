package cz.hartrik.dictionary;

import java.util.Collection;

/**
 * Rozhraní pro slovník. Slovník je pro rychlejší práci rozdělen na několik
 * částí, podle délky slov.
 *
 * @version 2015-02-02
 * @author Patrik Harag
 */
public interface IDictionary {

    /**
     * Vrátí jméno slovníku.
     *
     * @return jméno slovníku
     */
    public String name();

    /**
     * Přidá nové slovo do slovníku.
     *
     * @param word nové slovo
     */
    public void add(String word);

    /**
     * Přidá slova do slovníku.
     *
     * @param words nová slova
     */
    public void addAll(Collection<String> words);
    
    /**
     * Vrátí <code>true</code>, pokud slovník obsahuje určité slovo.
     *
     * @param word hledané slovo
     * @return existence
     */
    public boolean contains(String word);

    /**
     * Vrátí celkový počet slov ve slovníku.
     *
     * @return počet slov
     */
    public long size();

    /**
     * Vrátí kolekci slov s určitou délkou.
     *
     * @param length délka slov
     * @return kolekce slov s určitou délkou
     */
    public Collection<String> getPart(int length);

    /**
     * Vrátí kolekci slov se stejnou délkou.
     *
     * @param word slovo, podle kterého bude vybrána část slovníku
     * @return kolekce slov s určitou délkou
     */
    public default Collection<String> getPart(String word) {
        return getPart(word.length());
    }

}