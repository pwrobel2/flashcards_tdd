package flashcards_tdd.gui.dialog;

import flashcards_tdd.model.Flashcard;
import javafx.scene.control.TextField;

public class FlashcardAddDialog extends BaseDialog<Flashcard> {

    public FlashcardAddDialog(){
        super("Add Flashcard");
        TextField term = new DialogNotNullTextField();
        addTextField(term, "Term");

        TextField definition = new DialogNotNullTextField();
        addTextField(definition, "Definition");

        //TODO: Add learning level choice

        setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Flashcard(term.getText(), definition.getText());
            }
            return null;
        });
    }
}
