package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    private Document testDocument;

    @BeforeEach
    void runBefore() { }

    @Test
    void testConstructor() {
        String text = "This is some text.";
        testDocument = new Document(text);
        assertEquals(text, testDocument.getText());
    }

    @Test
    void testConstructorEmptyStringGetText() {
        String text = "";
        testDocument = new Document(text);
        assertFalse(testDocument == null);
        assertEquals(null, testDocument.getText());
    }

    @Test
    void testCleanWhitespace() {
        String text = " This is a nice freakin' car  right here , boss . Where's that 200 dollar car? 40% off, right? ";
        testDocument = new Document(text);
        String correctText = "This is a nice freakin' car right here, boss. Where's that 200 dollar car? 40% off, right?";
        testDocument.fixWhitespace();
        testDocument.fixWhitespacePunc();
        String result = testDocument.getText();
        assertEquals(correctText, result);
    }

    @Test
    void testBreakTextIntoArray() {
        String text = "Here is some text, it needs breaking up.";
        testDocument = new Document(text);
        testDocument.breakTextIntoWordArray();
        ArrayList<String> wordList = new ArrayList<String>(
                Arrays.asList("Here", " ", "is", " ", "some", " ", "text", ",", " ", "it", " ", "needs", " ", "breaking", " ", "up", "."));
        assertEquals(wordList, testDocument.getWordsArray());
    }

    @Test
    void testBreakTextIntoArrayAndPutBackTogether() {
        String text = "Here is some text, it needs breaking up and putting back together! Do it!";
        testDocument = new Document(text);
        testDocument.breakTextIntoWordArray();
        assertEquals(text, testDocument.putWordArrayBackTogether());
    }

}







