package ui;

import model.Document;
import model.DocumentLibrary;
import persistence.DocReader;
import persistence.DocWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class BeautySpellGUI implements ActionListener {

    private JFrame frame;
    private JFileChooser openFileChooser;

    private JLabel logoLabel;
    private JButton loadLibraryButton;
    private JButton saveLibraryButton;
    private JButton openDocumentButton;
    private JButton saveDocumentButton;
    private JButton newDocumentButton;
    private JButton trimWhitespaceButton;
    private JButton runSpellcheckButton;
    private JButton showErrorsButton;
    private JButton quitButton;

    private DocumentLibrary documentLibrary;
    private JScrollPane documentListScrollPane;
    private JScrollPane textEditorScrollPane;

    private DefaultListModel<Document> model;

    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 800;
    private static final int LEFT_SIDEBAR_WIDTH = 155;

    private static final String JSON_STORE = "./data/MyDocumentLibrary.json";
    private DocWriter docWriter;
    private DocReader docReader;

    public BeautySpellGUI() throws FileNotFoundException {
        initFrame();
//        initTestDocumentLibrary();
        initComponents();
        addComponentsToFrame();
        initFileChooser();
        showGUI();
    }

    // EFFECTS: initialize GUI
    public void initFrame() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
    }

    public void initTestDocumentLibrary() throws FileNotFoundException {
        documentLibrary = new DocumentLibrary();
//        docWriter = new DocWriter(JSON_STORE);
//        docReader = new DocReader(JSON_STORE);
        loadDocumentLibrary();
    }

    // EFFECTS: loads workroom from file
    private void loadDocumentLibrary() {
        try {
            documentLibrary = docReader.read();
            updateDocumentList();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: initialize components
    public void initComponents() {
        initSidebar();
        initTextEditor();
    }

    // EFFECTS: initialize sidebar
    public void initSidebar() {
        initButtons();
        model = new DefaultListModel<>();
        JList<Document> documentList = new JList<>();
        documentList.setModel(model);

//        LinkedList<Document> docList = documentLibrary.getDocuments();
//        for (Document d : docList) {
//            model.addElement(d);
//        }

        documentListScrollPane = new JScrollPane(documentList);
        documentListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        documentListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        documentListScrollPane.setPreferredSize(new Dimension(LEFT_SIDEBAR_WIDTH, 200));
    }

    public void updateDocumentList() {
        LinkedList<Document> docList = documentLibrary.getDocuments();
        for (Document d : docList) {
            model.addElement(d);
        }
    }

    // EFFECTS: initialize buttons
    public void initButtons() {
        logoLabel = new JLabel("BeautySpell");

        loadLibraryButton = new JButton("Load Library");
        loadLibraryButton.addActionListener(this);

        saveLibraryButton = new JButton("Save Library");
        newDocumentButton = new JButton("New Document");
        openDocumentButton = new JButton("Open Document");
        saveDocumentButton = new JButton("Save Document");
        trimWhitespaceButton = new JButton("Trim Whitespace");
        runSpellcheckButton = new JButton("Run Spellcheck");
        showErrorsButton = new JButton("Show Errors");

        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
    }

    // EFFECTS: initialize text editor component
    public void initTextEditor() {
//        JTextArea documentTextArea = new JTextArea(documentLibrary.getDocument(0).getText());
        JTextArea documentTextArea = new JTextArea("");
        documentTextArea.setLineWrap(true);
        documentTextArea.setWrapStyleWord(true);
        documentTextArea.setEditable(true);
        textEditorScrollPane = new JScrollPane(documentTextArea);
        textEditorScrollPane.setPreferredSize(new Dimension(WINDOW_WIDTH - LEFT_SIDEBAR_WIDTH, WINDOW_HEIGHT));
        textEditorScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    public void initFileChooser() {
        openFileChooser = new JFileChooser();
        openFileChooser.setFileFilter(new FileNameExtensionFilter("JSON flles", "json"));
    }

    // EFFECTS: ads GUI components into panelHolder
    public void addComponentsToFrame() {
        JPanel buttonPanel = new JPanel();
        JPanel editorPanel = new JPanel();

        buttonPanel.add(logoLabel);
        buttonPanel.add(loadLibraryButton);
        buttonPanel.add(saveLibraryButton);
        buttonPanel.add(documentListScrollPane);
        buttonPanel.add(newDocumentButton);
        buttonPanel.add(openDocumentButton);
        buttonPanel.add(saveDocumentButton);
        buttonPanel.add(new JLabel("  ")); // spacer
        buttonPanel.add(trimWhitespaceButton);
        buttonPanel.add(runSpellcheckButton);
        buttonPanel.add(showErrorsButton);
        buttonPanel.add(quitButton);

        editorPanel.add(textEditorScrollPane);

        buttonPanel.setPreferredSize(new Dimension(LEFT_SIDEBAR_WIDTH, WINDOW_HEIGHT));

        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(editorPanel, BorderLayout.CENTER);

    }

    // EFFECTS: finalize GUI setup and show GUI
    public void showGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("BeautySpell");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new BeautySpellGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary file not found!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadLibraryButton) {
            int returnValue = openFileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String path = openFileChooser.getSelectedFile().getAbsolutePath();
                docReader = new DocReader(path);
                loadDocumentLibrary();
//                try {
//
//                } catch (IOException ioe) {
////                    System.out.println("whoops!");
//                }
            } else {
                return;
            }
        } else if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }
}







