package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    private Document testDocument;
    private DocumentLibrary testDocLib;

    @BeforeEach
    void setup() throws FileNotFoundException {
        testDocLib = new DocumentLibrary();
    }

    @Test
    void testConstructor() {
        String name = "DocName";
        String text = "This is some text.";
        testDocument = new Document(name, text);
        assertEquals(text, testDocument.getText());
        assertNotNull(testDocument);
    }

    @Test
    void testConstructorEmptyStringGetText() {
        String name = "DocName";
        String text = "";
        testDocument = new Document(name, text);
        assertNotNull(testDocument);
        assertEquals("", testDocument.getText());
    }

    @Test
    void testCleanWhitespace() {
        String name = "DocName";
        String text = " This is a nice freakin' car  right  here , boss . Where's that 200 dollar car? 40% off, right? ";
        testDocument = new Document(name, text);
        String correctText = "This is a nice freakin' car right here, boss. Where's that 200 dollar car? 40% off, right?";
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        String result = testDocument.getText();
        assertEquals(correctText, result);
    }

    @Test
    void testCleanPunctuationWhitespace() {
        String name = "DocName";
        String text = "Some messy , weird  ,  text right here man . ";
        testDocument = new Document(name, text);
        String correctText = "Some messy, weird, text right here man.";
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        String result = testDocument.getText();
        assertEquals(correctText, result);
    }

    @Test
    void testBreakTextIntoArray() {
        String name = "DocName";
        String text = "Here is some text, it needs breaking up.";
        testDocument = new Document(name, text);
        ArrayList<String> wordList = new ArrayList<>(
                Arrays.asList("Here", " ", "is", " ", "some", " ", "text", ",", " ", "it", " ", "needs", " ", "breaking", " ", "up", "."));
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        assertEquals(wordList, testDocument.getWordsArray());
    }

    @Test
    void testBreakTextIntoArrayAndPutBackTogether() {
        String name = "DocName";
        String text = "Here is some text, it needs breaking up and putting back together! Do it.";
        testDocument = new Document(name, text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        assertEquals(text, testDocument.putWordArrayBackTogether());
    }

    @Test
    void testNoSpellingErrors() {
        String name = "DocName";
        String text = "This sentence has no typos.";
        testDocument = new Document(name, text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocLib.addDocument(testDocument);
        testDocLib.runSpellcheck(testDocument);
        assertEquals(0, testDocument.getNumErrors());
    }

    @Test
    void testOneSpellingError() {
        String name = "DocName";
        String text = "This shirt cost me $40 dollarz.";
        testDocument = new Document(name, text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocLib.addDocument(testDocument);
        testDocLib.runSpellcheck(testDocument);
        assertEquals(1, testDocument.getNumErrors());
    }

    @Test
    void testTwoSpellingErrors() {
        String name = "DocName";
        String text = "Thiss sentence hazz two typos.";
        testDocument = new Document(name, text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocLib.addDocument(testDocument);
        testDocLib.runSpellcheck(testDocument);
        assertEquals(2, testDocument.getNumErrors());
    }

    @Test
    void testFourSpellingErrors() {
        String name = "DocName";
        String text = "Thiss sentenc haz four typoss.";
        testDocument = new Document(name, text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocLib.addDocument(testDocument);
        testDocLib.runSpellcheck(testDocument);
        assertEquals(4, testDocument.getNumErrors());
    }

    @Test
    void testOneWordNoError() {
        String name = "DocName";
        String text = "oats";
        testDocument = new Document(name, text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocLib.addDocument(testDocument);
        assertFalse(testDocument.printErrors());
        testDocLib.runSpellcheck(testDocument);
        assertTrue(testDocument.printErrors());
        assertEquals(0, testDocument.getNumErrors());
        assertEquals(1, testDocument.breakTextIntoWordArray().size());
        assertEquals(text, testDocument.getText());
    }

    @Test
    void testOneWordWithError() {
        String name = "DocName";
        String text = "jkuykuybew";
        testDocument = new Document(name, text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocLib.addDocument(testDocument);
        assertFalse(testDocument.printErrors());
        testDocLib.runSpellcheck(testDocument);
        assertTrue(testDocument.printErrors());
        ListOfSpellingErrors list = testDocument.getListOfErrors();
        assertEquals(1, testDocument.getNumErrors());
        assertTrue(testDocument.getHasErrors());
        assertEquals(1, testDocument.breakTextIntoWordArray().size());
        assertEquals(text, testDocument.getText());
        assertEquals("jkuykuybew", testDocument.getNextError().getTypoText());
        assertTrue(testDocument.getIsSpellchecked());
        assertEquals("DocName", testDocument.getName());
    }

    @Test
    void testDocumentLengthZero() {
        String name = "DocName";
        String text = "";
        testDocument = new Document(name, text);
        assertEquals(0, testDocument.getText().length());
    }

    @Test
    void testReplaceText() {
        String name = "DocName";
        String text = "Some text here.";
        testDocument = new Document(name, text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocLib.addDocument(testDocument);
        assertFalse(testDocument.printErrors());
        testDocLib.runSpellcheck(testDocument);
        assertTrue(testDocument.printErrors());
        assertEquals(0, testDocument.getNumErrors());
        assertEquals(6, testDocument.breakTextIntoWordArray().size());
        assertEquals(text, testDocument.getText());
        text = "Now we get new text!";
        testDocument.replaceText(text);
        assertEquals(0, testDocument.getNumErrors());
        assertEquals(10, testDocument.breakTextIntoWordArray().size());
        assertEquals(text, testDocument.getText());
    }

    @Test
    void testDocumentLibrary() {
        String name = "DocName";
        String text = "Some text.";
        testDocument = new Document(name, text);
        testDocLib.addDocument(testDocument);
        assertEquals(1, testDocLib.numDocuments());
        assertEquals(testDocument, testDocLib.getLastDocument());
    }

    @Test
    void testTypoPositions() {
        String name = "DocName";
        String text = "i haz stuff";
        testDocument = new Document(name, text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocLib.addDocument(testDocument);
        testDocLib.runSpellcheck(testDocument);
        assertTrue(testDocument.hasErrors());
        SpellingError e = testDocument.getNextError();
        assertEquals(2, e.getTypoPositionStart());
        assertEquals(5, e.getTypoPositionEnd());
    }

    @Test
    void longTextWithTypo() {
        String name = "DocName";
        String text = "This is going to be a very long text and a typo will appear heeere. Now that we have made the " +
                "typo, we will continue to write in here.";
        testDocument = new Document(name, text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocLib.addDocument(testDocument);
        testDocLib.runSpellcheck(testDocument);
        testDocument.getListOfErrors().getErrors();
        SpellingError e = testDocument.getNextError();
        e.errorPreviewString();
        assertTrue(testDocument.hasErrors());
    }

    @Test
    void testWordSuggestion() {
        String name = "DocName";
        String text = "numbre";
        testDocument = new Document(name, text);
        testDocument.fixWhitespace();
        testDocument.fixPunctuationWhitespace();
        testDocument.breakTextIntoWordArray();
        testDocLib.addDocument(testDocument);
        testDocLib.runSpellcheck(testDocument);
        assertEquals(1, testDocument.getNumErrors());
        SpellingError e = testDocument.getNextError();
        assertEquals("number", e.getSuggestedWord());
    }

    @Test
    void testDeleteDocument() {
        String name = "DocName";
        String text = "numbre";
        testDocument = new Document(name, text);
        assertEquals(0, testDocLib.numDocuments());
        assertEquals(name, testDocument.toString());
        testDocLib.addDocument(testDocument);
        testDocLib.setDocumentText(0, "Overwriting!");
        assertEquals(1, testDocLib.numDocuments());
        assertTrue(testDocLib.documentExists("DocName"));
        assertFalse(testDocLib.documentExists("qwexd3sad"));
        testDocLib.getDocuments();
        testDocLib.deleteDocument(0);
        testDocLib.deleteDocument(12); // does nothing
        assertEquals(0, testDocLib.numDocuments());
    }

}







