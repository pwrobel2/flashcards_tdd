package flashcards_tdd.gui;

import flashcards_tdd.FlashcardManager;
import flashcards_tdd.FlashcardManagerImpl;
import flashcards_tdd.gui.dialog.FlashcardAddDialog;
import flashcards_tdd.gui.dialog.FlashcardUpdateDialog;
import flashcards_tdd.model.Flashcard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainWindowController implements Initializable {

    @FXML
    TableView<Flashcard> flashcardTableView;
    @FXML
    TableColumn<Flashcard, String> termColumn;
    @FXML
    TableColumn<Flashcard, String> definitionColumn;
    @FXML
    TableColumn<Flashcard, Integer> learningLevelColumn;
    private FlashcardManager fm;

    @FXML
    private void onAddButtonClick(ActionEvent event){
        FlashcardAddDialog dialog = new FlashcardAddDialog();
        Optional<?> result = dialog.showAndWait();
        if(result != null && result.isPresent()){
            fm.createFlashcard((Flashcard)result.get());
        }
        loadDataToTableView();
    }

    @FXML
    private void onEditButtonClick(ActionEvent event){
        Flashcard toUpdate = flashcardTableView.getSelectionModel().getSelectedItem();
        if(toUpdate != null) {
            FlashcardUpdateDialog dialog = new FlashcardUpdateDialog(toUpdate);
            Optional<?> result = dialog.showAndWait();
            if(result != null && result.isPresent()){
                fm.updateFlashcard((Flashcard)result.get());
            }
        }
        loadDataToTableView();
    }

    @FXML
    private void onRemoveButtonClick(ActionEvent event){
        Flashcard toDelete = flashcardTableView.getSelectionModel().getSelectedItem();
        if(toDelete != null){
            fm.deleteFlashcard(toDelete);
        }
        loadDataToTableView();
    }

    @FXML
    private void onLearningLevelResetButtonClick(ActionEvent event){
        Flashcard toReset = flashcardTableView.getSelectionModel().getSelectedItem();
        if(toReset != null){
            toReset.resetLearningLevel();
            fm.updateFlashcard(toReset);
        }
        loadDataToTableView();
    }
    
    @FXML
    private void onStartLearningButtonClick(ActionEvent event){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("LearningWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Learn");
            stage.setScene(new Scene(root, 747, 607));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            stage.setOnHiding( (e) -> {
                Parent primaryRoot;
                try {
                    primaryRoot = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Learn");
                    primaryStage.setScene(new Scene(primaryRoot, 774, 774));
                    primaryStage.show();
                }
                catch (IOException ex) {
                    System.err.println("Error while creating new window" + ex.toString());
                }
            });
        }
        catch (IOException e) {
            System.err.println("Error while creating new window" + e.toString());
        }
    }

    private void loadDataToTableView(){
        ObservableList<Flashcard> dataList = FXCollections.observableArrayList();
        dataList.addAll(fm.readAllFlashcardsList());
        termColumn.setCellValueFactory(new PropertyValueFactory<Flashcard, String>("term"));
        definitionColumn.setCellValueFactory(new PropertyValueFactory<Flashcard, String>("definition"));
        learningLevelColumn.setCellValueFactory(new PropertyValueFactory<Flashcard, Integer>("learningLevel"));
        flashcardTableView.setItems(dataList);
    }

    public void initialize(URL url, ResourceBundle rb) {
        fm = new FlashcardManagerImpl();
        loadDataToTableView();
    }
}
