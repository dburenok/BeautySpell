package persistence;

import model.Document;
import model.DocumentLibrary;
import org.junit.jupiter.api.Test;
import persistence.DocReader;
import persistence.DocWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DocWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            DocumentLibrary dl = new DocumentLibrary();
            DocWriter writer = new DocWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDocumentLibrary() {
        try {
            DocumentLibrary dl = new DocumentLibrary();
            DocWriter writer = new DocWriter("./data/testWriterEmptyDocumentLibrary.json");
            writer.open();
            writer.write(dl);
            writer.close();

            DocReader reader = new DocReader("./data/testWriterEmptyDocumentLibrary.json");
            dl = reader.read();
            assertEquals(0, dl.numDocuments());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDocumentLibrary() {
        try {
            DocumentLibrary dl = new DocumentLibrary();
            dl.addDocument(new Document("Test Document", "Here is some text."));
            dl.addDocument(new Document("Test Document", "Here is some more cool text."));
            DocWriter writer = new DocWriter("./data/testWriterGeneralDocumentLibrary.json");
            writer.open();
            writer.write(dl);
            writer.close();

            DocReader reader = new DocReader("./data/testWriterGeneralDocumentLibrary.json");
            dl = reader.read();
            assertEquals(2, dl.numDocuments());
            assertEquals("Here is some text.", dl.getDocument(0).getText());
            assertEquals("Here is some more cool text.", dl.getDocument(1).getText());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
