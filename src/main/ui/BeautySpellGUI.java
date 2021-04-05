package ui;

import model.Document;
import model.DocumentLibrary;
import model.SpellingError;
import persistence.DocReader;
import persistence.DocWriter;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.LinkedList;

import javax.swing.ImageIcon;

public class BeautySpellGUI implements ActionListener {

    private JFrame frame;
    private JFileChooser openFileChooser;

    private JLabel logoLabel;
    private JButton loadLibraryButton;
    private JButton saveLibraryButton;
    private JButton newDocumentButton;
    private JButton openDocumentButton;
    private JButton saveDocumentButton;
    private JButton deleteDocumentButton;
    private JButton trimWhitespaceButton;
    private JButton runSpellcheckButton;
    private JButton showErrorsButton;
    private JButton quitButton;

    private DocumentLibrary documentLibrary;
    private JScrollPane documentListScrollPane;
    private JTextArea documentTextArea;
    private JScrollPane textEditorScrollPane;

    private DefaultListModel<Document> model;
    private JList<Document> documentList;

    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 800;
    private static final int LEFT_SIDEBAR_WIDTH = 155;

    //    private static final String JSON_STORE = "./data/MyDocumentLibrary.json";
    private DocWriter docWriter;
    private DocReader docReader;

    private final ImageIcon logo = new ImageIcon("./data/beautyspell_logo.png");

    public BeautySpellGUI() {
        initFrame();
        initComponents();
        addComponentsToFrame();
        initFileChooser();
        initDocumentLibrary();
        showGUI();
    }

    // EFFECTS: initialize frame
    public void initFrame() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
    }

    // EFFECTS: loads document library from file
    private void loadDocumentLibrary() {
        try {
            documentLibrary = docReader.read();
            updateDocumentList();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + docReader.getSource());
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveDocumentLibrary(String path) {
        try {
            docWriter = new DocWriter(path);
            docWriter.open();
            docWriter.write(documentLibrary);
            docWriter.close();
            System.out.println("Saved document library to " + path);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + path);
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
        documentList = new JList<>();
        documentList.setModel(model);
        documentList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        documentList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int index = documentList.getSelectedIndex();
                    if (index >= 0) {
                        documentTextArea.setText(documentLibrary.getDocument(index).getText());
                    }
                }
            }
        });

        documentListScrollPane = new JScrollPane(documentList);
        documentListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        documentListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        documentListScrollPane.setPreferredSize(new Dimension(LEFT_SIDEBAR_WIDTH, 180));
    }

    public void updateDocumentList() {
        model.removeAllElements();
        LinkedList<Document> docList = documentLibrary.getDocuments();
        if (docList.size() > 0) {
            for (Document d : docList) {
                model.addElement(d);
            }
        }
    }

    // EFFECTS: initialize buttons
    public void initButtons() {
        logoLabel = new JLabel(logo);

        loadLibraryButton = new JButton("Load Library");
        loadLibraryButton.addActionListener(this);

        saveLibraryButton = new JButton("Save Library");
        saveLibraryButton.addActionListener(this);

        newDocumentButton = new JButton("New Document");
        newDocumentButton.addActionListener(this);

        openDocumentButton = new JButton("Open Document");
        openDocumentButton.addActionListener(this);

        saveDocumentButton = new JButton("Save Document");
        saveDocumentButton.addActionListener(this);

        deleteDocumentButton = new JButton("Delete Document");
        deleteDocumentButton.addActionListener(this);

        trimWhitespaceButton = new JButton("Trim Whitespace");
        trimWhitespaceButton.addActionListener(this);

        runSpellcheckButton = new JButton("Run Spellcheck");
        runSpellcheckButton.addActionListener(this);

        showErrorsButton = new JButton("Show Errors");
        showErrorsButton.addActionListener(this);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
    }

    // EFFECTS: initialize text editor component
    public void initTextEditor() {
        documentTextArea = new JTextArea("");
        documentTextArea.setLineWrap(true);
        documentTextArea.setWrapStyleWord(true);
        documentTextArea.setEditable(true);
        textEditorScrollPane = new JScrollPane(documentTextArea);
        textEditorScrollPane.setPreferredSize(new Dimension(WINDOW_WIDTH - LEFT_SIDEBAR_WIDTH, WINDOW_HEIGHT - 35));
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
        buttonPanel.add(deleteDocumentButton);
        buttonPanel.add(trimWhitespaceButton);
        buttonPanel.add(runSpellcheckButton);
        buttonPanel.add(showErrorsButton);
        buttonPanel.add(quitButton);

        editorPanel.add(textEditorScrollPane);

        buttonPanel.setPreferredSize(new Dimension(LEFT_SIDEBAR_WIDTH, WINDOW_HEIGHT));

        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(editorPanel, BorderLayout.CENTER);

    }

    public void initDocumentLibrary() {
        try {
            this.documentLibrary = new DocumentLibrary();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't initialize document library!");
        }
    }

    // EFFECTS: finalize GUI setup and show GUI
    public void showGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("BeautySpell");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void showMessage(String title, String text) {
        JOptionPane.showMessageDialog(frame, text, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: handle button clicks and perform appropriate actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadLibraryButton) {
            loadLibraryButtonClicked();
        } else if (e.getSource() == saveLibraryButton) {
            saveLibraryButtonClicked();
        } else if (e.getSource() == newDocumentButton) {
            newDocumentButtonClicked();
        } else if (e.getSource() == openDocumentButton) {
            openDocumentButtonClicked();
        } else if (e.getSource() == saveDocumentButton) {
            saveDocumentButtonClicked();
        } else if (e.getSource() == deleteDocumentButton) {
            deleteDocumentButtonClicked();
        } else if (e.getSource() == trimWhitespaceButton) {
            trimWhitespaceButtonClicked();
        } else if (e.getSource() == runSpellcheckButton) {
            runSpellcheckButtonClicked();
        } else if (e.getSource() == showErrorsButton) {
            showErrorsButtonClicked();
        } else if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }

    public void loadLibraryButtonClicked() {
        int returnValue = openFileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String path = openFileChooser.getSelectedFile().getAbsolutePath();
            docReader = new DocReader(path);
            loadDocumentLibrary();
        }
    }

    public void saveLibraryButtonClicked() {
        String path = new File("").getAbsolutePath().concat("/data/MyDocumentLibrary.json");
        saveDocumentLibrary(path);
        playSuccessSound();
        showMessage("Saved", "Saved library to " + path);
    }

    public void newDocumentButtonClicked() {
        String name = JOptionPane.showInputDialog(frame,
                "Document name:", "New Document", JOptionPane.PLAIN_MESSAGE);
        if (name.length() > 0) {
            documentLibrary.addDocument(new Document(name, documentTextArea.getText()));
            updateDocumentList();
        }
    }

    public void openDocumentButtonClicked() {
        int index = documentList.getSelectedIndex();
        if (index >= 0) {
            documentTextArea.setText(documentLibrary.getDocument(index).getText());
        } else {
            playFailSound();
            showMessage("Error", "No document selected!");
        }
    }

    public void saveDocumentButtonClicked() {
        int index = documentList.getSelectedIndex();
        if (index >= 0) {
            documentLibrary.setDocumentText(index, documentTextArea.getText());
            playSuccessSound();
            showMessage("Saved", documentLibrary.getDocument(index).getName() + " saved!");
        } else {
            playFailSound();
            showMessage("Error", "No document selected!");
        }
    }

    public void deleteDocumentButtonClicked() {
        int index = documentList.getSelectedIndex();
        if (index >= 0) {
            String name = documentLibrary.getDocument(index).getName();
            documentLibrary.deleteDocument(index);
            updateDocumentList();
            playSuccessSound();
            showMessage("Deleted", name + " deleted!");
        } else {
            playFailSound();
            showMessage("Error", "No document selected!");
        }
    }

    public void trimWhitespaceButtonClicked() {
        int index = documentList.getSelectedIndex();
        if (index >= 0) {
            Document d = documentLibrary.getDocument(index);
            String name = d.getName();
            d.fixWhitespace();
            d.fixPunctuationWhitespace();
            playSuccessSound();
            showMessage("Done", "Whitespace trimmed!");
            documentTextArea.setText(d.getText());
        } else {
            playFailSound();
            showMessage("Error", "No document selected!");
        }
    }

    public void runSpellcheckButtonClicked() {
        int index = documentList.getSelectedIndex();
        if (index >= 0) {
            Document d = documentLibrary.getDocument(index);
            documentLibrary.runSpellcheck(d);
            playSuccessSound();
            showMessage("Done", "Ran spellcheck!");
        } else {
            playFailSound();
            showMessage("Error", "No document selected!");
        }
    }

    public void showErrorsButtonClicked() {
        int index = documentList.getSelectedIndex();
        if (index >= 0) {
            Document d = documentLibrary.getDocument(index);
            while (d.getNumErrors() > 0) {
                errorCorrectionLoop(d);
            }
            playSuccessSound();
            showMessage("No errors", "No errors!");
        } else {
            playFailSound();
            showMessage("Error", "No document selected!");
        }
    }

    // EFFECTS: provides a pop-up for each spelling error and asks user for input
    public void errorCorrectionLoop(Document d) {
        SpellingError error = d.getNextError();
        String entry;
        String correctSpelling;
        String errorString = error.errorPreviewString();
        if (!error.getSuggestedWord().equals("")) {
            entry = JOptionPane.showInputDialog(frame,
                    errorString + "\n" + "Suggested word: " + error.getSuggestedWord()
                            + ". Please provide the correct spelling, or press Enter to use the suggestion.",
                    "Spelling Error", JOptionPane.QUESTION_MESSAGE);
        } else {
            entry = JOptionPane.showInputDialog(frame,
                    errorString + "\n" + "Please provide the correct spelling.",
                    "Spelling Error", JOptionPane.QUESTION_MESSAGE);
        }

        finalizeCorrectSpelling(d, error, entry);
        documentTextArea.setText(d.getText());
    }

    // EFFECTS: if entry is blank, set correct spelling to the suggested word, otherwise set it to the entry
    public void finalizeCorrectSpelling(Document d, SpellingError error, String entry) {
        String correctSpelling;
        if (entry.equals("")) {
            correctSpelling = error.getSuggestedWord();
        } else {
            correctSpelling = entry;
        }

        String oldText = d.getText();
        String newText = oldText.substring(0, error.getTypoPositionStart())
                + correctSpelling + oldText.substring(error.getTypoPositionEnd());
        d.replaceText(newText);
    }

    public void playSuccessSound() {
        InputStream chime;
        try {
            String filepath = new File("").getAbsolutePath().concat("/data/success.wav");
            chime = new FileInputStream(filepath);
            AudioStream audios = new AudioStream(chime);
            AudioPlayer.player.start(audios);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error");
        }
    }

    public void playFailSound() {
        InputStream chime;
        try {
            String filepath = new File("").getAbsolutePath().concat("/data/error.wav");
            chime = new FileInputStream(filepath);
            AudioStream audios = new AudioStream(chime);
            AudioPlayer.player.start(audios);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error");
        }
    }
}







