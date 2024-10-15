package battleship;

import java.util.Arrays;
import java.lang.StringBuilder;

public class Field {
    final char fog = '~';
    char[][] fogField;
    Ship[][] visibleField;
    int width = 10;
    int length = 10;

    public Field(int width, int length) {
        this.length = length;
        this.width = width;
        fogField = new char[width][length];
        visibleField = new Ship[width][length];
        fillFogField();
    }

    // Converts coordinates (ex. A1 to 0, 0) to x, y coordinates
    public static int[] stringCoordinateToInt(String coordinate) {
        int w = coordinate.charAt(0) - 'A';
        int l = coordinate.charAt(1) - '1';
        if (coordinate.length() == 3) l = (l + 1) * 10 + (coordinate.charAt(2) - '0') - 1;
        return new int[]{w, l};
    }

    // Converts x, y coordinates to string coordinates (ex. 0, 0 to A1)
    public static String intCoordinateToString(int w, int l) {
        return "" + (char) (w + 'A') + (l + 1);
    }

    public void setFogField(int[] wl, char c) {
        fogField[wl[0]][wl[1]] = c;
    }

    public char getFogField(int[] wl) {
        return fogField[wl[0]][wl[1]];
    }

    public void setVisibleField(int[] wl, Ship ship) {
        visibleField[wl[0]][wl[1]] = ship;
    }

    public Ship getVisibleField(int[] wl) {
        return visibleField[wl[0]][wl[1]];
    }

    public void fillFogField() {
        for (char[] f : fogField) {
            Arrays.fill(f, fog);
        }
    }

    public String printVisibleField() {
        StringBuilder printVisible = new StringBuilder();
        printVisible.append("  ");
        for (int i = 0; i < length; i++) {
            printVisible.append(" ").append(i + 1);
        }
        printVisible.append("\n");
        for (int i = 0; i < width; i++) {
            printVisible.append((char) ('A' + i));
            for (int j = 0; j < length; j++) {
                if (visibleField[i][j] != null) {
                    printVisible.append(" O");
                } else {
                    printVisible.append(" ").append(fogField[i][j]);
                }
            }
            printVisible.append("\n");
        }
        return printVisible.toString();
    }

    public String printFogField() {
        StringBuilder printFog = new StringBuilder();
        printFog.append("  ");
        for (int i = 0; i < length; i++) {
            printFog.append(" ").append(i + 1);
        }
        printFog.append("\n");
        for (int i = 0; i < width; i++) {
            printFog.append((char) ('A' + i));
            for (int j = 0; j < length; j++) {
                printFog.append(" ").append(fogField[i][j]);
            }
            printFog.append("\n");
        }
        return printFog.toString();
    }

    public boolean isValidCoordinate(String coordinate) {
        int[] wl = stringCoordinateToInt(coordinate);
        return isValidCoordinate(wl);
    }

    public boolean isValidCoordinate(int[] wl) {
        return (wl[1] >= 0 && wl[1] < this.length) && (wl[0] >= 0 && wl[0] < this.width);
    }

    // Prints possible coordinates for ship placement based off of first coordinate.
    public String printPossibleCoordinates(int w, int l, int length) {
        StringBuilder valid = new StringBuilder();
        length -= 1;
        if (isValidCoordinate(new int[]{w + length, l}))
            valid.append(Field.intCoordinateToString(w + length, l)).append(" ");
        if (isValidCoordinate(new int[]{w - length, l}))
            valid.append(Field.intCoordinateToString(w - length, l)).append(" ");
        if (isValidCoordinate(new int[]{w, l + length}))
            valid.append(Field.intCoordinateToString(w, l + length)).append(" ");
        if (isValidCoordinate(new int[]{w, l - length}))
            valid.append(Field.intCoordinateToString(w, l - length)).append(" ");
        return valid.toString();
    }

}
