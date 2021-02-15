package model;

// Library of documents

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class DocumentLibrary {

    ArrayList<Document> docs;
    HashSet<String> wordDictionary;

    public DocumentLibrary() throws FileNotFoundException {
        docs = new ArrayList<>();
        loadDictionary();
    }

    public void addDocument(Document d) {
        d.addDictionary(wordDictionary);
        docs.add(d);
    }

    public int numDocuments() {
        return docs.size();
    }

//    public Document getDocument(int index) {
//        if (index + 1 <= docs.size()) {
//            return docs.get(index);
//        } else {
//            return null;
//        }
//    }

    public Document getLastDocument() {
        return docs.get(docs.size() - 1);
    }

    // REQUIRES: dictionary.txt file must be present at [project_dir]/data/dictionary.txt
    // MODIFIES: this.wordDictionary
    // EFFECTS: loads dictionary into a HashSet
    // TODO: move this method into DocumentLibrary as it's redundant to load a new dict for each document
    public void loadDictionary() throws FileNotFoundException {
        wordDictionary = new HashSet<>();
        String dictPath = new File("").getAbsolutePath().concat("/data/dictionary.txt");
        File file = new File(dictPath);
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            wordDictionary.add(scan.nextLine().toLowerCase());
        }
    }

}
