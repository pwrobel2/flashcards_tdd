package test.java.flashcards_tdd; //Trzeba zmienic

import org.junit.Test;
import to.model.Flashcard;

import static org.junit.Assert.*;


public class flashcardsTest {

    @Test
    public void createFlashcard(){
        String term = "Test term";
        String definition = "Test definition";
        Flashcard flashcard = new Flashcard(term, definition);
        boolean created = DataManager.addToDatabase(flashcard);
        assertTrue(created);
    }

    @Test
    public void getFlashcard(){
        String term = "Test term";
        String definition = "Test definition";
        Flashcard flashcard = new Flashcard(term, definition);
        int id = DataManager.addToDatabaseReturnId(flashcard);
        Flashcard tmp = DataManager.getFlashcardFromDatabase(id);
        assertTrue(tmp instanceof Flashcard);
    }

    @Test
    public void deleteFlashcard(){
        String term = "Test term";
        String definition = "Test definition";
        Flashcard flashcard = new Flashcard(term, definition);
        int id = DataManager.addToDatabaseReturnId(flashcard);
        Flashcard tmp = DataManager.getFlashcardFromDatabase(id);
        boolean hasDeletionPassed = DataManager.deleteFromDatabase(tmp);
        boolean hasFlashcardBeenDeleted = DataManager.getFlashcardFromDatabase(id) == null;
        assertTrue(hasDeletionPassed && hasFlashcardBeenDeleted);
    }

    @Test
    public void updateFlashcard_changeTerm(){
        String term = "Test term";
        String definition = "Test definition";
        Flashcard flashcard = new Flashcard(term, definition);
        int id = DataManager.addToDatabaseReturnId(flashcard);
        String newTerm = "Updated test term";
        flashcard = DataManager.getFlashcardFromDatabase(id);
        flashcard.setTerm(newTerm);
        boolean hasUpdatePassed = DataManager.updateInDatabase(flashcard);
        Flashcard tmp = DataManager.getFlashcardFromDatabase(id);
        boolean hasTermChanged = tmp.getTerm().equals(newTerm);
        assertTrue(hasUpdatePassed && hasTermChanged);
    }

    @Test
    public void updateFlashcard_changeDefinition(){
        String term = "Test term";
        String definition = "Test definition";
        Flashcard flashcard = new Flashcard(term, definition);
        int id = DataManager.addToDatabaseReturnId(flashcard);
        String newDef = "Updated test definition";
        flashcard = DataManager.getFlashcardFromDatabase(id);
        flashcard.setDefinition(newDef);
        boolean hasUpdatePassed = DataManager.updateInDatabase(flashcard);
        Flashcard tmp = DataManager.getFlashcardFromDatabase(id);
        boolean hasDefinitionChanged = tmp.getDefinition().equals(newDef);
        assertTrue(hasUpdatePassed && hasDefinitionChanged);
    }


    @Test
    public void isCorrectTest_correct(){
        Flashcard flashcard = new Flashcard("Test term","Test Definition");
        boolean isCorrect = flashcard.isCorrect("Test term");
        assertTrue(isCorrect);
    }

    @Test
    public void isCorrectTest_failed(){
        Flashcard flashcard = new Flashcard("Test term","Test Definition");
        boolean isCorrect = flashcard.isCorrect("Wrong term");
        assertFalse(isCorrect);
    }


    @Test
    public void increaseLerningLevelTest(){
        Flashcard flashcard = new Flashcard("Test term","Test definition");
        int startingLevel = flashcard.getLearningLevel();
        flashcard.increaseLearningLevel();
        int finishLevel = flashcard.getLearningLevel();
        assertTrue(startingLevel<finishLevel);
    }

    @Test
    public void increaseLerningLevelTest_upperLimit(){
        Flashcard flashcard = new Flashcard("Test term","Test definition");
        for (int i = 0; i < 6; i++)
            flashcard.increaseLearningLevel();
        int level = flashcard.getLearningLevel();
        assertEquals(5, level);
    }

    @Test
    public void resetLerningLevelTest(){
        Flashcard flashcard = new Flashcard("Test term", "Test definition");
        flashcard.increaseLearningLevel();
        int startLevel = flashcard.getLearningLevel();
        flashcard.resetLearningLevel();
        int finishLevel = flashcard.getLearningLevel();
        assertTrue(startLevel>finishLevel && finishLevel == 0);
    }

}
