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
        assertNotEquals(-1, flashcardId);
    }

    @Test
    public void createFlashcardInvalid() {
        int flashcardId = flashcardManager.createFlashcard(null);
        assertEquals(-1, flashcardId);
    }

    @Test
    public void readFlashcardById() {
        final String term = "Test term";
        final String definition = "Test definition";
        Flashcard flashcard = new Flashcard(term, definition);
        int flashcardId = flashcardManager.createFlashcard(flashcard);
        Flashcard readFlashcard = flashcardManager.readFlashcardById(flashcardId);
        assertNotNull(readFlashcard);
        assertEquals(flashcardId, readFlashcard.getId());
        assertEquals(term, readFlashcard.getTerm());
        assertEquals(definition, readFlashcard.getDefinition());
        assertEquals(0, readFlashcard.getLearningLevel());
    }

    @Test
    public void readFlashcardFromAllFlashcardsList() {
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
                assertEquals(flashcardId, readFlashcard.getId());
                assertEquals(term, readFlashcard.getTerm());
                assertEquals(definition, readFlashcard.getDefinition());
                assertEquals(0, readFlashcard.getLearningLevel());
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void readFlashcardByIdInvalid() {
        final int flashcardId = -1;
        Flashcard readFlashcard = flashcardManager.readFlashcardById(flashcardId);
        assertNull(readFlashcard);
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
        assertEquals(1, updatedFlashcardCount);
        Flashcard updatedFlashcard = flashcardManager.readFlashcardById(flashcardId);
        assertEquals(updatedTerm, updatedFlashcard.getTerm());
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
        assertEquals(1, updatedFlashcardCount);
        Flashcard updatedFlashcard = flashcardManager.readFlashcardById(flashcardId);
        assertEquals(updatedDefinition, updatedFlashcard.getDefinition());
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
            assertEquals(1, updatedFlashcardCount);
            Flashcard updatedFlashcard = flashcardManager.readFlashcardById(flashcardId);
            assertEquals(learningLevel, updatedFlashcard.getLearningLevel());
        }
    }

    @Test
    public void updateFlashcardInvalid() {
        final String term = "Test term";
        final String definition = "Test definition";
        Flashcard toBeUpdatedFlashcard = new Flashcard(term, definition);
        int updatedFlashcardCount = flashcardManager.updateFlashcard(toBeUpdatedFlashcard);
        assertEquals(0, updatedFlashcardCount);
        updatedFlashcardCount = flashcardManager.updateFlashcard(null);
        assertEquals(0, updatedFlashcardCount);
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
        assertEquals(1, deletedFlashcardCount);
        Flashcard deletedFlashcard = flashcardManager.readFlashcardById(flashcardId);
        assertNull(deletedFlashcard);
    }

    @Test
    public void deleteFlashcardInvalid() {
        final String term = "Test term";
        final String definition = "Test definition";
        Flashcard toBeDeletedFlashcard = new Flashcard(term, definition);
        int deletedFlashcardCount = flashcardManager.deleteFlashcard(toBeDeletedFlashcard);
        assertEquals(0, deletedFlashcardCount);
        deletedFlashcardCount = flashcardManager.deleteFlashcard(null);
        assertEquals(0, deletedFlashcardCount);
    }
}
