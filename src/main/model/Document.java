package model;

import java.io.File;
import java.util.*;

import java.util.Scanner;
import java.util.TreeSet;

import java.io.FileNotFoundException;

// A document object represented by the text, and a ListOfSpellingErrors
public class Document {


    private String text;
    private ListOfSpellingErrors errorList;
    private ArrayList<String> words = new ArrayList<>();
    private Boolean isSpellchecked = false;

    private static final List<Character> nonPunctChars = Arrays.asList('\'');

    // REQUIRES: text must be size > 0
    public Document(String text) {
        if (text.length() > 0) {
            this.text = text;
            this.errorList = new ListOfSpellingErrors();
            fixWhitespace();
            fixWhitespacePunc();
            breakTextIntoWordArray();
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
    public String fixWhitespacePunc() {
        text = text.replaceAll("\\s+(?=\\p{Punct})", "");
        return text;
    }

    // EFFECTS: Returns ordered ArrayList with each word and non-letter char broken into separate elements
    @SuppressWarnings("checkstyle:MethodLength")
    public ArrayList<String> breakTextIntoWordArray() {
        int location = 0;
        StringBuilder currentWord = new StringBuilder();

        while (location < text.length()) {

            Character c = text.charAt(location);

            if (Character.isLetter(c) || Character.isDigit(c) || nonPunctChars.contains(c)) {
                currentWord.append(c);
            } else if (c == ' ') {
                words.add(currentWord.toString());
                if (location + 1 != text.length()) {
                    words.add(" ");
                }
                currentWord = new StringBuilder();
            } else {
                words.add(currentWord.toString());
                words.add(String.valueOf(c));
                if (location + 1 != text.length()) {
                    words.add(" ");
                }
                currentWord = new StringBuilder();
                location++;
            }
            location++;
        }
        return words;
    }

    public String putWordArrayBackTogether() {
        String putBack = "";
        for (String w: words) {
            putBack += w;
        }
        return putBack;
    }


    public ListOfSpellingErrors spellcheck() throws FileNotFoundException {
        TreeSet<String> wordDictionary = new TreeSet<>();
        File file = new File("/Users/dmitriy/Documents/CPSC210/project/project_u7j5a/data/dictionary.txt");
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            wordDictionary.add(scan.nextLine().toLowerCase());
        }

        ListOfSpellingErrors errors = new ListOfSpellingErrors();

        for (String w: words) {
            if (w.length() > 1 || (w.length() == 1 && Character.isLetter(w.charAt(0)))) {
                if (wordDictionary.contains(w.toLowerCase())) {
                    // TODO
                    // makeSpellingError();
                    // errors.add(error);

                }
            }

        }
        return errors;
    }

}


















