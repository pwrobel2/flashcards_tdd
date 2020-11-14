package flashcards_tdd.model;

import javax.persistence.*;

@Entity
@Table(name = "FLASHCARDS")
public class Flashcard {
    private static final int maximumLearningLevel = 5;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "term", nullable = false)
    private String term;

    @Column(name = "definition", nullable = false)
    private String definition;

    @Column(name = "learningLevel", nullable = false)
    private int learningLevel;

    public Flashcard() {
        this("", "", 0);
    }

    public Flashcard(String term, String definition) {
        this(term, definition, 0);
    }

    public Flashcard(String term, String definition, int learningLevel) {
        this.term = term;
        this.definition = definition;
        this.learningLevel = learningLevel;
    }

    static public int getMaximumLearningLevel() {
        return maximumLearningLevel;
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

    public void setLearningLevel(int learningLevel) {
        this.learningLevel = Math.max(0, Math.min(maximumLearningLevel, learningLevel));
    }

    public void increaseLearningLevel() {
        if (learningLevel < maximumLearningLevel) {
            learningLevel++;
        }
    }

    public void resetLearningLevel() {
        learningLevel = 0;
    }

    public boolean isTermCorrect(String term) {
        return this.term.equals(term);
    }
}
