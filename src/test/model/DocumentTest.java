package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    private Document testDocument;

    @BeforeEach
    void runBefore() {}

    @Test
    void testConstructor() {
        String text = "This is some text.";
        testDocument = new Document(text);
        assertEquals(text, testDocument.getText());
        assertNotNull(testDocument);
    }

    @Test
    void testConstructorEmptyStringGetText() {
        String text = "";
        testDocument = new Document(text);
        assertNotNull(testDocument);
        assertNull(testDocument.getText());
    }

    @Test
    void testCleanWhitespace() {
        String text = " This is a nice freakin' car  right here , boss . Where's that 200 dollar car? 40% off, right? ";
        testDocument = new Document(text);
        String correctText = "This is a nice freakin' car right here, boss. Where's that 200 dollar car? 40% off, right?";
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        String result = testDocument.getText();
        assertEquals(correctText, result);
    }

    @Test
    void testCleanPunctuationWhitespace() {
        String text = "Some messy , weird  ,  text right here man . ";
        testDocument = new Document(text);
        String correctText = "Some messy, weird, text right here man.";
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        String result = testDocument.getText();
        assertEquals(correctText, result);
    }

    @Test
    void testBreakTextIntoArray() {
        String text = "Here is some text, it needs breaking up.";
        testDocument = new Document(text);
        ArrayList<String> wordList = new ArrayList<>(
                Arrays.asList("Here", " ", "is", " ", "some", " ", "text", ",", " ", "it", " ", "needs", " ", "breaking", " ", "up", "."));
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        assertEquals(wordList, testDocument.getWordsArray());
    }

    @Test
    void testBreakTextIntoArrayAndPutBackTogether() {
        String text = "Here is some text, it needs breaking up and putting back together! Do it.";
        testDocument = new Document(text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        assertEquals(text, testDocument.putWordArrayBackTogether());
    }

    @Test
    void testNoSpellingErrors() throws FileNotFoundException {
        String text = "This sentence has no typos.";
        testDocument = new Document(text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocument.loadDictionary();
        testDocument.spellcheck();
        assertEquals(0, testDocument.numErrors());
    }

    @Test
    void testTwoSpellingErrors() throws FileNotFoundException {
        String text = "Thiss sentence haz two typos.";
        testDocument = new Document(text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocument.loadDictionary();
        testDocument.spellcheck();
        assertEquals(2, testDocument.numErrors());
    }

    @Test
    void testFourSpellingErrors() throws FileNotFoundException {
        String text = "Thiss sentenc haz four typoss.";
        testDocument = new Document(text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocument.loadDictionary();
        testDocument.spellcheck();
        assertEquals(4, testDocument.numErrors());
    }

}







