package ui;

import model.Document;
import sun.awt.HKSCS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

// dont need to test this
public class BeautySpell {

    private boolean running = true;

    public BeautySpell() throws IOException {
        runBeautySpell();
    }

    public void runBeautySpell() throws FileNotFoundException {
        String text = "Here is some text, it needs some spell checking. The questin is, is there a typo?";
        Document d = new Document(text);
        d.spellcheck();
    }

}
