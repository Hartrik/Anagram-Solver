package cz.hartrik.anagram;

import cz.hartrik.common.Exceptions;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Okno se správcem slovníků.
 *
 * @version 2015-08-16
 * @author Patrik Harag
 */
public class DictionaryManager extends Stage {

    static final String FILE_FXML = "DictionaryManager.fxml";
    static final String FILE_ICON = "icon - dictionary.png";

    private final DictionaryManagerController controller;

    public DictionaryManager(ResourceBundle rb) {
        final URL url = getClass().getResource(FILE_FXML);
        final FXMLLoader fxmlLoader = new FXMLLoader(url, rb);

        Exceptions.unchecked(
                () -> setScene(new Scene((Parent) fxmlLoader.load())));

        controller = fxmlLoader.getController();

        setTitle(String.format(rb.getString("manager/frame-title"), Main.APP_VERSION));
        getIcons().add(new Image(getClass().getResourceAsStream(FILE_ICON)));
        setMinHeight(400);
        setMinWidth(750);
    }

    public DictionaryManagerController getController() {
        return controller;
    }

}