package flashcards_tdd;

import java.util.List;

import flashcards_tdd.model.Flashcard;

public interface FlashcardManager {
    // Creates a new flashcard.
    // Returns flashcard's id when flashcard was successfully created or -1 otherwise.
    public int createFlashcard(Flashcard flashcard);

    // Updates the flashcard with matching id.
    // Returns number of updated flashcards.
    public int updateFlashcard(Flashcard flashcard);

    // Deletes the flashcard with matching id.
    // Returns number of deleted flashcards.
    public int deleteFlashcard(Flashcard flashcard);

    // Returns flashcard with matching id or null when the flashcard was not found.
    public Flashcard readFlashcardById(int id);

    // Returns the list of all flashcards. Never returns null nor null element.
    public List<Flashcard> readAllFlashcardsList();
}
