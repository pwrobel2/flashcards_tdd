package flashcards_tdd;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import flashcards_tdd.model.Flashcard;

public class LearningManagerTest {
    private static FlashcardManager flashcardManager;
    private static LearningManager learningManager;

    @BeforeClass
    public static void BeforeClass() {
        flashcardManager = new FlashcardManagerImpl();
        learningManager = new LearningManagerImpl();
        learningManager.bindFlashcardManager(flashcardManager);
    }

    @AfterClass
    public static void AfterClass() {
        HibernateUtil.shutdown();
    }

    @Before
    public void Before() {
        final String[] terms = { "Test term #1", "Test term #2", "Test term #3" };
        final String[] defnintions = { "Test definition #1", "Test definition #2", "Test definition #3" };
        for (int i = 0; i < terms.length && i < defnintions.length; i++) {
            Flashcard flashcard = new Flashcard(terms[i], defnintions[i]);
            flashcardManager.createFlashcard(flashcard);
        }
    }

    @Test
    public void nextFlashcard() {
        boolean runAtLeastOnce = false;
        while (learningManager.nextFlashcard()) {
            assertNotNull(learningManager.getTerm());
            assertNotNull(learningManager.getDefinition());
            assertNotNull(learningManager.getLearningLevel());
            runAtLeastOnce = true;
        }
        assertTrue(runAtLeastOnce);
    }

    @Test
    public void testTerm() {
        final String incorrectTerm = "Incorrect test term";
        assertTrue(learningManager.nextFlashcard());
        assertTrue(learningManager.testTerm(learningManager.getTerm()));
        assertFalse(learningManager.testTerm(incorrectTerm));
    }

    @Test
    public void testTermAndGetLearningLevel() {
        final String incorrectTerm = "Incorrect test term";
        assertTrue(learningManager.nextFlashcard());
        int learningLevel = 0;
        learningManager.testTerm(incorrectTerm);
        assertEquals(learningLevel, learningManager.getLearningLevel().intValue());
        while (learningLevel < Flashcard.getMaximumLearningLevel()) {
            learningLevel++;
            learningManager.testTerm(learningManager.getTerm());
            assertEquals(learningLevel, learningManager.getLearningLevel().intValue());
        }
    }
}
