package cz.hartrik.anagram;

import cz.hartrik.common.ui.javafx.DragAndDropInitializer;
import cz.hartrik.dictionary.HashDictionary;
import cz.hartrik.dictionary.IDictionary;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @version 2015-02-03
 * @author Patrik Harag
 */
public class DictionaryManagerController implements Initializable {

    private FileChooser fileChooser;
    
    @FXML private ListView<IDictionary> listView;
    @FXML private Button bAdd;
    @FXML private Button bRemove;
    
    @FXML private VBox contentArea;
    
    private List<Node> mainContent;
    @FXML private Label lTitle;
    @FXML private Label lDescription;
    @FXML private BarChart<String, Integer> chart;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Vybrat slovník");
        fileChooser.setInitialDirectory(new File("."));
        
        mainContent = new ArrayList<>(contentArea.getChildren());
        contentArea.getChildren().clear();
        
        listView.setCellFactory(new DictionaryCellFactory());
        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, previous, dict) -> {

            if (dict == null) {
                bRemove.setDisable(true);
                contentArea.getChildren().clear();
            } else {
                bRemove.setDisable(!(dict instanceof UserDictionary));
                
                lTitle.setText(dict.name());
                
                final String format = "Obsahuje celkem %,d slov.";
                lDescription.setText(String.format(format, dict.size()));
                
                chart.getData().setAll(
                        Collections.singleton(createChartData(dict)));
                
                contentArea.getChildren().setAll(mainContent);
            }
        });

        DragAndDropInitializer.initFileDragAndDrop(listView, this::addFiles);
    }

    protected XYChart.Series<String, Integer> createChartData(IDictionary dict) {
        return IntStream.rangeClosed(1, 21).mapToObj(i -> {
            String label = (i > 20) ? ">20" : String.valueOf(i);
            return new XYChart.Data<>(label, dict.getPart(i).size());
            
        }).collect(XYChart.Series<String, Integer>::new,
                (coll, data) -> coll.getData().add(data), (a, b) -> {});
    }
    
    protected void addDictionaries(IDictionary... dictionaries) {
        listView.getItems().addAll(dictionaries);
        if (dictionaries.length > 0)
            listView.getSelectionModel().select(dictionaries[0]);
    }

    @FXML 
    private void addDictionary() {
        List<File> selected = fileChooser.showOpenMultipleDialog(getWindow());
        
        if (selected != null)
            addFiles(selected.stream()
                    .map(File::toPath).collect(Collectors.toList()));
    }
    
    @FXML 
    private void removeDictionary() {
        IDictionary selected = listView.getSelectionModel().getSelectedItem();
        listView.getItems().remove(selected);
    }
    
    private void addFiles(List<Path> files) {
        files.stream().forEach((file) -> {
            try {
                addFile(file);
            } catch (Exception ex) { }
        });
    }
    
    private void addFile(Path file) throws IOException {
        if (!listView.getItems().stream()
                .filter(dict -> dict instanceof UserDictionary)
                .map(dict -> (UserDictionary) dict)
                .anyMatch(dict -> sameFile(dict.file, file))) {
            
            UserDictionary userDictionary = new UserDictionary(file);
            Files.lines(file)
                    .filter(line -> !Objects.isNull(line))
                    .map(String::trim)
                    .filter(word -> !word.isEmpty())
                    .map(String::toLowerCase)
                    .forEach(userDictionary::add);

            listView.getItems().add(userDictionary);
        }
    }
    
    private boolean sameFile(Path file1, Path file2) {
        try {
            return Files.isSameFile(file1, file2);
        } catch (IOException ex) {
            return false;
        }
    }
    
    public static class UserDictionary extends HashDictionary {
        private final Path file;

        public UserDictionary(Path file) {
            super(file.getFileName().toString());
            this.file = file;
        }
    }
    
    private Window getWindow() {
        return listView.getScene().getWindow();
    }
    
    public Stream<IDictionary> userDictionaries() {
        return listView.getItems().stream()
                .filter(dict -> dict instanceof UserDictionary);
    }
    
}