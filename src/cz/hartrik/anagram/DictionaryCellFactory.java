
package cz.hartrik.anagram;

import cz.hartrik.anagram.DictionaryManagerController.UserDictionary;
import cz.hartrik.common.ui.javafx.SimpleCellFactory;
import cz.hartrik.dictionary.IDictionary;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Tvoří obsah buňek v <code>ListView</code>.
 * 
 * @version 2015-02-01
 * @author Patrik Harag
 */
public class DictionaryCellFactory extends SimpleCellFactory<IDictionary> {
    
    @Override
    protected Node createContent(IDictionary dictionary) {
        final Label name = new Label(dictionary.name());

        if (dictionary instanceof UserDictionary) {
            return name;

        } else {
            Label info = new Label("(vestavěný)");
            info.setTextFill(Color.BLUE);
            return new HBox(5, info, name);
        }
    }
    
}