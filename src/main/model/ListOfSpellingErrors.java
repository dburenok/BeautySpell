package model;

import java.util.ArrayList;

public class ListOfSpellingErrors {

    private ArrayList<SpellingError> errorList;

    public ListOfSpellingErrors() {
        this.errorList = errorList = new ArrayList<SpellingError>();
    }

    public int numErrors() {
        return errorList.size();
    }

    public SpellingError nextError() {
        return errorList.get(0);
    }

}
