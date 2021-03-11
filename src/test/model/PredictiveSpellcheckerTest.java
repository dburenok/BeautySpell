package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class PredictiveSpellcheckerTest {

    PredictiveSpellchecker checker;

    @BeforeEach
    public void setup() {
        checker = new PredictiveSpellchecker();
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
        String[] words = new String[] { "o" };
        HashSet<String> wordsSet = new HashSet<>(Arrays.asList(words));
        HashSet<String> generatedWordSet = checker.generateTypingErrorPaths(word);
        assertEquals(wordsSet, generatedWordSet);
    }

    @Test
    void testGenerateOptionsTwoLong() {
        String word = "of";
        String[] words = new String[] { "og", "of", "ot", "ov", "oc", "or", "od" };
        HashSet<String> wordsSet = new HashSet<>(Arrays.asList(words));
        HashSet<String> generatedWordSet = checker.generateTypingErrorPaths(word);
        assertEquals(wordsSet, generatedWordSet);
    }

//    @Test
//    void compareClosenessOfTwoWords() {
//        double result1 = checker.compareCloseness("milk", "melk");
//        double result2 = checker.compareCloseness("milk", "oolk");
//        assertTrue(result1 > result2);
//    }

}
