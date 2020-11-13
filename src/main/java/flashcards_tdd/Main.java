package flashcards_tdd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui/MainWindow.fxml"));
        primaryStage.setTitle("Flashcards");
        primaryStage.setScene(new Scene(root, 774, 774));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
