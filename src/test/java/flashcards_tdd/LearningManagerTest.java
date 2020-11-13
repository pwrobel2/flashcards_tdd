package flashcards_tdd;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class LearningManagerTest {
    private static FlashcardManager flashcardManager;
    private static LearningManager learningManager;

    @BeforeClass
    public static void BeforeClass() {
        flashcardManager = new FlashcardManagerImpl();
        learningManager = new LearningManagerImpl();
        learningManager.bindFlashcardManager(flashcardManager);
    }

    // TODO 
}
