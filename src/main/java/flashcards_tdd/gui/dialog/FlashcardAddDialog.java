package flashcards_tdd.gui.dialog;

import flashcards_tdd.model.Flashcard;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class FlashcardAddDialog extends BaseDialog<Flashcard> {

    public FlashcardAddDialog(){
        super("Add Flashcard");
        TextField term = new DialogNotNullTextField();
        addTextField(term, "Term");

        TextField definition = new DialogNotNullTextField();
        addTextField(definition, "Definition");

        Slider learningLevel = new Slider(0, 5, 0);
        learningLevel.setBlockIncrement(1);
        learningLevel.setMajorTickUnit(1);
        learningLevel.setShowTickLabels(true);
        learningLevel.setMinorTickCount(0);
        learningLevel.setSnapToTicks(true);
        addSlider(learningLevel, "Learning level");


        setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Flashcard(term.getText(), definition.getText(), (int)learningLevel.getValue());
            }
            return null;
        });
    }
}
