package ui;

import model.Document;
import model.DocumentLibrary;
import persistence.DocReader;
import persistence.DocWriter;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public BeautySpellGUI() throws FileNotFoundException {
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
        documentListScrollPane.setPreferredSize(new Dimension(LEFT_SIDEBAR_WIDTH, 200));
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
        logoLabel = new JLabel("BeautySpell");

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
            int returnValue = openFileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String path = openFileChooser.getSelectedFile().getAbsolutePath();
                docReader = new DocReader(path);
                loadDocumentLibrary();
            }
        } else if (e.getSource() == saveLibraryButton) {
            String path = new File("").getAbsolutePath().concat("/data/MyDocumentLibrary.json");
            saveDocumentLibrary(path);
            showMessage("Saved", "Saved library to " + path);
        } else if (e.getSource() == newDocumentButton) {
            String name = (String) JOptionPane.showInputDialog(frame,
                    "Document name:", "New Document", JOptionPane.PLAIN_MESSAGE);
            if (name.length() > 0) {
                documentLibrary.addDocument(new Document(name, documentTextArea.getText()));
                updateDocumentList();
            }
        } else if (e.getSource() == openDocumentButton) {
            int index = documentList.getSelectedIndex();
            if (index >= 0) {
                documentTextArea.setText(documentLibrary.getDocument(index).getText());
            } else {
                showMessage("Error", "No document selected!");
            }
        } else if (e.getSource() == saveDocumentButton) {
            int index = documentList.getSelectedIndex();
            if (index >= 0) {
                documentLibrary.setDocumentText(index, documentTextArea.getText());
                showMessage("Saved", documentLibrary.getDocument(index).getName() + " saved!");
            } else {
                showMessage("Error", "No document selected!");
            }
        } else if (e.getSource() == deleteDocumentButton) {
            int index = documentList.getSelectedIndex();
            if (index >= 0) {
                String name = documentLibrary.getDocument(index).getName();
                documentLibrary.deleteDocument(index);
                updateDocumentList();
                showMessage("Deleted", name + " deleted!");
            } else {
                showMessage("Error", "No document selected!");
            }
        } else if (e.getSource() == trimWhitespaceButton) {
            int index = documentList.getSelectedIndex();
            if (index >= 0) {
                Document d = documentLibrary.getDocument(index);
                String name = d.getName();
                d.fixWhitespace();
                d.fixPunctuationWhitespace();
                showMessage("Done", "Whitespace trimmed!");
                documentTextArea.setText(d.getText());
            } else {
                showMessage("Error", "No document selected!");
            }
        } else if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }
}







