package to.model; //Trzeba zmienic

public class Flashcard {

    private int id;
    private String term;
    private String definition;
    private int learningLevel = 0;

    public Flashcard(String term, String definition){
        this.term = term;
        this.definition = definition;
    }

    public int getId() {
        return id;
    }

    public String getTerm(){
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


    public boolean isCorrect(String term){
        if(this.term == term)
            return true;
        else
            return false;
    }

    public int getLearningLevel(){
        return learningLevel;
    }

    public void increaseLearningLevel(){
        if (learningLevel<5)
            learningLevel++;
    }

    public void resetLearningLevel(){
        learningLevel = 0;
    }


}
