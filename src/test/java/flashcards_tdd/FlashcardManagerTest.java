package flashcards_tdd;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;

import flashcards_tdd.model.*;

public class FlashcardManagerTest {
    private static FlashcardManager flashcardManager;

    @BeforeClass
    public static void BeforeClass() {
        flashcardManager = new FlashcardManagerImpl();
    }

    @AfterClass
    public static void AfterClass() {
        HibernateUtil.shutdown();
    }

    @Test
    public void createFlashcard() {
        final String term = "Test term";
        final String definition = "Test definition";
        Flashcard flashcard = new Flashcard(term, definition);
        int flashcardId = flashcardManager.createFlashcard(flashcard);
        assertNotEquals(flashcardId, -1);
    }

    @Test
    public void createAndReadFlashcardById() {
        final String term = "Test term";
        final String definition = "Test definition";
        Flashcard flashcard = new Flashcard(term, definition);
        int flashcardId = flashcardManager.createFlashcard(flashcard);
        Flashcard readFlashcard = flashcardManager.readFlashcardById(flashcardId);
        assertNotNull(readFlashcard);
        assertEquals(readFlashcard.getId(), flashcardId);
        assertEquals(readFlashcard.getTerm(), term);
        assertEquals(readFlashcard.getDefinition(), definition);
        assertEquals(readFlashcard.getLearningLevel(), 0);
    }

    @Test
    public void createAndReadFlashcardFromAllFlashcardsList() {
        final String term = "Test term";
        final String definition = "Test definition";
        Flashcard flashcard = new Flashcard(term, definition);
        int flashcardId = flashcardManager.createFlashcard(flashcard);
        List<Flashcard> readAllFlashcardsList = flashcardManager.readAllFlashcardsList();
        boolean found = false;
        for (Flashcard readFlashcard : readAllFlashcardsList) {
            assertNotNull(readFlashcard);
            if (readFlashcard.getId() == flashcardId) {
                assertFalse(found);
                assertEquals(readFlashcard.getId(), flashcardId);
                assertEquals(readFlashcard.getTerm(), term);
                assertEquals(readFlashcard.getDefinition(), definition);
                assertEquals(readFlashcard.getLearningLevel(), 0);
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void updateFlashcardTerm() {
        final String term = "Test term";
        final String definition = "Test definition";
        final String updatedTerm = "Updated test term";
        Flashcard flashcard = new Flashcard(term, definition);
        int flashcardId = flashcardManager.createFlashcard(flashcard);
        Flashcard toBeUpdatedFlashcard = flashcardManager.readFlashcardById(flashcardId);
        toBeUpdatedFlashcard.setTerm(updatedTerm);
        int updatedFlashcardCount = flashcardManager.updateFlashcard(toBeUpdatedFlashcard);
        assertEquals(updatedFlashcardCount, 1);
        Flashcard updatedFlashcard = flashcardManager.readFlashcardById(flashcardId);
        assertEquals(updatedFlashcard.getTerm(), updatedTerm);
    }

    @Test
    public void updateFlashcardDefinition() {
        final String term = "Test term";
        final String definition = "Test definition";
        final String updatedDefinition = "Updated test definition";
        Flashcard flashcard = new Flashcard(term, definition);
        int flashcardId = flashcardManager.createFlashcard(flashcard);
        Flashcard toBeUpdatedFlashcard = flashcardManager.readFlashcardById(flashcardId);
        toBeUpdatedFlashcard.setDefinition(updatedDefinition);
        int updatedFlashcardCount = flashcardManager.updateFlashcard(toBeUpdatedFlashcard);
        assertEquals(updatedFlashcardCount, 1);
        Flashcard updatedFlashcard = flashcardManager.readFlashcardById(flashcardId);
        assertEquals(updatedFlashcard.getDefinition(), updatedDefinition);
    }

    @Test
    public void updateFlashcardLearningLevel() {
        final String term = "Test term";
        final String definition = "Test definition";
        final String[] testedMethods = { "setLearningLevel", "increaseLearningLevel", "resetLearningLevel" };
        Flashcard flashcard = new Flashcard(term, definition);
        int flashcardId = flashcardManager.createFlashcard(flashcard);
        int learningLevel = 0;
        for (String testedMethod : testedMethods) {
            Flashcard toBeUpdatedFlashcard = flashcardManager.readFlashcardById(flashcardId);
            if (testedMethod.equals("setLearningLevel")) {
                learningLevel = Flashcard.getMaximumLearningLevel() / 2;
                toBeUpdatedFlashcard.setLearningLevel(learningLevel);
            }
            else if (testedMethod.equals("increaseLearningLevel")) {
                while (learningLevel < Flashcard.getMaximumLearningLevel()) {
                    learningLevel++;
                    toBeUpdatedFlashcard.increaseLearningLevel();
                }
            } else if (testedMethod.equals("resetLearningLevel")) {
                learningLevel = 0;
                toBeUpdatedFlashcard.resetLearningLevel();
            }
            int updatedFlashcardCount = flashcardManager.updateFlashcard(toBeUpdatedFlashcard);
            assertEquals(updatedFlashcardCount, 1);
            Flashcard updatedFlashcard = flashcardManager.readFlashcardById(flashcardId);
            assertEquals(updatedFlashcard.getLearningLevel(), learningLevel);
        }
    }

    @Test
    public void deleteFlashcard() {
        final String term = "Test term";
        final String definition = "Test definition";
        Flashcard flashcard = new Flashcard(term, definition);
        int flashcardId = flashcardManager.createFlashcard(flashcard);
        Flashcard toBeDeletedFlashcard = flashcardManager.readFlashcardById(flashcardId);
        assertNotNull(toBeDeletedFlashcard);
        int deletedFlashcardCount = flashcardManager.deleteFlashcard(toBeDeletedFlashcard);
        assertEquals(deletedFlashcardCount, 1);
        Flashcard deletedFlashcard = flashcardManager.readFlashcardById(flashcardId);
        assertNull(deletedFlashcard);
    }
}
