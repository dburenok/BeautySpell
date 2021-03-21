package persistence;

import model.Document;
import model.DocumentLibrary;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Represents a reader that reads workroom from JSON data stored in file
// Code taken partially from https://github.com/stleary/JSON-java
public class DocReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public DocReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public DocumentLibrary read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDocumentLibrary(jsonObject);
    }

    public String getSource() {
        return this.source;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private DocumentLibrary parseDocumentLibrary(JSONObject jsonObject) throws FileNotFoundException {
        DocumentLibrary dl = new DocumentLibrary();
        addDocuments(dl, jsonObject);
        return dl;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addDocuments(DocumentLibrary dl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("documents");
        for (Object json : jsonArray) {
            JSONObject nextDocument = (JSONObject) json;
            addDocument(dl, nextDocument);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addDocument(DocumentLibrary dl, JSONObject jsonObject) {
        Document doc = new Document(jsonObject.getString("name"), jsonObject.getString("text"));
        dl.addDocument(doc);
    }
}
