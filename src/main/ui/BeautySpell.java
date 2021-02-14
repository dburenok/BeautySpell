package ui;

import model.Document;
import model.DocumentLibrary;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.*;

// dont need to test this
public class BeautySpell {

    private boolean running = true;
    private boolean trimmed = false;
    private boolean spellchecked = false;
    Scanner sc;
    Document myDoc;

    public BeautySpell() throws IOException {
        runBeautySpell();
    }

    public void runBeautySpell() throws FileNotFoundException {

        println("Welcome to BeautySpell!");

        DocumentLibrary myDocLib = new DocumentLibrary();

        mainLoop(myDocLib);

    }

    public void mainLoop(DocumentLibrary myDocLib) {

        while (running) {
            print("Please enter (or paste) your document: ");

            sc = new Scanner(System.in);
            String txt = sc.nextLine();

            println("Thank you! Saving document...");

            myDoc = new Document(txt);
            myDocLib.addDocument(myDoc);

            println("Document saved.");

            boolean back = false;
            while (!back) {

                Document myDoc = myDocLib.getDocument(0);
                printOptions();

                sc = new Scanner(System.in);
                String choice = sc.nextLine();

                switch (choice) {
                    case "t":
                        choiceTrim(myDoc);
                        break;
                    case "r":
                        choiceRunSpellcheck(myDoc);
                        break;
                    case "s":
                        choiceShowErrors(myDoc);
                        break;
                    case "b":
                        println("Going back...");
                        back = true;
                        break;
                }
            }
            println("Your library has " + myDocLib.numDocuments() + " document(s).");
            println("Add new document [a] or quit [q] ?");
            sc = new Scanner(System.in);
            String choice = sc.nextLine();
            switch (choice) {
                case "a":
                    break;
                case "q":
                    running = false;
                    break;
            }
        }


    }

    public void choiceTrim(Document myDoc) {
        myDoc.fixWhitespace();
        myDoc.fixPunctuationWhitespace();
        trimmed = true;
        println();
        println(">>> Trimmed whitespace! <<<");
    }

    public void choiceRunSpellcheck(Document myDoc) {
        if (!trimmed) {
            println();
            println("Document not ready! Please trim whitespace first.");
            return;
        }
        println();
        print("Running spellcheck...");
//        myDoc.loadDictionary();
        myDoc.breakTextIntoWordArray();
        myDoc.runSpellcheck();
        println();
        println(">>> Ran spellcheck! " + myDoc.numErrors() + " error(s) found. <<<");
    }

    public void choiceShowErrors(Document myDoc) {
        myDoc.showErrors();
    }

    public void printOptions() {
        println();
        println("Choose your option:");
        println("   [t] trim whitespace, [r] - run spellcheck, [s] - show errors, [b] - back");
        println();
    }

    public void print(String s) {
        System.out.print(s);
    }

    public void println(String s) {
        System.out.println(s);
    }

    public void println() {
        System.out.println();
    }

}
