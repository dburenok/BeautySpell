package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Library of document objects, also holds the dictionary
public class DocumentLibrary {

    private LinkedList<Document> docs;
    protected TreeSet<String> dictionary;
    private PredictiveSpellchecker checker;
    private String name;

    // REQUIRES: dictionary file must be available in the proper location
    public DocumentLibrary() throws FileNotFoundException {
        docs = new LinkedList<>();
        loadDictionary();
        checker = new PredictiveSpellchecker(dictionary);
    }

    // REQUIRES: wordDictionary must be loaded
    // MODIFIES: this, d
    // EFFECTS: Adds given document to document array
    public void addDocument(Document d) {
        if (!this.docs.contains(d)) {
            this.docs.add(d);
            d.setDocumentLibrary(this);
        }
    }

    // EFFECTS: returns number of documents in document library
    public int numDocuments() {
        return docs.size();
    }

    public TreeSet<String> getDictionary() {
        return dictionary;
    }

    public Document getDocument(int index) {
        return docs.get(index);
    }

    public LinkedList<Document> getDocuments() {
        return docs;
    }

    // REQUIRES: docs not empty
    public Document getLastDocument() {
        return docs.get(docs.size() - 1);
    }

    // EFFECTS: return the first document that matches given name
    public Document getDocumentByName(String name) {
        for (Document d : docs) {
            if (d.getName().equals(name)) {
                return d;
            }
        }
        return null;
    }

    // EFFECTS: return true if document with given name exists
    public boolean documentExists(String name) {
        for (Document d : docs) {
            if (d.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: index >= 0
    // EFFECTS: manually set a document's text
    public void setDocumentText(int index, String text) {
        docs.get(index).replaceText(text);
    }

    public void deleteDocument(int index) {
        if (index <= docs.size() - 1) {
            docs.remove(index);
        }
    }

    // REQUIRES: dictionary.txt file must be present at [project_dir]/data/dictionary.txt
    // MODIFIES: this.wordDictionary
    // EFFECTS: loads dictionary into a HashSet
    public void loadDictionary() throws FileNotFoundException {
        dictionary = new TreeSet<>();
        String dictPath = new File("").getAbsolutePath().concat("/data/dictionary.txt");
        File file = new File(dictPath);
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            dictionary.add(scan.nextLine().toLowerCase());
        }
    }

    // REQUIRES: this.text.length() > 0
    // MODIFIES: this
    // EFFECTS: for every array word not in dictionary, new SpellingError object is added to ListOfSpellingErrors
    public void checkSpelling(Document myDoc) {
        myDoc.setListOfErrors(new ListOfSpellingErrors());

        int position = 0;

        for (String word : myDoc.getWordsArray()) {
            position += word.length();
            if (myDoc.isWord(word)) {
                if (!dictionary.contains(word.toLowerCase())) {
                    myDoc.setHasErrors(true);
                    String suggestedWord = "";
                    if (word.length() < 10) {
                        suggestedWord = checker.getStrictSuggestion(word.toLowerCase());
                    }
                    SpellingError error = new SpellingError(position - word.length(), position, word, suggestedWord);
                    myDoc.addError(error);
                }
            }
        }

        myDoc.setIsSpellchecked(true);

        if (myDoc.getNumErrors() == 0) {
            myDoc.setHasErrors(false);
        }
    }

    public ArrayList<String> listDocumentNames() {
        ArrayList<String> docNames = new ArrayList<>();
        for (Document d : docs) {
            docNames.add(d.getName());
        }
        return docNames;
    }

    // EFFECTS: converts self to json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "Document Library");
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
