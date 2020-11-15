package flashcards_tdd.gui.dialog;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDialog<T> extends Dialog<T> {

    protected final GridPane grid;
    protected int gridRows=0;
    private final Node okButton;
    protected ButtonType okButtonType;
    protected List<Requirement> requirements = new ArrayList<>();

    public BaseDialog(String title){
        super();
        setTitle(title);
        setHeaderText(null);
        okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        okButton = getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        getDialogPane().setContent(grid);
    }

    protected void setOkButtonDisable(boolean value){okButton.setDisable(value);}

    protected void addTextField(TextField textField, String name){
        textField.setPromptText(name);
        grid.add(new Label(name), 0, gridRows );
        grid.add(textField, 1, gridRows );
        gridRows++;
    }

    protected void addSlider(Slider slider, String name){
        grid.add(new Label(name), 0, gridRows);
        grid.add(slider, 1, gridRows);
        gridRows++;

    }

    private boolean areRequirementsNotMet(){
        for(Requirement r : requirements){
            if(!r.getIsMet()) {
                return true;
            }
        }
        return false;
    }

    protected class DialogNotNullTextField extends TextField{
        public DialogNotNullTextField(){
            super();
            Requirement req = new Requirement();
            requirements.add(req);
            this.textProperty().addListener((observable, oldValue, newValue) ->  {
                req.setIsMet(!newValue.trim().isEmpty());
                okButton.setDisable((areRequirementsNotMet()));
            });
        }
    }

    private static class Requirement{
        private boolean value;

        public Requirement(){
            this.value = false;
        }

        public Requirement(boolean value){
            this.value = value;
        }

        public void setIsMet(boolean value){
            this.value = value;
        }

        public boolean getIsMet(){
            return value;
        }
    }

}
