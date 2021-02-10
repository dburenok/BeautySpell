package ui;

import sun.awt.HKSCS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

// dont need to test this
public class BeautySpell {

    private static final List<Character> nonPunctChars = Arrays.asList('\'');

    private boolean running = true;

    public BeautySpell() throws IOException {
        runBeautySpell();
    }

    public void runBeautySpell() throws IOException {
//        File file = new File("/Users/dmitriy/Documents/CPSC210/project/project_u7j5a/data/dictionary.txt");
//        Scanner scan = new Scanner(file);
//
//        TreeSet<String> dictSet = new TreeSet<String>();
//
//        // creates dict set
//        while (scan.hasNextLine()) {
//            dictSet.add(scan.nextLine());
//        }
//
        String ttt = "In the above image, the navigable set extends the sorted set interface. Since a set doesn’t retain the insertion order, the navigable set interface provides the implementation to navigate through the Set. The class which implements the navigable set is a TreeSet which is an implementation of a self-balancing tree. Therefore, this interface provides us with a way to navigate through this tree.";
//        // need to remove commas and periods, then lowerscase everything
//        // String[] textSplit = text.split(" ");

        String text = " This is some  nice  fuckin' text right here , boss  . How's that 200 dollar car? 40% off right? ";
        text = cleanWhitespace(text);

        ArrayList<String> result = breakTextIntoArray(text);
        String putBack = "";

        for (String s: result) {
            putBack += s;
        }

        System.out.println(text.equals(putBack));

    }

    // REQUIRES: String length > 0
    // EFFECTS: Replaces consecutive whitespaces with a single whitespace
    public static String cleanWhitespace(String s) {
        return s.trim().replaceAll(" +", " ").replaceAll("\\s+(?=\\p{Punct})", "");
    }

    // REQUIRES: String length > 0
    // EFFECTS: Returns ordered ArrayList with each word and non-letter char broken into separate elements
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

}
