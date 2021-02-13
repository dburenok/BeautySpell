package model;

import java.util.ArrayList;

public class ListOfSpellingErrors {

    private ArrayList<SpellingError> errors;

    public ListOfSpellingErrors() {
        this.errors = new ArrayList<>();
    }

    public int numErrors() {
        return errors.size();
    }

    public SpellingError getNextError() {
        return errors.get(0);
    }

    public void addError(SpellingError e) {
        this.errors.add(e);
    }

    public void deleteFirstError() {
        this.errors.remove(0);
    }

    public void showErrors(String text) {
        System.out.println("This document has " + errors.size() + " errors.");
        for (SpellingError e : errors) {
            e.showError(text);
        }
    }

}
