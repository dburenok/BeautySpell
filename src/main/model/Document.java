package model;

import java.util.*;

// A document object represented by the text, and a ListOfSpellingErrors
public class Document {

    private String text;
    private ListOfSpellingErrors listOfErrors;
    private ArrayList<String> wordsArray;
    private Boolean isSpellchecked;
    HashSet<String> wordDictionary;


    // REQUIRES: text must be size > 0
    public Document(String text) {
        if (text.length() > 0) {
            this.text = text;
            this.wordsArray = new ArrayList<>();
            this.listOfErrors = new ListOfSpellingErrors();
            isSpellchecked = false;
        }
    }

    public ArrayList<String> getWordsArray() {
        return this.wordsArray;
    }

    public String getText() {
        return text;
    }

    // REQUIRES: s.length() > 0
    // MODIFIES: this
    // EFFECTS: manually replace document text, set isSpellchecked bool to false
    public void replaceText(String s) {
        this.text = s;
        this.isSpellchecked = false;
        breakTextIntoWordArray();
        runSpellcheck();
    }

    // MODIFIES: this.text
    // EFFECTS: Replaces consecutive whitespaces with a single whitespace
    public void fixWhitespace() {
        text = text.trim().replaceAll(" +", " ");
    }

    // MODIFIES: this.text
    // EFFECTS: Removes extra whitespaces before punctuation
    public void fixPunctuationWhitespace() {
        text = text.replaceAll("\\s+(?=\\p{Punct})", "");
    }

    // MODIFIES: this
    // EFFECTS: Returns ordered ArrayList with each word and non-letter char broken into separate elements
    public ArrayList<String> breakTextIntoWordArray() {
        int location = 0;
        StringBuilder currentWord = new StringBuilder();
        this.wordsArray = new ArrayList<>();

        while (location < text.length()) {

            Character c = text.charAt(location);

            char nonPunctChar = '\'';

            if (Character.isLetter(c) || Character.isDigit(c) || c == nonPunctChar) {
                currentWord.append(c);
                handleLastWord(location, currentWord);
            } else if (c == ' ') {
                wordsArray.add(currentWord.toString());
                insertWhitespaceIfNotOnLastWord(location);
                currentWord = new StringBuilder();
            } else {
                wordsArray.add(currentWord.toString());
                wordsArray.add(String.valueOf(c));
                insertWhitespaceIfNotOnLastWord(location);
                currentWord = new StringBuilder();
                location++;
            }
            location++;
        }
        return wordsArray;
    }

    // MODIFIES: this.wordsArray
    // EFFECTS: if on last index in string and it's a char, add the string to wordsArray
    public void handleLastWord(int location, StringBuilder currentWord) {
        if (location + 1 == text.length()) {
            wordsArray.add(currentWord.toString());
        }
    }

    // MODIFIES: this.wordsArray
    // EFFECTS: if while loop not on last word, add a whitespace to the wordsArray. On the last word, we don't do that.
    public void insertWhitespaceIfNotOnLastWord(int location) {
        if (location + 1 < text.length()) {
            wordsArray.add(" ");
        }
    }

    // EFFECTS: returns the rebuilt string from the wordsArray - mostly for consistency and testing
    public String putWordArrayBackTogether() {
        StringBuilder putBack = new StringBuilder();
        for (String w: wordsArray) {
            putBack.append(w);
        }
        return putBack.toString();
    }

    // REQUIRES: this.text.length() > 0
    // MODIFIES: this
    // EFFECTS: for every array word not in dictionary, new SpellingError object is added to ListOfSpellingErrors
    public void runSpellcheck() {
        this.listOfErrors = new ListOfSpellingErrors();
        int position = 0;
        for (String w: wordsArray) {
            position += w.length();
            if (isWord(w)) {
                if (!wordDictionary.contains(w.toLowerCase())) {
                    // TODO
                    SpellingError error = new SpellingError(position - w.length(), position, w, "NON");
                    listOfErrors.addError(error);
                }
            }
        }
        isSpellchecked = true;
    }

    // EFFECTS: Helper method: if current word is an actual word return true, if a symbol/punctuation/etc: return false
    public Boolean isWord(String w) {
        return w.length() > 1 && Character.isLetter(w.charAt(0)) || w.length() == 1 && Character.isLetter(w.charAt(0));
    }

    // EFFECTS: displays current errors in document
    public void showErrors() {
        if (!isSpellchecked) {
            System.out.println();
            System.out.println("No errors to show! Please run spellcheck first.");
        } else {
            listOfErrors.showErrors(text);
        }
    }

    public int numErrors() {
        return listOfErrors.numErrors();
    }

    public SpellingError getNextError() {
        return this.listOfErrors.getNextError();
    }

    public void addDictionary(HashSet<String> dict) {
        this.wordDictionary = dict;
    }

}


















