package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {

    private JFrame frame;
    private JPanel panel;

    private JButton loadLibraryButton;
    private JButton saveLibraryButton;
    private JLabel testLabel;


    private int count = 0;

    public GUI() {
        frame = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        testLabel = new JLabel("Some text");
        loadLibraryButton = new JButton("Load Documents");
        loadLibraryButton.addActionListener(this);
        saveLibraryButton = new JButton("Save Documents");

        panel.add(testLabel);
        panel.add(loadLibraryButton);
        panel.add(saveLibraryButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("BeautySpell");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        testLabel.setText(String.valueOf(count));
    }
}
