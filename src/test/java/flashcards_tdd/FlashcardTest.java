package flashcards_tdd;

import static org.junit.Assert.*;
import org.junit.Test;

import flashcards_tdd.model.*;

public class FlashcardTest {
    @Test
    public void isCorrectTest_correct() {
        Flashcard flashcard = new Flashcard("Test term", "Test Definition");
        boolean isCorrect = flashcard.isCorrect("Test term");
        assertTrue(isCorrect);
    }

    @Test
    public void isCorrectTest_failed() {
        Flashcard flashcard = new Flashcard("Test term", "Test Definition");
        boolean isCorrect = flashcard.isCorrect("Wrong term");
        assertFalse(isCorrect);
    }

    @Test
    public void increaseLerningLevelTest() {
        Flashcard flashcard = new Flashcard("Test term", "Test definition");
        int startingLevel = flashcard.getLearningLevel();
        flashcard.increaseLearningLevel();
        int finishLevel = flashcard.getLearningLevel();
        assertTrue(startingLevel < finishLevel);
    }

    @Test
    public void increaseLerningLevelTest_upperLimit() {
        Flashcard flashcard = new Flashcard("Test term", "Test definition");
        for (int i = 0; i < 6; i++)
            flashcard.increaseLearningLevel();
        int level = flashcard.getLearningLevel();
        assertEquals(5, level);
    }

    @Test
    public void resetLerningLevelTest() {
        Flashcard flashcard = new Flashcard("Test term", "Test definition");
        flashcard.increaseLearningLevel();
        int startLevel = flashcard.getLearningLevel();
        flashcard.resetLearningLevel();
        int finishLevel = flashcard.getLearningLevel();
        assertTrue(startLevel > finishLevel && finishLevel == 0);
    }
}
