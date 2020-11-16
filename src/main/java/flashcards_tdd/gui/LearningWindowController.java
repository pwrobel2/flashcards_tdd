package flashcards_tdd.gui;

import flashcards_tdd.FlashcardManagerImpl;
import flashcards_tdd.LearningManager;
import flashcards_tdd.LearningManagerImpl;
import flashcards_tdd.model.Flashcard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class LearningWindowController implements Initializable {

    @FXML
    TextField answerField;
    @FXML
    Text termDefinition;
    @FXML
    Button nextButton;
    @FXML
    Text correctLabel;
    @FXML
    Text answerLabel;
    private ObservableList<Flashcard> dataList = FXCollections.observableArrayList();
    private LearningManager lm;
    private double answersCount = 0;
    private double correctAnswersCount = 0;


    private void nextFlashcard(){
        answerField.setText("");
        updateCorrectLabel();
        if(lm.nextFlashcard()){
            termDefinition.setText(lm.getDefinition());
        } else {
            learningFinished();
        }
    }

    private void onIncorrectTerm(String answer){
        Alert wrongAlert = new Alert(Alert.AlertType.ERROR, ("Wrong answer: " + answer + "\nCorrect answer: " + lm.getTerm()), ButtonType.OK);
        wrongAlert.setHeaderText("");
        wrongAlert.setTitle("Wrong answer");
        wrongAlert.showAndWait();
    }

    @FXML
    private void onAnswerButtonClick(){
        answersCount++;
        String answer = answerField.getText();
        if (lm.testTerm(answer)){
            correctAnswersCount++;
        } else {
            onIncorrectTerm(answer);
        }
        nextFlashcard();
    }

    private void updateCorrectLabel(){
        correctLabel.setText("Correct: " + String.format("%.2f", (answersCount != 0 ? correctAnswersCount / answersCount * 100 : 0)) + "%");
    }

    private void learningFinished(){
        termDefinition.setText("Learning finished");
        answerField.setVisible(false);
        answerLabel.setVisible(false);
        correctLabel.setLayoutX(292);
        correctLabel.setTextAlignment(TextAlignment.CENTER);
        nextButton.setText("Exit");
        nextButton.setOnAction((event) -> {
            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.close();
        });
    }

    public void initialize(URL url, ResourceBundle rb) {
        lm = new LearningManagerImpl();
        lm.bindFlashcardManager(new FlashcardManagerImpl());
        nextFlashcard();
        return;
    }
}