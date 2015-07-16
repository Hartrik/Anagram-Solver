package cz.hartrik.anagram;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 * 
 * @version 2015-02-03
 * @author Patrik Harag
 */
public class AboutController implements Initializable {

    static final String FILE_CONTENT = "about.html";
    
    @FXML private WebView webView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String htmlURL = getClass().getResource(FILE_CONTENT).toExternalForm();
        webView.getEngine().load(htmlURL);
    }
    
}