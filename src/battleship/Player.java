package battleship;

import java.util.Arrays;

public class Player {
    private final Ship[] ships;
    private final char[][] fogField = new char[10][10];
    private final Ship[][] visibleField = new Ship[10][10];
    private final String name;

    // Constructor
    public Player(String name) {
        this.name = name;
        ships = new Ship[] {
                new Ship(Ship.Ships.AIRCRAFT_CARRIER),
                new Ship(Ship.Ships.BATTLESHIP),
                new Ship(Ship.Ships.SUBMARINE),
                new Ship(Ship.Ships.CRUISER),
                new Ship(Ship.Ships.DESTROYER)
        };
        createFogField();
    }

    // Return an array of Ship objects
    public Ship[] getShips() {
        return ships;
    }

    // Create the array of fog
    public void createFogField() {
        for (char[] chars : this.fogField) {
            Arrays.fill(chars, '~');
        }
    }

    // Prints the array of fog field with coordinates
    public void printFogField() {
        System.out.print(" ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        char r = 'A';
        for (char[] chars : this.fogField) {
            System.out.print(r++);
            for (char aChar : chars) {
                System.out.print(" " + aChar);
            }
            System.out.println();
        }
    }

    //  Change individual cells of 2d array
    public void setFogField(int x, int y, char c) {
        this.fogField[x][y] = c;
    }

    // Gets char of individual fog cell in 2d array
    public char getFogField(int x, int y) {
        return this.fogField[x][y];
    }

    // Prints the locations of the ships filling in nulls with the fog field
    public void printVisibleField() {
        System.out.print(" ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        char r = 'A';
        for (int i = 0; i < this.visibleField.length; i++) {
            System.out.print(r++);
            for (int j = 0; j < this.visibleField[i].length; j++) {
                if (visibleField[i][j] == null) {
                    System.out.print(" " + this.fogField[i][j]);
                } else {
                    System.out.print(" O");
                }
            }
            System.out.println();
        }
    }

    // Places a ship reference in the visible field 2d array
    public void setVisibleField(Ship ship, int x, int y) {
        this.visibleField[x][y] = ship;
    }

    // Retrieve the ship from the visible field
    public Ship getShip(int x, int y) {
        return this.visibleField[x][y];
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
}
