package battleship;

import java.util.Random;

public class Player {
    private final Ship[] ships;
    public Field field;
    private final String name;

    // Constructor
    public Player(String name) {
        this.name = name;
        ships = new Ship[] {
                new Ship(Ships.AIRCRAFT_CARRIER),
                new Ship(Ships.BATTLESHIP),
                new Ship(Ships.SUBMARINE),
                new Ship(Ships.CRUISER),
                new Ship(Ships.DESTROYER)
        };
        createField(10, 10);
    }

    // Return an array of Ship objects
    public Ship[] getShips() {
        return ships;
    }

    // Create the array of fog
    public void createField(int length, int width) {
        this.field = new Field(width, length);
    }

    // Checks if all ships health is at zero and declares the game is lost
    public boolean isLost() {
        for (Ship ship : this.getShips()) {
            if (ship.getHealth() > 0) return false;
        }
        return true;
    }

    // Get player name
    public String getName() {
        return this.name;
    }

    public void setRandomShip(Ship ship) {
        boolean valid = false;
        Random random = new Random();
        while (!valid) {
            int[] wl = new int[2];
            wl[0] = random.nextInt(field.width);
            wl[1] = random.nextInt(field.length);
            if (field.getVisibleField(wl) != null) continue;

            String[] loc = field.printPossibleCoordinates(wl[0], wl[1], ship.getHealth()).split(" ");
            int i = random.nextInt(loc.length);

            valid = verifyBetween(wl, Field.stringCoordinateToInt(loc[i]), ship);
        }
    }

    public boolean verifyBetween(int[] wl1, int[] wl2, Ship ship) {
        if (wl1[0] > wl2[0]) {
            int temp = wl1[0];
            wl1[0] = wl2[0];
            wl2[0] = temp;
        }
        if (wl1[1] > wl2[1]) {
            int temp = wl1[1];
            wl1[1] = wl2[1];
            wl2[1] = temp;
        }

        for (int w = wl1[0]; w <= wl2[0]; w++) {
            for (int l = wl1[1]; l <= wl2[1]; l++) {
                if (field.getVisibleField(new int[]{w, l}) != null) return false;
                if (!verifyAdjacent(w, l)) return false;
            }
        }

        for (int w = wl1[0]; w <= wl2[0]; w++) {
            for (int l = wl1[1]; l <= wl2[1]; l++) {
                field.setVisibleField(new int[]{w, l}, ship);
            }
        }
        return true;
    }

    public boolean verifyAdjacent(int w, int l) {
        if (field.isValidCoordinate(new int[]{w + 1, l}) && field.getVisibleField(new int[]{w + 1, l}) != null)
            return false;
        if (field.isValidCoordinate(new int[]{w - 1, l}) && field.getVisibleField(new int[]{w - 1, l}) != null)
            return false;
        if (field.isValidCoordinate(new int[]{w, l + 1}) && field.getVisibleField(new int[]{w, l + 1}) != null)
            return false;
        return !field.isValidCoordinate(new int[]{w, l - 1}) || field.getVisibleField(new int[]{w, l - 1}) == null;
    }
}
