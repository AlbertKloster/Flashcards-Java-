package flashcards;

public class Card {
    private final String term;
    private final String definition;
    private int errors;

    public Card(String term, String definition, int errors) {
        this.term = term;
        this.definition = definition;
        this.errors = errors;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    public int getErrors() {
        return errors;
    }

    public void increaseErrors() {
        errors++;
    }

    public void resetErrors() {
        errors = 0;
    }

    @Override
    public String toString() {
        return term + (char) 31 + definition + (char) 31 + errors ;
    }
}
