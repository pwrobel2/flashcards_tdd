package flashcards_tdd;

import java.util.List;

import flashcards_tdd.model.Flashcard;

public interface FlashcardManager {
    // Creates a new flashcard. When flashcard's id is null, a valid id is generated.
    // Returns 1 when flashcard was successfully created or 0 when flashcard already exists. Throws IOException.
    public int createFlashcard(Flashcard flashcard);

    // Updates the flashcard with matching id.
    // Returns number of updated flashcards. Throws IOException.
    public int updateFlashcard(Flashcard flashcard);

    // Deletes the flashcard with matching id.
    // Returns number of deleted flashcards. Throws IOException.
    public int deleteFlashcard(Flashcard flashcard);

    // Returns flashcard with matching id or null when the flashcard was not found. Throws IOException.
    public Flashcard readFlashcardById(int id);

    // Returns the list of all flashcards. Never returns null nor null element. Throws IOException.
    public List<Flashcard> readAllFlashcardsList();
}
