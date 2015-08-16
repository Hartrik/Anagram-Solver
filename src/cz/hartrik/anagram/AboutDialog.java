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
 * Dialog o programu.
 *
 * @version 2015-08-16
 * @author Patrik Harag
 */
public class AboutDialog extends Stage {

    static final String FILE_FXML = "About.fxml";
    static final String FILE_ICON = "icon - info.png";

    public AboutDialog(ResourceBundle rb) {
        URL resource = getClass().getResource(FILE_FXML);
        Parent root = Exceptions.uncheckedGet(() -> FXMLLoader.load(resource, rb));

        setScene(new Scene(root));
        setTitle(String.format(rb.getString("about/frame-title"), Main.APP_VERSION));
        getIcons().add(new Image(getClass().getResourceAsStream(FILE_ICON)));

        setMinWidth(400);
        setMinHeight(300);
    }

}