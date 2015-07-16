package cz.hartrik.anagram;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Okno se správcem slovníků.
 * 
 * @version 2015-02-02
 * @author Patrik Harag
 */
public class DictionaryManager extends Stage {

    public static final String TITLE = Main.APP_NAME + " - správce slovníků";
           static final String FILE_FXML = "DictionaryManager.fxml";
           static final String FILE_ICON = "icon - dictionary.png";
    
    private final DictionaryManagerController controller;
    
    public DictionaryManager() {
        final URL url = getClass().getResource(FILE_FXML);
        final FXMLLoader fxmlLoader = new FXMLLoader(url);
        
        try {
            setScene(new Scene((Parent) fxmlLoader.load()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        controller = fxmlLoader.getController();
        
        setTitle(TITLE);
        getIcons().add(new Image(getClass().getResourceAsStream(FILE_ICON)));
        setMinHeight(400);
        setMinWidth(750);
    }

    public DictionaryManagerController getController() {
        return controller;
    }

}