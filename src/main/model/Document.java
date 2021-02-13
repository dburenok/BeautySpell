package model;

import java.io.File;
import java.util.*;

import java.util.Scanner;

import java.io.FileNotFoundException;

// A document object represented by the text, and a ListOfSpellingErrors
public class Document {

    private String text;
    private ListOfSpellingErrors errors;
    private ArrayList<String> wordsArray;
    private Boolean isSpellchecked;

    HashSet<String> wordDictionary;

    // REQUIRES: text must be size > 0
    public Document(String text) {
        if (text.length() > 0) {
            this.text = text;
            this.wordsArray = new ArrayList<>();
            this.errors = new ListOfSpellingErrors();
            isSpellchecked = false;
        }
    }

    public ArrayList<String> getWordsArray() {
        return this.wordsArray;
    }

    public String getText() {
        return text;
    }

    // REQUIRES: dictionary.txt file must be present at [project_dir]/data/dictionary.txt
    // MODIFIES: this.wordDictionary
    // EFFECTS: loads dictionary into a HashSet
    // TODO: move this method into DocumentLibrary as it's redundant to load a new dict for each document
    public void loadDictionary() throws FileNotFoundException {
        wordDictionary = new HashSet<>();
        String dictPath = new File("").getAbsolutePath().concat("/data/dictionary.txt");
        File file = new File(dictPath);
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            wordDictionary.add(scan.nextLine().toLowerCase());
        }
    }

    // REQUIRES: s.length() > 0
    // MODIFIES: this
    // EFFECTS: manually replace document text, set isSpellchecked bool to false
    public String replaceText(String s) {
        this.text = s;
        this.isSpellchecked = false;
        return text;
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
    public void breakTextIntoWordArray() {
        int location = 0;
        StringBuilder currentWord = new StringBuilder();

        while (location < text.length()) {

            Character c = text.charAt(location);

            char nonPunctChar = '\'';

            if (Character.isLetter(c) || Character.isDigit(c) || c == nonPunctChar) {
                currentWord.append(c);
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
        int position = 0;
        for (String w: wordsArray) {
            position += w.length();
            if (isWord(w)) {
                if (!wordDictionary.contains(w.toLowerCase())) {
                    // TODO
                    SpellingError error = new SpellingError(position - w.length(), position, w, "NON");
                    errors.addError(error);
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
        errors.showErrors(text);
    }

    public int numErrors() {
        return errors.numErrors();
    }

}


















