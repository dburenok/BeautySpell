package model;

import java.io.File;
import java.util.*;

import java.util.Scanner;

import java.io.FileNotFoundException;

// A document object represented by the text, and a ListOfSpellingErrors
public class Document {

    private String text;
    private ListOfSpellingErrors errors;
    private ArrayList<String> words = new ArrayList<>();
    private Boolean isSpellchecked = false;

    HashSet<String> wordDictionary;

    private final Character nonPunctChar = '\'';

    // REQUIRES: text must be size > 0
    public Document(String text) {
        if (text.length() > 0) {
            this.text = text;
            this.errors = new ListOfSpellingErrors();
        }
    }

    public void loadDictionary() throws FileNotFoundException {
        wordDictionary = new HashSet<>();
        File file = new File("/home/dmitriy/CPSC210/project_u7j5a/data/dictionary.txt");
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            wordDictionary.add(scan.nextLine().toLowerCase());
        }
    }

    public String setText(String s) {
        this.text = s;
        this.isSpellchecked = false;
        return text;
    }

    public ArrayList<String> getWordsArray() {
        return this.words;
    }

    public String getText() {
        return text;
    }

    // MODIFIES: this.text
    // EFFECTS: Replaces consecutive whitespaces with a single whitespace
    public String fixWhitespace() {
        text = text.trim().replaceAll(" +", " ");
        return text;
    }

    // MODIFIES: this.text
    // EFFECTS: Removes extra whitespace before punctuation
    public String fixPunctuationWhitespace() {
        text = text.replaceAll("\\s+(?=\\p{Punct})", "");
        return text;
    }

    // EFFECTS: Returns ordered ArrayList with each word and non-letter char broken into separate elements
    public ArrayList<String> breakTextIntoWordArray() {
        int location = 0;
        StringBuilder currentWord = new StringBuilder();

        while (location < text.length()) {

            Character c = text.charAt(location);

            if (Character.isLetter(c) || Character.isDigit(c) || c == nonPunctChar) {
                currentWord.append(c);
            } else if (c == ' ') {
                words.add(currentWord.toString());
                insertWhitespaceIfNotOnLastWord(location);
                currentWord = new StringBuilder();
            } else {
                words.add(currentWord.toString());
                words.add(String.valueOf(c));
                insertWhitespaceIfNotOnLastWord(location);
                currentWord = new StringBuilder();
                location++;
            }
            location++;
        }
        return words;
    }

    public void insertWhitespaceIfNotOnLastWord(int location) {
        if (location + 1 < text.length()) {
            words.add(" ");
        }
    }

    public String putWordArrayBackTogether() {
        StringBuilder putBack = new StringBuilder();
        for (String w: words) {
            putBack.append(w);
        }
        return putBack.toString();
    }


    public ListOfSpellingErrors spellcheck() {
        int position = 0;
        for (String w: words) {
            position += w.length();
            if (w.length() > 1 || (w.length() == 1 && Character.isLetter(w.charAt(0)))) {
                if (!wordDictionary.contains(w.toLowerCase())) {
                    // TODO
                    SpellingError error = new SpellingError(position - w.length(), position, w, "NON");
                    errors.addError(error);
                }
            }
        }
        return errors;
    }

    public void showErrors() {
        errors.showErrors(text);
    }

    public int numErrors() {
        return errors.numErrors();
    }

}


















