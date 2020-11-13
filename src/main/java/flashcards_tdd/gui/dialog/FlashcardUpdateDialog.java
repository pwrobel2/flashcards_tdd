package flashcards_tdd.gui.dialog;

import flashcards_tdd.model.Flashcard;
import javafx.scene.control.TextField;

public class FlashcardUpdateDialog extends BaseDialog<Flashcard> {

    private final Flashcard flashcardToEdit;

    public FlashcardUpdateDialog(Flashcard flashcard) {
        super("Update Employee");
        flashcardToEdit = flashcard;
        TextField term = new DialogNotNullTextField();
        addTextField(term, "Term");

        TextField definition = new DialogNotNullTextField();
        addTextField(definition, "Definition");

        //TODO: Add learning level choiceBox

        term.setText(flashcard.getTerm());
        definition.setText(flashcard.getDefinition());

        setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                flashcardToEdit.setTerm(term.getText());
                flashcardToEdit.setDefinition(definition.getText());
                return flashcardToEdit;
            }
            return null;
        });
    }
}
