package ui;

import exceptions.DictException;
import model.Document;
import model.DocumentLibrary;
import model.SpellingError;
import persistence.DocReader;
import persistence.DocWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.*;

// dont need to test this
public class BeautySpell {

    private static final String JSON_STORE = "./data/MyDocumentLibrary.json";

    private boolean running = true;
    private boolean trimmed = false;
    DocumentLibrary myDocLib;
    private DocWriter docWriter;
    private DocReader docReader;
    Scanner sc;
    Document myDoc;

    public BeautySpell() throws IOException, DictException {
        startBeautySpell();
    }

    // EFFECTS: begins the program loop
    public void startBeautySpell() throws FileNotFoundException, DictException {
        println("Welcome to BeautySpell!");
        myDocLib = new DocumentLibrary();
        docWriter = new DocWriter(JSON_STORE);
        docReader = new DocReader(JSON_STORE);
        mainLoop();
    }

    // EFFECTS: main program loop, begins after welcome message
    public void mainLoop() {
        while (running) {
            println("Your library has " + myDocLib.numDocuments() + " document(s).");
            println("[l] - load library, [s] - save library, [o] - open a document, [a] - add new document, [q] quit");
            sc = new Scanner(System.in);
            String choice = sc.nextLine();
            boolean back = false;
            switchChoices(choice, back);
        }
    }

    public void switchChoices(String choice, boolean back) {
        switch (choice) {
            case "s": {
                saveDocumentLibrary();
                break;
            }
            case "l": {
                loadDocumentLibrary();
                break;
            }
            case "a":
                addNewDocument(back);
                break;
            case "o":
                openDocument(back);
                break;
            case "q":
                running = false;
                break;
        }
    }

    //
    public void openDocument(boolean back) {
        if (myDocLib.numDocuments() == 0) {
            return;
        }
        boolean open = false;
        while (!open) {
            println("Your library has " + myDocLib.numDocuments() + " document(s). Which one do you want to open?");
//            System.out.println(myDocLib.listDocumentNames());
            sc = new Scanner(System.in);
            String docNameEntered = sc.nextLine();
            if (myDocLib.documentExists(docNameEntered)) {
//                myDoc = myDocLib.getDocumentByName(docNameEntered);
                open = true;
                insideDocumentLoop(back, myDoc);
            } else {
                System.out.println("Document doesn't exist!");
            }
        }
    }


    //
    public void addNewDocument(boolean back) {
        print("Please name your document: ");
        sc = new Scanner(System.in);
        String name = sc.nextLine();

        print("Please enter (or paste) the text of your document: ");
        sc = new Scanner(System.in);
        String text = sc.nextLine();

        println("Saving document...");

        myDoc = new Document(name, text);
        myDocLib.addDocument(myDoc);

        println("Document saved.");

        Document myDoc = myDocLib.getLastDocument();
        insideDocumentLoop(back, myDoc);
    }

    // EFFECTS: saves the workroom to file
    private void saveDocumentLibrary() {
        try {
            docWriter.open();
            docWriter.write(myDocLib);
            docWriter.close();
            System.out.println("Saved document library to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadDocumentLibrary() {
        try {
            myDocLib = docReader.read();
            System.out.println("Loaded Document Library from " + JSON_STORE);
            mainLoop();
        } catch (IOException | DictException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
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
                    printDocument();
                    break;
            }
        }
    }

    public  void printDocument() {
        println();
        println(">>> START OF DOCUMENT <<<");
        println();
        println(myDoc.getName() + "\n" + myDoc.getText());
        println();
        println(">>> END OF DOCUMENT <<<");
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
        myDocLib.runSpellcheck(myDoc);
        println();
        println(">>> Ran spellcheck! " + myDoc.getNumErrors() + " error(s) found. <<<");
    }

    // REQUIRES: myDoc.text not empty
    // MODIFIES: myDoc
    // EFFECTS: User selected: s - show errors
    public void choiceShowErrors(Document myDoc) {
        if (myDoc.getNumErrors() > 0) {
            println("\nDocument has " + myDoc.getNumErrors() + " errors.");
            while (myDoc.getNumErrors() > 0) {
                correctSpelling();
                println("\nSpelling fixed!\nDocument now has " + myDoc.getNumErrors() + " errors.");
            }
        } else {
            println("Document has no errors!");
        }
    }

    public void correctSpelling() {
        SpellingError error = myDoc.getNextError();
        error.errorPreviewString();
        String suggestedWord = error.getSuggestedWord();
        if (!suggestedWord.equals("")) {
            System.out.println("Suggested word: " + error.getSuggestedWord());
            print("\nPlease provide the correct spelling (or press enter to use suggestion): ");
        } else {
            print("\nPlease provide the correct spelling: ");
        }

        sc = new Scanner(System.in);
        String correctSpelling;
        String entry = sc.nextLine();
//        BeautySpellGUI.finalizeCorrectSpelling(myDoc, error, entry);
    }

    // EFFECTS: prints user selection choices to console
    public void printOptions() {
        println();
        println("[t] trim whitespace, [r] - run spellcheck, [s] - show errors, [p] - print document, [b] - back");
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
