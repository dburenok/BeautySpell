package model;

import javax.xml.ws.handler.HandlerResolver;
import java.util.*;

public class PredictiveSpellchecker {

    private final HashMap<String, HashSet<String>> options;
    HashSet<String> dictionary;

    public PredictiveSpellchecker(HashSet<String> dict) {
        this.options = new HashMap<>();
        this.dictionary = dict;
        initializeKeyNeighbours();
    }

    // EFFECTS: produces set of typing paths for given word
    public HashSet<String> generateTypingErrorPaths(String word) {
        ArrayList<HashSet<String>> optionsList = wordToOptionsList(word);

        while (optionsList.size() > 1) {
            HashSet<String> product = cartesianProduct(optionsList.get(0), optionsList.get(1));
            optionsList.remove(0);
            optionsList.remove(0);
            optionsList.add(0, product);
        }

        return optionsList.get(0);

    }

    // EFFECTS: produces the cartesian product of the given sets
    public HashSet<String> cartesianProduct(HashSet<String> a, HashSet<String> b) {
        HashSet<String> words = new HashSet<>();
        for (String i : a) {
            for (String j : b) {
                words.add(i + j);
            }
        }
        return words;
    }

    // EFFECTS: converts word to list of neighbour keys
    public ArrayList<HashSet<String>> wordToOptionsList(String word) {
        ArrayList<HashSet<String>> optionsList = new ArrayList<>();
        optionsList.add(new HashSet<>(Collections.singletonList(String.valueOf(word.charAt(0)))));
        for (int i = 1; i < word.length(); i++) {
            optionsList.add(options.get(String.valueOf(word.charAt(i))));
        }
        return optionsList;
    }

    // REQUIRES: word.length() > 0
    // EFFECTS: produce list of words which are real words, if any
    public HashSet<String> getStrictPathWordMatches(String word) {
        HashSet<String> options = generateTypingErrorPaths(word);
        HashSet<String> realWords = new HashSet<>();
        for (String s : options) {
            if (dictionary.contains(s)) {
                realWords.add(s);
            }
        }
        return realWords;
    }

    // REQUIRES: word.length() > 0
    // EFFECTS: produce string power set of given string
    public HashSet<String> stringPowerSet(String str) {
        final int MIN_LENGTH = str.length() - 2;
        HashSet<String> result = new HashSet<>();
        String tempString = "";
        for (int len = MIN_LENGTH; len <= str.length(); len++) {
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

    // EFFECTS: produce match score of how similar the two strings are
    public int compareCloseness(String s1, String s2) {
        Set<String> lettersS1 = new HashSet<>(Arrays.asList(s1.split("")));
        Set<String> lettersS2 = new HashSet<>(Arrays.asList(s2.split("")));
        lettersS1.retainAll(lettersS2);
        return lettersS1.size();
    }

    // EFFECTS: produce a single strict suggestion for word
    public String getStrictSuggestion(String entry) {
        HashSet<String> entryOptions = getStrictPathWordMatches(entry);

        double highestScore = 0;
        String closestMatch = "";

        for (String s : entryOptions) {
            double currentScore = intersectionPercent(stringPowerSet(entry), stringPowerSet(s));
            if (currentScore > highestScore) {
                highestScore = currentScore;
                closestMatch = s;
            }
        }
        return closestMatch;
    }

    // EFFECTS: produce percentage of intersection of a and b compared to set a
    public double intersectionPercent(HashSet<String> a, HashSet<String> b) {
        double firstSetSize = a.size();

        Set<String> intersection = new HashSet<>(a);
        intersection.retainAll(b);

        return intersection.size() / firstSetSize;
    }

    // EFFECTS: initialize neighbour key
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

    // EFFECTS: initialize neighbour key
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
        options.put("alphabet", new HashSet<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")));
    }

}
