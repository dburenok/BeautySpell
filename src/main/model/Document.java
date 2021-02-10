package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A document object represented by the text, and a ListOfSpellingErrors
public class Document {


    private String text;
    private ListOfSpellingErrors errorList;
    private static final List<Character> nonPunctChars = Arrays.asList('\'');

    public Document(String text) {
        this.text = text;
        this.errorList = new ListOfSpellingErrors();
    }

    // REQUIRES: String length > 0
    // EFFECTS: Replaces consecutive whitespaces with a single whitespace
    public static String cleanWhitespace(String s) {
        return s.trim().replaceAll(" +", " ").replaceAll("\\s+(?=\\p{Punct})", "");
    }

    // REQUIRES: String length > 0
    // EFFECTS: Returns ordered ArrayList with each word and non-letter char broken into separate elements
    @SuppressWarnings("checkstyle:MethodLength")
    public static ArrayList<String> breakTextIntoArray(String text) {
        int location = 0;
        String currentWord = "";
        ArrayList<String> words = new ArrayList<>();

        while (location < text.length()) {

            Character c = text.charAt(location);

            if (Character.isLetter(c) || Character.isDigit(c) || nonPunctChars.contains(c)) {
                currentWord += c;
            } else if (c == ' ') {
                words.add(currentWord);
                if (location + 1 != text.length()) {
                    words.add(" ");
                }
                currentWord = "";
            } else {
                words.add(currentWord);
                words.add(String.valueOf(c));
                if (location + 1 != text.length()) {
                    words.add(" ");
                }
                currentWord = "";
                location++;
            }

            location++;
        }
        return words;
    }

    public static void main(String[] args) {
        String text = " This is some  nice  fuckin' text right here , boss  . How's that 200 dollar car? 40% off right? ";
        text = cleanWhitespace(text);

        ArrayList<String> result = breakTextIntoArray(text);
        String putBack = "";

        for (String s: result) {
            putBack += s;
        }

        System.out.println(text.equals(putBack));
    }

}
