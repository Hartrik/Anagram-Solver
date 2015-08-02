package cz.hartrik.anagram;

import cz.hartrik.anagram.solve.AnagramSolver;
import cz.hartrik.anagram.solve.Combinatorics;
import cz.hartrik.anagram.solve.PermutationsGenerator;
import cz.hartrik.dictionary.IDictionary;
import cz.hartrik.dictionary.data.DictionaryLoader;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * @version 2015-08-02
 * @author Patrik Harag
 */
public class MainWindowController implements Initializable {

    private final IDictionary cz = DictionaryLoader.createCzDictionary();
    private final IDictionary en = DictionaryLoader.createEnDictionary();
    private DictionaryManager dictionaryManager;
    private AboutDialog aboutDialog;

    @FXML private ProgressIndicator progressIndicator;

    @FXML private TextField textField;
    @FXML private TextArea taPermutations;
    @FXML private TextArea taResult;

    @FXML private Label lChars;
    @FXML private Label lPerms;

    @FXML private CheckBox cbDictCzech;
    @FXML private CheckBox cbDictEnglish;
    @FXML private CheckBox cbDictUser;
    @FXML private CheckBox cbIgnoreDiacritics;
    @FXML private CheckBox cbSorted;
    @FXML private CheckBox cbPermutations;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle rb) {}

    @FXML
    protected synchronized void selectUserDictionary() {
        if (dictionaryManager == null) {
            dictionaryManager = new DictionaryManager();
            dictionaryManager.initOwner(textField.getScene().getWindow());
            dictionaryManager.getController().addDictionaries(
                    DictionaryLoader.createFakeCzDictionary(),
                    DictionaryLoader.createFakeEnDictionary());
        }
        showWindow(dictionaryManager);
    }

    @FXML
    protected synchronized void showInfo() {
        if (aboutDialog == null) {
            aboutDialog = new AboutDialog();
            aboutDialog.initOwner(textField.getScene().getWindow());
        }
        showWindow(aboutDialog);
    }

    private void showWindow(Stage stage) {
        if (!stage.isShowing())
            stage.showAndWait();
        else
            stage.requestFocus();
    }

    @FXML
    protected void onKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String normalized = update();
            textField.setText(normalized);
            textField.end();
            analyze(normalized);

        } else {
            if (event.getCode() == KeyCode.ESCAPE)
                textField.clear();

            update();
        }
    }

    protected String update() {
        final String input = textField.getText();
        final String normalized = input.trim().toLowerCase();

        lChars.setText(String.valueOf(normalized.length()));

        long perms = Combinatorics.permutationsExact(normalized);
        lPerms.setText((perms == -1L)
                ? "<velké číslo>"
                : (perms > 100_000_000L
                        ? String.format("> %,d", 100_000_000)
                        : String.format("%,d", perms)));

        taPermutations.clear();
        taResult.clear();

        return normalized;
    }

    protected void analyze(final String anagram) {
        progressIndicator.setVisible(true);
        textField.setEditable(false);

        new Thread(() -> {
            long startTime = System.currentTimeMillis();

            Thread thread1 = new Thread(() -> solve(anagram));
            Thread thread2 = new Thread(() -> permutations(anagram));

            thread1.start();
            thread2.start();

            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

            Platform.runLater(() -> {
                textField.setEditable(true);
                progressIndicator.setVisible(false);
            });

            System.out.println(System.currentTimeMillis() - startTime + " ms");
        }).start();
    }

    private void solve(final String anagram) {
        final AnagramSolver solver = new AnagramSolver(anagram);
        solver.setIgnoreDiacritics(cbIgnoreDiacritics.isSelected());

        final Set<String> results = new LinkedHashSet<>();

        if (cbDictCzech.isSelected())
            results.addAll(solver.search(cz));

        if (cbDictEnglish.isSelected())
            results.addAll(solver.search(en));

        if (cbDictUser.isSelected() && dictionaryManager != null)
            dictionaryManager.getController().userDictionaries().forEach(
                    (dictionary) -> results.addAll(solver.search(dictionary)));

        Stream<String> stream = results.stream();
        stream = cbSorted.isSelected() ? stream.sorted() : stream;
        final String toString = stream.collect(Collectors.joining("\n"));

        Platform.runLater(() -> taResult.setText(toString));
    }

    private void permutations(final String anagram) {
        if (!cbPermutations.isSelected()) {
            Platform.runLater(() -> taPermutations.clear());
            return;
        }

        long mPerms = Combinatorics.permutationsExact(anagram);
        if (mPerms != -1 && mPerms < 25_000 && anagram.length() <= 10) {
            final Collection<String> perms = PermutationsGenerator.generate(anagram);
            final Stream<String> stream = (cbSorted.isSelected())
                    ? perms.stream().sorted()
                    : perms.stream();

            final String collect = stream.collect(Collectors.joining("\n"));
            Platform.runLater(() -> taPermutations.setText(collect));
        } else {
            Platform.runLater(() -> taPermutations.setText("✗"));
        }
    }

}