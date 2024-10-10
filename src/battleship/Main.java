package battleship;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Player player1 = new Player();
        Player player2 = new Player();

        System.out.println("Player 1, place your ships on the game field");
        System.out.println();
        placeShips(player1);

        System.out.println("Press Enter and pass the move to another player");
        sc.nextLine();
        sc.nextLine();
        clearScreen();

        System.out.println("Player 2, place your ships on the game field");
        System.out.println();
        placeShips(player2);

        while (true) {

            System.out.println("Press Enter and pass the move to another player");
            sc.nextLine();
            sc.nextLine();
            clearScreen();

            player2.printFogField();
            System.out.println("---------------------");
            player1.printVisibleField();
            System.out.println();
            System.out.println("Player 1, it's your turn:");
            String a = sc.next();
            System.out.println();
            shoot(player2, a);

            if (player2.isLost()) break;

            System.out.println("Press Enter and pass the move to another player");
            sc.nextLine();
            sc.nextLine();
            clearScreen();

            player1.printFogField();
            System.out.println("---------------------");
            player2.printVisibleField();
            System.out.println();
            System.out.println("Player 2, it's your turn:");
            a = sc.next();
            System.out.println();
            shoot(player1, a);

            if (player1.isLost()) break;
        }

        if (player1.isLost()) {
            System.out.println("Player 2 has won!");
        } else {
            System.out.println("Player 1 has won!");
        }
    }

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
                player.setShip(ship, x, y);
            }
        }
        return true;
    }

    public static boolean invalidCoordinate(int n) {
        return (n < 0 || n > 9);
    }

    public static int[] stringCoordinateToInt(String coordinate) {
        int x = coordinate.charAt(0) - 'A';
        int y = coordinate.charAt(1) - '1';
        if (coordinate.length() == 3) y = (y + 1) * 10 + (coordinate.charAt(2) - '0') - 1;
        return new int[] {x, y};
    }

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

    public static void placeShips(Player player) {
        for (Ship ship : player.getShips()) {
            player.printVisibleField();
            System.out.println();
            System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getHealth() + " cells):");
            System.out.println();


            // Verify the coordinates are valid.
            boolean valid = false;
            while (!valid) {
                // Get coordinates
                String a = sc.next().toUpperCase();
                String b = sc.next().toUpperCase();
                valid = verifyShipPlacement(a, b, ship, player);
                System.out.println();
            }
        }
        player.printVisibleField();
        System.out.println();
    }

    public static void clearScreen() {
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
    }
}
