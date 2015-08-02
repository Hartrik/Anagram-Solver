package cz.hartrik.anagram;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Vstupní třída.
 *
 * @version 2015-02-04
 * @author Patrik Harag
 */
public class Main extends Application {

    public static final String APP_NAME  = "Lamač přesmyček 3.1";
           static final String FILE_FXML = "MainWindow.fxml";
           static final String FILE_ICON = "icon - refresh.png" ;

    @Override
    public void start(Stage stage) throws Exception {
        setUserAgentStylesheet(STYLESHEET_MODENA);

        final Parent root = FXMLLoader.load(getClass().getResource(FILE_FXML));

        Image icon = new Image(getClass().getResourceAsStream(FILE_ICON));
        stage.getIcons().add(icon);
        stage.setTitle(APP_NAME);

        stage.setMinWidth(600);
        stage.setMinHeight(450);

        stage.setScene(new Scene(root));
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
