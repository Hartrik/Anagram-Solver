package cz.hartrik.dictionary;

/**
 * Rozhraní pro slovník načítaný po částech. Díky tomuto přístupu je start
 * aplikace rychlý a aplikace spotřebuje méně paměti.
 *
 * @version 2015-07-19
 * @author Patrik Harag
 */
public interface ILazyDictionary extends IDictionary {

    /**
     * Načte celý slovník.
     */
    void loadAllParts();

    /**
     * Načte určitou část slovníku.
     *
     * @param i část slovníku, který má být načtena.
     */
    void loadPart(int i);

}