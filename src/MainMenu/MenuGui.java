package MainMenu;

import javax.swing.*;
import java.awt.*;

public class MenuGui {
    private JFrame menuFrame;
    private JPanel menuPanel;
    private MenuGui menu;

    public MenuGui() {
        menuStart();
    }

    public static void main(String[] args) {
        MenuGui menu = new MenuGui();
    }

    public void menuStart() {
        createFrame();
    }

    private void createFrame() {
        menuFrame = new JFrame("Main Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(800, 600);
        menuFrame.setLayout(new BorderLayout());
        menuFrame.setLocationRelativeTo(null);

        menuFrame.setVisible(true);
    }

    private void createPanel() {
        menuPanel = new JPanel(new BorderLayout());
    }
}
