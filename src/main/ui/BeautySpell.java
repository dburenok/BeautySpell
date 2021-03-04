package ui;

import model.Document;
import model.DocumentLibrary;
import model.SpellingError;

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

    // EFFECTS: begins the program loop
    public void runBeautySpell() throws FileNotFoundException {
        println("Welcome to BeautySpell!");
        DocumentLibrary myDocLib = new DocumentLibrary();
        mainLoop(myDocLib);
    }

    // EFFECTS: main program loop, begins after welcome message
    public void mainLoop(DocumentLibrary myDocLib) {

        while (running) {

            println("Your library has " + myDocLib.numDocuments() + " document(s).");
            println("[o] - open a document, [a] - add new document, [q] quit");
            sc = new Scanner(System.in);
            String choice = sc.nextLine();
            boolean back = false;
            switch (choice) {
                case "a":
                    print("Please enter (or paste) your document: ");

                    sc = new Scanner(System.in);
                    String txt = sc.nextLine();

                    println("Saving document...");

                    myDoc = new Document(txt);
                    myDocLib.addDocument(myDoc);

                    println("Document saved.");

                    Document myDoc = myDocLib.getLastDocument();
                    insideDocumentLoop(back, myDoc);
                    break;
                case "o":
                    if (myDocLib.numDocuments() == 0) {
                        //println("You have no documents in your library.");
                        //mainLoop(myDocLib);
                        break;
                    }
                    println("You have " + myDocLib.numDocuments() + " in your library. Which one do you want to open?");
                    sc = new Scanner(System.in);
                    int docNum = Integer.parseInt(sc.nextLine());
                    myDoc = myDocLib.getDocument(docNum);
                    insideDocumentLoop(back, myDoc);
                    break;
                case "q":
                    running = false;
                    break;
            }
        }
    }

    // EFFECTS: program loop when working on a document
    public void insideDocumentLoop(Boolean back, Document myDoc) {
        while (!back) {

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
                case "p":
                    println(myDoc.getText());
                    break;
            }
        }
    }

    // REQUIRES: myDoc.text not empty
    // MODIFIES: myDoc
    // EFFECTS: User selected: t - trim whitespace
    public void choiceTrim(Document myDoc) {
        myDoc.fixWhitespace();
        myDoc.fixPunctuationWhitespace();
        trimmed = true;
        println();
        println(">>> Trimmed whitespace! <<<");
    }

    // REQUIRES: myDoc.text not empty
    // MODIFIES: myDoc
    // EFFECTS: User selected: r - run spellcheck
    public void choiceRunSpellcheck(Document myDoc) {
        if (!trimmed) {
            println();
            println("Document not ready! Please trim whitespace first.");
            return;
        }
        println();
        print("Running spellcheck...");
        myDoc.breakTextIntoWordArray();
        myDoc.runSpellcheck();
        println();
        println(">>> Ran spellcheck! " + myDoc.numErrors() + " error(s) found. <<<");
    }

    // REQUIRES: myDoc.text not empty
    // MODIFIES: myDoc
    // EFFECTS: User selected: s - show errors
    public void choiceShowErrors(Document myDoc) {
        if (myDoc.numErrors() > 0) {
            println("Document has " + myDoc.numErrors() + " errors.");
            while (myDoc.numErrors() > 0) {

                SpellingError e = myDoc.getNextError();
                e.showError(myDoc.getText());

                print("Please provide the correct spelling: ");
                sc = new Scanner(System.in);
                String correctSpelling = sc.nextLine();

                String oldText = myDoc.getText();
                String newText = oldText.substring(0, e.typoPositionStart())
                        + correctSpelling + oldText.substring(e.typoPositionEnd());
                myDoc.replaceText(newText);

                println("Spelling fixed!");
                println("Document now has " + myDoc.numErrors() + " errors.");
            }
        } else {
            println("Document has no errors!");
        }
    }

    // EFFECTS: prints user selection choices to console
    public void printOptions() {
        println();
        println("[t] trim whitespace, [r] - run spellcheck, [s] - show errors, [p] - print document, [b] - back");
        println();
    }

    // EFFECTS: helper method that prints given string to console, no newline
    public void print(String s) {
        System.out.print(s);
    }

    // EFFECTS: helper method that prints given string to console, with newline
    public void println(String s) {
        System.out.println(s);
    }

    // EFFECTS: helper method that prints a blank newline to console
    public void println() {
        System.out.println();
    }

}
