package model;

import java.util.ArrayList;

public class ListOfSpellingErrors {

    private ArrayList<SpellingError> errors;

    public ListOfSpellingErrors() {
        this.errors = new ArrayList<>();
    }

    // EFFECTS: returns number of SpellingError objects
    public int numErrors() {
        return errors.size();
    }

    // REQUIRES: errors not empty
    // MODIFIES: this
    // EFFECTS: remove the first error in list, return it
    public SpellingError getNextError() {
        SpellingError e = errors.get(0);
        errors.remove(0);
        return e;
    }

    // MODIFIES: this
    // EFFECTS: add given spelling error to error list
    public void addError(SpellingError e) {
        this.errors.add(e);
    }

    // REQUIRES: errors not empty
    // EFFECTS: display errors to console.
    public void showErrors(String text) {
        System.out.println();
        System.out.println("This document has " + errors.size() + " errors.");
        for (SpellingError e : errors) {
            e.showError(text);
        }
    }

}
