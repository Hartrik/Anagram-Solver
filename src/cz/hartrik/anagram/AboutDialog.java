package cz.hartrik.anagram;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Dialo o programu.
 * 
 * @version 2015-02-03
 * @author Patrik Harag
 */
public class AboutDialog extends Stage {

    public static final String TITLE = Main.APP_NAME + " - informace";
           static final String FILE_FXML = "About.fxml";
           static final String FILE_ICON = "icon - info.png";
    
    public AboutDialog() {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource(FILE_FXML));
            setScene(new Scene(root));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        setTitle(TITLE);
        getIcons().add(new Image(getClass().getResourceAsStream(FILE_ICON)));
        
        setMinWidth(400);
        setMinHeight(300);
    }
    
}