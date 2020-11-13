package flashcards_tdd.model;

public class Flashcard {
    private int id;
    private String term;
    private String definition;
    private int learningLevel = 0;
    private static final int maximumLearningLevel = 5;

    public Flashcard(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public int getId() {
        return id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getLearningLevel() {
        return learningLevel;
    }

    static public int getMaximumLearningLevel() {
        return maximumLearningLevel;
    }

    public void increaseLearningLevel() {
        if (learningLevel < maximumLearningLevel) {
            learningLevel++;
        }
    }

    public void resetLearningLevel() {
        learningLevel = 0;
    }

    public boolean isCorrect(String term) {
        return this.term.equals(term);
    }
}
