package battleship;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;

public class Main {
    public static BattleShipGUI gui;
    public static List<String> line = new ArrayList<>();

    public static void main(String[] args) {
        Main.gui = new BattleShipGUI();
        readFile();
        BattleShipMain.twoPlayer();
    }

    public static void readFile() {
        String filePath = "C:/Users/rober/IdeaProjects/BattleShip_In_Console/src/battleship/Testing";

        try {
            Main.line = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
