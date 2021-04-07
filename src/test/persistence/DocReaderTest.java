package persistence;

import exceptions.DictException;
import model.DocumentLibrary;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class DocReaderTest {

    @Test
    void testReaderNonExistentFile() {
        DocReader reader = new DocReader("./data/noSuchFile.json");
        try {
            DocumentLibrary dl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (DictException e) {
            fail();
        }
    }

    @Test
    void testReaderEmptyDocumentLibrary() {
        DocReader reader = new DocReader("./data/test/testReaderEmptyDocumentLibrary.json");
        try {
            DocumentLibrary dl = reader.read();
            assertEquals(0, dl.numDocuments());
            System.out.println(reader.getSource());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (DictException e) {
            fail();
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
        } catch (DictException e) {
            fail();
        }
    }

}
