package flashcards_tdd;

public interface LearningManager {
    // Binds given flashcard manager to learning manager. Must not be null.
    void bindFlashcardManager(FlashcardManager flashcardManager);
    
    // Switches to the next flashcard. Must be called after bindFlashcardManager method.
    // Returns true if next flashcard was available and false otherwise.
    boolean nextFlashcard();

    // Returns current flashcard's term or null if no flashcard was available.
    String getTerm();
    
    // Returns current flashcard's definition or null if no flashcard was available.
    String getDefinition();

    // Returns current flashcard's learning level or null if no flashcard was available.
    Integer getLearningLevel();
    
    // Tests if term is correct. If yes, increases learning level, if no, resets learning level.
    // Flashcard must be available. Otherwise, throws NullPointerException.
    // Returns true if term is correct or false otherwise.
    boolean testTerm(String term);
}
