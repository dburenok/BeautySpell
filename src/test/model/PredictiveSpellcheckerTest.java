package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class PredictiveSpellcheckerTest {

    PredictiveSpellchecker checker;
    DocumentLibrary testDocLibrary;

    @BeforeEach
    public void setup() throws FileNotFoundException {
        testDocLibrary = new DocumentLibrary();
        checker = new PredictiveSpellchecker(testDocLibrary.getDictionary());
    }

    @Test
    void testCartesianProduct() {
        HashSet<String> setA = new HashSet<>();
        HashSet<String> setB = new HashSet<>();
        setA.add("a");
        setA.add("b");
        setA.add("c");
        setB.add("x");
        setB.add("y");
        String[] manualResult = new String[]{"ax", "ay", "bx", "by", "cx", "cy"};
        HashSet<String> manualResultSet = new HashSet<>(Arrays.asList(manualResult));
        HashSet<String> result = checker.cartesianProduct(setA, setB);
        assertEquals(manualResultSet, result);
    }

    @Test
    void testGenerateOptionsOneLong() {
        String word = "o";
        String[] words = new String[]{"o"};
        HashSet<String> wordsSet = new HashSet<>(Arrays.asList(words));
        HashSet<String> generatedWordSet = checker.generateTypingErrorPaths(word);
        assertEquals(wordsSet, generatedWordSet);
    }

    @Test
    void testGenerateOptionsTwoLong() {
        String word = "of";
        String[] words = new String[]{"og", "of", "ot", "ov", "oc", "or", "od"};
        HashSet<String> wordsSet = new HashSet<>(Arrays.asList(words));
        HashSet<String> generatedWordSet = checker.generateTypingErrorPaths(word);
        assertEquals(wordsSet, generatedWordSet);
    }

    @Test
    void compareClosenessOfTwoWords() {
        assertTrue(checker.compareCloseness("milk", "melk") > checker.compareCloseness("milk", "oolk"));
        assertTrue(checker.compareCloseness("doggy", "doppy") > checker.compareCloseness("doggy", "bobby"));
    }

}
