package flashcards_tdd.gui.dialog;

import flashcards_tdd.model.Flashcard;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class FlashcardUpdateDialog extends BaseDialog<Flashcard> {

    private final Flashcard flashcardToEdit;

    public FlashcardUpdateDialog(Flashcard flashcard) {
        super("Update Flashcard");
        flashcardToEdit = flashcard;
        TextField term = new DialogNotNullTextField();
        addTextField(term, "Term");

        TextField definition = new DialogNotNullTextField();
        addTextField(definition, "Definition");

        term.setText(flashcard.getTerm());
        definition.setText(flashcard.getDefinition());

        Slider learningLevel = new Slider(0, 5, flashcardToEdit.getLearningLevel());
        learningLevel.setBlockIncrement(1);
        learningLevel.setMajorTickUnit(1);
        learningLevel.setShowTickLabels(true);
        learningLevel.setMinorTickCount(0);
        learningLevel.setSnapToTicks(true);
        addSlider(learningLevel, "Learning level");

        setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                flashcardToEdit.setTerm(term.getText());
                flashcardToEdit.setDefinition(definition.getText());
                flashcardToEdit.setLearningLevel((int)learningLevel.getValue());
                return flashcardToEdit;
            }
            return null;
        });
    }
}
