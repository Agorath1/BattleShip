package battleship;

import java.util.Arrays;

public class Player {
    private final Ship[] ships;
    private final char[][] fogField = new char[10][10];
    private final Ship[][] visibleField = new Ship[10][10];

    public Player() {
        ships = new Ship[] {
                new Ship(Ship.Ships.AIRCRAFT_CARRIER),
                new Ship(Ship.Ships.BATTLESHIP),
                new Ship(Ship.Ships.SUBMARINE),
                new Ship(Ship.Ships.CRUISER),
                new Ship(Ship.Ships.DESTROYER)
        };
        createFogField();
    }

    public Ship[] getShips() {
        return ships;
    }

    public void createFogField() {
        for (char[] chars : this.fogField) {
            Arrays.fill(chars, '~');
        }
    }

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
    public void setFogField(int x, int y, char c) {
        this.fogField[x][y] = c;
    }
    public char getFogField(int x, int y) {
        return this.fogField[x][y];
    }

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
    public void setVisibleField(Ship ship, int x, int y) {
        this.visibleField[x][y] = ship;
    }

    public void setShip(Ship ship, int x, int y) {
        this.visibleField[x][y] = ship;
    }
    public Ship getShip(int x, int y) {
        return this.visibleField[x][y];
    }

    public boolean isLost() {
        for (Ship ship : this.getShips()) {
            if (ship.getHealth() > 0) return false;
        }
        return true;
    }
}
