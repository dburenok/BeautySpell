package model;

import exceptions.CartesianProductException;
import exceptions.DictException;
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
    public void setup() throws FileNotFoundException, DictException {
        testDocLibrary = new DocumentLibrary();
        checker = new PredictiveSpellchecker(testDocLibrary.getDictionary());
    }

    @Test
    void testCartesianProduct() throws CartesianProductException {
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
    void testGenerateOptionsOneLong() throws CartesianProductException {
        String word = "o";
        String[] words = new String[]{"o"};
        HashSet<String> wordsSet = new HashSet<>(Arrays.asList(words));
        HashSet<String> generatedWordSet = checker.generateTypingErrorPaths(word);
        assertEquals(wordsSet, generatedWordSet);
    }

    @Test
    void testGenerateOptionsTwoLong() throws CartesianProductException {
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


    // PHASE 4

    @Test
    void passNonEmptyDictionaryNoFail() {
        try {
            DocumentLibrary testDocLibrary2 = new DocumentLibrary();
            PredictiveSpellchecker checker = new PredictiveSpellchecker(testDocLibrary2.getDictionary());
            // pass
        } catch (DictException | FileNotFoundException e) {
            fail();
        }
    }

    @Test
    void passEmptyDictionaryToFail() {
        try {
            DocumentLibrary testDocLibrary2 = new DocumentLibrary();
            PredictiveSpellchecker checker = new PredictiveSpellchecker(null);
            fail();
        } catch (DictException e) {
            // pass
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    @Test
    void testCartesianProductException() {
        try {
            HashSet<String> s1 = new HashSet<>();
            HashSet<String> s2 = new HashSet<>();
            HashSet<String> set = checker.cartesianProduct(s1, s2);
            fail();
        } catch (CartesianProductException e) {
            // pass
        }
    }

    @Test
    void testCartesianProductPass() {
        try {
            HashSet<String> s1 = new HashSet<>();
            HashSet<String> s2 = new HashSet<>();
            s1.add("a");
            s2.add("b");
            HashSet<String> set = checker.cartesianProduct(s1, s2);
            // pass
        } catch (CartesianProductException e) {
            fail();
        }
    }

}
