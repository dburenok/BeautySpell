package ui;

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
    private boolean spellchecked = false;
    private DocWriter docWriter;
    private DocReader docReader;
    Scanner sc;
    Document myDoc;


    public BeautySpell() throws IOException {
        runBeautySpell();
    }

    // EFFECTS: begins the program loop
    public void runBeautySpell() throws FileNotFoundException {
        println("Welcome to BeautySpell!");
        myDocLib = new DocumentLibrary();
        docWriter = new DocWriter(JSON_STORE);
        docReader = new DocReader(JSON_STORE);
        mainLoop(myDocLib);
    }

    // EFFECTS: main program loop, begins after welcome message
    public void mainLoop(DocumentLibrary myDocLib) {
        while (running) {

            println("Your library has " + myDocLib.numDocuments() + " document(s).");
            println("[l] - load library, [s] - save library, [o] - open a document, [a] - add new document, [q] quit");
            sc = new Scanner(System.in);
            String choice = sc.nextLine();
            boolean back = false;
            switchChoices(choice, myDocLib, back);
        }
    }

    public void switchChoices(String choice, DocumentLibrary dl, boolean back) {
        switch (choice) {
            case "s": {
                saveDocumentLibrary(dl);
                break;
            }
            case "l": {
                loadDocumentLibrary();
                break;
            }
            case "a":
                addNewDocument(dl, back);
                break;
            case "o":
                openDocument(dl, back);
                break;
            case "q":
                running = false;
                break;
        }
    }

    //
    public void openDocument(DocumentLibrary dl, boolean back) {
        if (dl.numDocuments() == 0) {
            return;
        }
        boolean open = false;
        while (!open) {
            println("You have " + dl.numDocuments() + " in your library. Which one do you want to open?");
            sc = new Scanner(System.in);
            int docNum = Integer.parseInt(sc.nextLine());
            if (docNum > myDocLib.numDocuments()) {
                System.out.println("Document doesn't exist!");
            } else {
                myDoc = dl.getDocument(docNum);
                open = true;
                insideDocumentLoop(back, myDoc);
            }
        }
    }


    //
    public void addNewDocument(DocumentLibrary dl, boolean back) {
        print("Please name your document: ");
        sc = new Scanner(System.in);
        String docName = sc.nextLine();

        print("Please enter (or paste) the text of your document: ");
        sc = new Scanner(System.in);
        String txt = sc.nextLine();

        println("Saving document...");

        myDoc = new Document(txt, docName);
        dl.addDocument(myDoc);

        println("Document saved.");

        Document myDoc = dl.getLastDocument();
        insideDocumentLoop(back, myDoc);
    }

    // EFFECTS: saves the workroom to file
    private void saveDocumentLibrary(DocumentLibrary dl) {
        try {
            docWriter.open();
            docWriter.write(dl);
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
            mainLoop(myDocLib);
        } catch (IOException e) {
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
            println();
            println("Document has " + myDoc.getNumErrors() + " errors.");
            while (myDoc.getNumErrors() > 0) {

                SpellingError e = myDoc.getNextError();
                e.showError(myDoc.getText());

                print("Please provide the correct spelling: ");
                sc = new Scanner(System.in);
                String correctSpelling = sc.nextLine();

                String oldText = myDoc.getText();
                String newText = oldText.substring(0, e.typoPositionStart())
                        + correctSpelling + oldText.substring(e.typoPositionEnd());
                myDoc.replaceText(newText, myDocLib);

                println("Spelling fixed!");
                println("Document now has " + myDoc.getNumErrors() + " errors.");
            }
        } else {
            println("Document has no errors!");
        }
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
