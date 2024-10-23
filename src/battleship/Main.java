package battleship;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static BattleShipGUI gui;
    public static List<String> line = new ArrayList<>();
    public static HashMap<String, String> options = new HashMap<>();

    public static void main(String[] args) {
        readOptions();
        Main.gui = new BattleShipGUI();
        BattleShipMain.twoPlayer();
    }

    public static List<String> readFile(String filePath) {
        InputStream inputStream = Main.class.getResourceAsStream(filePath);
        List<String> output = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public static void readOptions() {
        InputStream inputStream = Main.class.getResourceAsStream("Options");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] option = line.split(":");
                Main.options.put(option[0], option[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
