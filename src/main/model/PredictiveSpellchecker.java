package model;

import java.io.FileNotFoundException;
import java.util.*;

public class PredictiveSpellchecker {

    private final HashMap<String, HashSet<String>> options;
    HashSet<String> wordDictionary;

    PredictiveSpellchecker() {
        options = new HashMap<>();
        initializeKeyNeighbours();
    }

    public HashSet<String> generateOptions(String word) {
        ArrayList<HashSet<String>> optionsList = wordToOptionsList(word);

        while (optionsList.size() > 1) {
            HashSet<String> product = cartesianProduct(optionsList.get(0), optionsList.get(1));
            optionsList.remove(0);
            optionsList.remove(0);
            optionsList.add(0, product);
        }

        return optionsList.get(0);

    }

    public HashSet<String> cartesianProduct(HashSet<String> a, HashSet<String> b) {
        HashSet<String> words = new HashSet<>();
        for (String i : a) {
            for (String j : b) {
                words.add(i + j);
            }
        }
        return words;
    }

    public ArrayList<HashSet<String>> wordToOptionsList(String word) {
        ArrayList<HashSet<String>> optionsList = new ArrayList<>();
        optionsList.add(new HashSet<>(Collections.singletonList(String.valueOf(word.charAt(0)))));
        for (int i = 1; i < word.length(); i++) {
            optionsList.add(options.get(String.valueOf(word.charAt(i))));
        }
        return optionsList;
    }

    public HashSet<String> checkWordInDict(String word, HashSet<String> wordDictionary) {

        HashSet<String> options = generateOptions(word);
        HashSet<String> realWords = new HashSet<>();
        for (String s : options) {
            if (wordDictionary.contains(s)) {
                realWords.add(s);
            }
        }
        return realWords;
    }


    public double compareCloseness(String s1, String s2) {
        return 0;
    }

    public HashSet<String> stringPowerSet(String str) {
        HashSet<String> result = new HashSet<>();
        String tempString = "";
        for (int len = 1; len <= str.length(); len++) {
            for (int i = 0; i <= str.length() - len; i++) {
                int j = i + len - 1;
                for (int k = i; k <= j; k++) {
                    tempString = tempString + str.charAt(k);
                }
                result.add(tempString);
                tempString = "";

            }
        }
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        PredictiveSpellchecker checker = new PredictiveSpellchecker();
        DocumentLibrary doclib = new DocumentLibrary();
        HashSet<String> dict = doclib.wordDictionary;

        String word = "papre";

        HashSet<String> opts = checker.checkWordInDict(word, dict);

        System.out.println("Word entered: " + word);

        for (String s : opts) {
            System.out.print(s + ", conf: ");
            System.out.print(checker.intersectionSizeRatio(checker.stringPowerSet(word), checker.stringPowerSet(s)));
            System.out.print(" ");
            System.out.println(checker.stringPowerSet(s));
        }
    }

    public double intersectionSizeRatio(HashSet<String> a, HashSet<String> b) {
        double firstSetSize = a.size();
        a.retainAll(b);
        return Math.round(a.size() / firstSetSize * 1000.0) / 1000.0;
    }


    private void initializeKeyNeighbours() {
        options.put("a", new HashSet<>(Arrays.asList("a", "q", "w", "s", "x", "z")));
        options.put("b", new HashSet<>(Arrays.asList("b", "v", "g", "h", "n")));
        options.put("c", new HashSet<>(Arrays.asList("c", "x", "d", "f", "v")));
        options.put("d", new HashSet<>(Arrays.asList("d", "s", "e", "r", "f", "c", "x")));
        options.put("e", new HashSet<>(Arrays.asList("e", "r", "d", "s", "w")));
        options.put("f", new HashSet<>(Arrays.asList("f", "d", "r", "t", "g", "v", "c")));
        options.put("g", new HashSet<>(Arrays.asList("g", "f", "t", "y", "h", "b", "v")));
        options.put("h", new HashSet<>(Arrays.asList("h", "g", "y", "u", "j", "n", "b")));
        options.put("i", new HashSet<>(Arrays.asList("i", "u", "j", "k", "o")));
        options.put("j", new HashSet<>(Arrays.asList("j", "h", "u", "i", "k", "m", "n")));
        options.put("k", new HashSet<>(Arrays.asList("k", "j", "i", "o", "l", "m")));
        options.put("l", new HashSet<>(Arrays.asList("l", "k", "o", "p")));
        initializeKeyNeighboursB();
    }

    private void initializeKeyNeighboursB() {
        options.put("m", new HashSet<>(Arrays.asList("m", "n", "j", "k")));
        options.put("n", new HashSet<>(Arrays.asList("n", "b", "h", "j", "m")));
        options.put("o", new HashSet<>(Arrays.asList("o", "i", "k", "l", "p")));
        options.put("p", new HashSet<>(Arrays.asList("p", "l", "o")));
        options.put("q", new HashSet<>(Arrays.asList("q", "a", "w")));
        options.put("r", new HashSet<>(Arrays.asList("r", "e", "d", "f", "t")));
        options.put("s", new HashSet<>(Arrays.asList("s", "a", "w", "e", "d", "x", "z")));
        options.put("t", new HashSet<>(Arrays.asList("t", "r", "f", "g", "y")));
        options.put("u", new HashSet<>(Arrays.asList("u", "y", "h", "j", "i")));
        options.put("v", new HashSet<>(Arrays.asList("v", "c", "f", "g", "b")));
        options.put("w", new HashSet<>(Arrays.asList("w", "q", "a", "s", "e")));
        options.put("x", new HashSet<>(Arrays.asList("x", "z", "s", "d", "c")));
        options.put("y", new HashSet<>(Arrays.asList("y", "t", "g", "h", "u")));
        options.put("z", new HashSet<>(Arrays.asList("z", "a", "s", "x")));
    }

}
