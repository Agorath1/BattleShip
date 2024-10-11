package battleship;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to game BattleShip, programmed by Robert");
        System.out.println();

        boolean loop = true;
        while (loop) {
            System.out.println("Enter a command to continue(Two Player, Quit)");
            String input = sc.nextLine().toLowerCase();

            // Main menu loop
            switch (input) {
                case "two player":
                    battleLoop();
                    break;
                case "quit":
                case "q":
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid Command");
            }
        }

    }

    // Two player battle loop
    public static void battleLoop() {

        // Create player one
        System.out.println("Enter first player name:");
        String playerName = sc.next();
        Player player1 = new Player(playerName);

        // Create player two
        System.out.println("Enter second player name:");
        playerName = sc.next();
        Player player2 = new Player(playerName);

        // Place all the ships for player one
        placeShips(player1);

        // Clears the screen so next player can not see ship placement
        clearScreen();

        // Place all ships for player two
        placeShips(player2);

        while (true) {
            // Clear the screen between player movements
            clearScreen();

            player2.printFogField();
            System.out.println("---------------------");
            player1.printVisibleField();
            System.out.println();
            System.out.println(player1.getName() + ", it's your turn:");
            String a = sc.next();
            System.out.println();
            shoot(player2, a);

            if (player2.isLost()) break;

            // Clear the screen between player movements
            clearScreen();

            player1.printFogField();
            System.out.println("---------------------");
            player2.printVisibleField();
            System.out.println();
            System.out.println(player2.getName() + ", it's your turn:");
            a = sc.next();
            System.out.println();
            shoot(player1, a);

            if (player1.isLost()) break;
        }

        if (player1.isLost()) {
            System.out.println(player2.getName() + " has won!");
        } else {
            System.out.println(player1.getName() + " has won!");
        }
    }

    // Checks if coordinates, location, length, and other ships to verify ship can be placed
    // Also places the ship
    public static boolean verifyShipPlacement(String a, String b, Ship ship, Player player) {

        // Checks for string size
        if (a.length() < 2 || b.length() < 2 || a.length() > 3 || b.length() > 3) {
            System.out.println("Error!");
            return false;
        }

        // Convert to array coordinates
        int[] coordinate1 = stringCoordinateToInt(a);
        int[] coordinate2 = stringCoordinateToInt(b);
        int x1 = coordinate1[0];
        int x2 = coordinate2[0];
        int y1 = coordinate1[1];
        int y2 = coordinate2[1];

        // Checks for the boundaries
        if (invalidCoordinate(x1) ||
                invalidCoordinate(x2) ||
                invalidCoordinate(y1) ||
                invalidCoordinate(y2)) {
            System.out.println("Error!");
            return false;
        }

        // Check if diagonal
        if (Math.abs(x1 - x2) > 0 && Math.abs(y1 - y2) > 0) {
            System.out.println("Error!");
            return false;
        }

        // Check ship length
        int length = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)) + 1;
        if (length != ship.getHealth()) {
            System.out.println("Error, Wrong length of the " + ship.getName() + "! Try again:");
            return false;
        }

        // Verify no overlap or adjacent
        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 > y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }

        // Make sure the ship is not adjacent to another ship
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                if (player.getShip(x, y) != null) {
                    System.out.println("Error! Wrong ship location! Try again:");
                    return false;
                }
                if(!invalidCoordinate(x + 1) && player.getShip(x + 1, y) != null) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                if(!invalidCoordinate(x - 1) && player.getShip(x - 1, y) != null) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                if(!invalidCoordinate(y + 1) && player.getShip(x, y + 1) != null) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                if(!invalidCoordinate(y - 1) && player.getShip(x, y - 1) != null) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
        }

        // Place ship between two points
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                player.setVisibleField(ship, x, y);
            }
        }
        return true;
    }

    // Verifies the battleship field size is within the range
    public static boolean invalidCoordinate(int n) {
        return (n < 0 || n > 9);
    }

    // Converts coordinates (ex. A1 to 0, 0) to x, y coordinates
    public static int[] stringCoordinateToInt(String coordinate) {
        int x = coordinate.charAt(0) - 'A';
        int y = coordinate.charAt(1) - '1';
        if (coordinate.length() == 3) y = (y + 1) * 10 + (coordinate.charAt(2) - '0') - 1;
        return new int[] {x, y};
    }

    // Converts x, y coordinates to string coordinates (ex. 0, 0 to A1)
    public static String intCoordinateToString(int x, int y) {
        return "" + (char) ( x + 'A') + (y + 1);
    }

    // Verifies proper locations then shoots spot
    // Verifies if there is a ship and updates the ship health, the visible field, and
    // the fog field.
    public static void shoot(Player player, String coordinates) {
        if (coordinates.length() < 2) {
            System.out.println("Improper coordinates");
            return;
        }
        int[] xy = stringCoordinateToInt(coordinates);
        int x = xy[0];
        int y = xy[1];

        if (invalidCoordinate(x) || invalidCoordinate(y)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return;
        }

        Ship ship = player.getShip(x, y);
        if (ship != null || player.getFogField(x, y) == 'X') {
            player.setFogField(x, y, 'X');
            if (ship != null) ship.hitShip();
            player.setVisibleField(null, x, y);
            player.printFogField();
            System.out.println();
            if (player.isLost()) {
                System.out.println("You sank the last ship. You won. Congratulations!");
            } else if (ship != null && ship.getHealth() == 0) {
                System.out.println("You sank a ship! Specify a new target:");
            } else {
                System.out.println("You hit a ship!  Try again:");
            }
        } else {
            player.setFogField(x, y, 'M');
            player.printFogField();
            System.out.println();
            System.out.println("You missed!  Try again:");
        }
    }

    // Loops through players ships to place them all.
    public static void placeShips(Player player) {

        System.out.println(player.getName() + ", place your ships on the game field");
        System.out.println();

        for (Ship ship : player.getShips()) {
            player.printVisibleField();
            System.out.println();
            System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getHealth() + " cells):");
            System.out.println();


            // Verify the coordinates are valid, keeps looping otherwise.
            boolean valid = false;
            while (!valid) {
                // Get coordinates
                System.out.println("Enter first coordinate: ");
                String a = sc.next().toUpperCase();
                int[] coordinates = stringCoordinateToInt(a);
                if (invalidCoordinate(coordinates[0]) || invalidCoordinate(coordinates[1])) {
                    System.out.println("Invalid coordinate.");
                    continue;
                }
                // Display possible coordinates
                printPossibleCoordinates(coordinates[0], coordinates[1], ship.getHealth());
                System.out.println("Enter second coordinate: ");
                String b = sc.next().toUpperCase();
                valid = verifyShipPlacement(a, b, ship, player);
                System.out.println();
            }
        }
        player.printVisibleField();
        System.out.println();
    }

    // Clears cmd screen between players
    public static void clearScreen() {
        System.out.println("Press Enter and pass the move to another player.");
        sc.nextLine();
        sc.nextLine();
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // This command will clear the CMD screen on Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // ANSI escape code for clearing the screen on Unix-based systems
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Press enter to continue");
        sc.nextLine();
    }

    // Prints possible coordinates for ship placement based off of first coordinate.
    public static void printPossibleCoordinates(int x, int y, int length) {
        System.out.print("Valid placements: ");
        length -= 1;
        if (!invalidCoordinate(x + length)) System.out.print(intCoordinateToString(x + length, y) + " ");
        if (!invalidCoordinate(x - length)) System.out.print(intCoordinateToString(x - length, y) + " ");
        if (!invalidCoordinate(y + length)) System.out.print(intCoordinateToString(x, y + length) + " ");
        if (!invalidCoordinate(y - length)) System.out.print(intCoordinateToString(x, y - length) + " ");
        System.out.println();
    }
}
