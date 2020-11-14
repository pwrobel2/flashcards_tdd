package flashcards_tdd;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import flashcards_tdd.model.Flashcard;

public class LearningManagerImpl implements LearningManager {
    private FlashcardManager flashcardManager;
    private Flashcard currentFlashcard;

    @Override
    public void bindFlashcardManager(FlashcardManager flashcardManager) {
        this.flashcardManager = flashcardManager;
    }

    @Override
    public boolean nextFlashcard() {
        Random random = new Random();
        List<Flashcard> availableFlashcards = new ArrayList<Flashcard>();
        List<Flashcard> allFlashcards = flashcardManager.readAllFlashcardsList();
        for (Flashcard flashcard: allFlashcards) {
            if (flashcard.getLearningLevel() < Flashcard.getMaximumLearningLevel()) {
                availableFlashcards.add(flashcard);
            }
        }
        if (availableFlashcards.isEmpty()) {
            currentFlashcard = null;
            return false;
        }
        currentFlashcard = availableFlashcards.get(Math.abs(random.nextInt() % availableFlashcards.size()));
        return true;
    }

    @Override
    public String getTerm() {
        if (currentFlashcard == null) {
            return null;
        }
        return currentFlashcard.getTerm();
    }

    @Override
    public String getDefinition() {
        if (currentFlashcard == null) {
            return null;
        }
        return currentFlashcard.getDefinition();
    }

    @Override
    public Integer getLearningLevel() {
        if (currentFlashcard == null) {
            return null;
        }
        return currentFlashcard.getLearningLevel();
    }

    @Override
    public boolean testTerm(String term) {
        boolean result = currentFlashcard.isTermCorrect(term);
        if (result) {
            currentFlashcard.increaseLearningLevel();
        } else {
            currentFlashcard.resetLearningLevel();
        }
        flashcardManager.updateFlashcard(currentFlashcard);
        return result;
    }
}
