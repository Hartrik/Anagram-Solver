package cz.hartrik.anagram;

import cz.hartrik.common.io.Resources;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Vstupní třída.
 *
 * @version 2015-08-16
 * @author Patrik Harag
 */
public class Main extends Application {

    public static final String APP_VERSION = "3.2 /D";

    static final String FILE_FXML = "MainWindow.fxml";
    static final String FILE_ICON = "icon - refresh.png" ;
    static final String STRINGS = "cz.hartrik.anagram.strings";

    @Override
    public void start(Stage stage) throws Exception {
        setUserAgentStylesheet(STYLESHEET_MODENA);

        ResourceBundle rb = ResourceBundle.getBundle(STRINGS);
        URL fxml = getClass().getResource(FILE_FXML);

        stage.setScene(new Scene(FXMLLoader.load(fxml, rb)));
        stage.getIcons().add(Resources.image(FILE_ICON, getClass()));
        stage.setTitle(String.format(rb.getString("main/frame-title"), APP_VERSION));

        stage.setWidth(650);
        stage.setHeight(500);

        stage.setMinWidth(600);
        stage.setMinHeight(450);

        stage.show();
    }

    /**
     * Vstupní metoda.
     *
     * @param args argumenty z příkazové řádky
     */
    public static void main(String[] args) {
        launch(args);
    }

}
