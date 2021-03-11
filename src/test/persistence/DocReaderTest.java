package persistence;

import model.DocumentLibrary;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import persistence.DocReader;


public class DocReaderTest {

    @Test
    void testReaderNonExistentFile() {
        DocReader reader = new DocReader("./data/noSuchFile.json");
        try {
            DocumentLibrary dl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDocumentLibrary() {
        DocReader reader = new DocReader("./data/test/testReaderEmptyDocumentLibrary.json");
        try {
            DocumentLibrary dl = reader.read();
            assertEquals(0, dl.numDocuments());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDocumentLibrary() {
        DocReader reader = new DocReader("./data/test/testReaderGeneralDocumentLibrary.json");
        try {
            DocumentLibrary dl = reader.read();
            assertEquals(2, dl.numDocuments());
            assertEquals("This is some text,", dl.getDocument(0).getText());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
