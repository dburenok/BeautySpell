package ui;

import model.Document;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.*;

// dont need to test this
public class BeautySpell {

    private boolean running = true;
    Scanner sc;

    public BeautySpell() throws IOException {
        runBeautySpell();
    }

    public void runBeautySpell() throws FileNotFoundException {

        println("Welcome to BeautySpell!");
        print("Please enter (or paste) your text: ");

        sc = new Scanner(System.in);
        String txt = sc.nextLine();

        println("Thank you! Saving text...");

        Document myDoc = new Document(txt);

        println("Document saved.");

        println(myDoc.getText());

        mainLoop(myDoc);

    }

    public void mainLoop(Document myDoc) throws FileNotFoundException {
        boolean back = false;
        while (!back) {

            printOptions();

            sc = new Scanner(System.in);
            String choice = sc.nextLine();

            switch (choice) {
                case "f":
                    choiceF(myDoc);
                    break;
                case "s":
                    choiceS(myDoc);
                    break;
                case "e":
                    choiceE(myDoc);
                    break;
                case "b":
                    println("Going back...");
                    back = true;
                    break;
            }
        }
    }

    public void choiceF(Document myDoc) {
        myDoc.fixWhitespace();
        myDoc.fixPunctuationWhitespace();
        println("Fixed whitespace!");
    }

    public void choiceS(Document myDoc) throws FileNotFoundException {
        myDoc.loadDictionary();
        myDoc.breakTextIntoWordArray();
        myDoc.runSpellcheck();
        println("Ran spellcheck!");
    }

    public void choiceE(Document myDoc) {
        myDoc.showErrors();
    }

    public void printOptions() {
        println("Choose your option:");
        println("f => Trim whitespace");
        println("s => Run spellcheck");
        println("e => Show errors");
        println("b => Back");
    }

    public void print(String s) {
        System.out.print(s);
    }

    public void println(String s) {
        System.out.println(s);
    }

}
