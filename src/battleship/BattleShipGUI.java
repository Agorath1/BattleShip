package battleship;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BattleShipGUI {
    public String buttonText = "";
    private JTextPane gameBoardArea;
    private JTextField inputField;
    private JFrame frame;
    private JButton fireButton;
    private JScrollPane scrollPane;

    public BattleShipGUI() {
        setFrame();
        setGameBoardArea();
        setInputPanel();

        // Add action listener for the Fire button
        fireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonText = inputField.getText().trim();
                inputField.setText("");
            }
        });
        // Add action listener for the JTextField to respond to Enter key
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonText = inputField.getText().trim();
                inputField.setText("");
            }
        });

        // Set frame visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BattleShipGUI::new);
    }

    // Clear out the game board
    public void resetGameBoard(String text) {
        System.out.print(text);
        gameBoardArea.setText(text);
    }

    public void addGameBoard(String text) {
        System.out.print(text);
        gameBoardArea.setText(gameBoardArea.getText() + text);
        gameBoardArea.setCaretPosition(gameBoardArea.getDocument().getLength());
    }

    private void setFrame() {
        // Create the main frame
        frame = new JFrame("Battleship Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
    }

    private void setGameBoardArea() {
        // Create the main panel with BorderLayout to fill available space
        JPanel boardPanel = new JPanel(new BorderLayout());
        gameBoardArea = new JTextPane();
        gameBoardArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
//        gameBoardArea.setBackground(Color.BLACK);
//        gameBoardArea.setForeground(Color.GREEN);
        gameBoardArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gameBoardArea.setEditable(false);
        StyledDocument doc = gameBoardArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        // Add the gameBoardArea to a JScrollPane and place it in the center of boardPanel
        scrollPane = new JScrollPane(gameBoardArea);
        boardPanel.add(scrollPane, BorderLayout.CENTER);


        // Add the boardPanel to the center of the frame
        frame.add(boardPanel, BorderLayout.CENTER);
    }

    public void setInputPanel() {
        // Create an input panel for coordinates
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        inputField = new JTextField(20);
        fireButton = new JButton("Submit");

//        inputPanel.add(new JLabel("Enter Coordinates (e.g., A5):"));
        inputPanel.add(inputField);
        inputPanel.add(fireButton);

        // Add input panel at the bottom of the frame
        frame.add(inputPanel, BorderLayout.SOUTH);
    }
}
