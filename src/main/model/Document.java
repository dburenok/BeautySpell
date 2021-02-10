package model;

// A document object represented by the text, and a ListOfSpellingErrors
public class Document {

    private String text;
    private ListOfSpellingErrors errorList;

    public Document(String text) {
        this.text = text;
        this.errorList = new ListOfSpellingErrors();
    }

}
