package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

// Library of document objects, also holds the dictionary
public class DocumentLibrary {

    ArrayList<Document> docs;
    HashSet<String> wordDictionary;

    // REQUIRES: dictionary file must be available in the proper location
    public DocumentLibrary() throws FileNotFoundException {
        docs = new ArrayList<>();
        loadDictionary();
    }

    // REQUIRES: wordDictionary must be loaded
    // MODIFIES: this, d
    // EFFECTS: Adds given document to document array
    public void addDocument(Document d) {
        d.addDictionary(wordDictionary);
        docs.add(d);
    }

    // EFFECTS: returns number of documents in document library
    public int numDocuments() {
        return docs.size();
    }

    // REQUIRES: index must be non-negative integer and less than docs.size()
    // EFFECTS: returns specific document from library
    public Document getDocument(int index) {
        return docs.get(index);
    }

    // REQUIRES: docs not empty
    // EFFECTS: Returns the last document in library
    public Document getLastDocument() {
        return docs.get(docs.size() - 1);
    }

    // REQUIRES: dictionary.txt file must be present at [project_dir]/data/dictionary.txt
    // MODIFIES: this.wordDictionary
    // EFFECTS: loads dictionary into a HashSet
    public void loadDictionary() throws FileNotFoundException {
        wordDictionary = new HashSet<>();
        String dictPath = new File("").getAbsolutePath().concat("/data/dictionary.txt");
        File file = new File(dictPath);
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            wordDictionary.add(scan.nextLine().toLowerCase());
        }
    }

    // REQUIRES: this.text.length() > 0
    // MODIFIES: this
    // EFFECTS: for every array word not in dictionary, new SpellingError object is added to ListOfSpellingErrors
    public void runSpellcheck(Document myDoc) {
        myDoc.setListOfErrors(new ListOfSpellingErrors());
        int position = 0;
        for (String w: myDoc.getWordsArray()) {
            position += w.length();
            if (myDoc.isWord(w)) {
                if (!wordDictionary.contains(w.toLowerCase())) {
                    myDoc.setHasErrors(true);
                    SpellingError error = new SpellingError(position - w.length(), position, w, "NON");
                    myDoc.addError(error);
                }
            }
        }
        myDoc.setIsSpellchecked(true);
        if (myDoc.getNumErrors() == 0) {
            myDoc.setHasErrors(false);
        }
    }

    // EFFECTS: converts self to json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "DocumentLibrary");
        json.put("documents", documentsToJson());
        return json;
    }

    // EFFECTS: returns documents in this DocumentLibrary as a JSON array
    private JSONArray documentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Document d : docs) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }

}
