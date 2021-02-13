package ui;

import model.Document;

import java.io.FileNotFoundException;
import java.io.IOException;

// dont need to test this
public class BeautySpell {

    private boolean running = true;

    public BeautySpell() throws IOException {
        runBeautySpell();
    }

    public void runBeautySpell() throws FileNotFoundException {
        String text = "Here is some toxt, it needs some spell checking. The questin is, is there a typo?";
        Document myDoc = new Document(text);
        myDoc.fixWhitespace();
        myDoc.fixPunctuationWhitespace();
        myDoc.breakTextIntoWordArray();
        myDoc.loadDictionary();
        myDoc.spellcheck();
        myDoc.showErrors();
    }

}
