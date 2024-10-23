package battleship;

import java.util.Objects;

public class BattleShipMain {
    public static void twoPlayer() {
        addScreen("Enter name for player one: ");
        Player playerOne = new Player(getInput());
        addScreen(playerOne.getName() + "\n\n");
        addScreen("Enter name for player two:\n");
        Player playerTwo = new Player(getInput());
        addScreen(playerTwo.getName() + "\n\n");

        // Place all the ships for player one
        placeShips(playerOne);

        // Place all ships for player two
        placeShips(playerTwo);

        while (true) {
            battleLoop(playerOne, playerTwo);
            if (playerTwo.isLost()) break;
            addScreen("\nType anything to continue.");
            getInput();

            battleLoop(playerTwo, playerOne);
            if (playerOne.isLost()) break;
            addScreen("\nType anything to continue.");
            getInput();
        }

        if (playerOne.isLost()) {
            addScreen(playerTwo.getName() + " has won!");
        } else {
            addScreen(playerOne.getName() + " has won!");
        }
    }

    public static void placeShips(Player player) {
        Field field = player.field;

        for (Ship ship : player.getShips()) {
            clearScreen(player.field.printVisibleField());
            addScreen(player.getName() + ", place your ships on the game field.\n");
            addScreen("\nEnter the coordinates of the " + ship.getName() + " (" + ship.getHealth() + " cells).\n\n");


            // Verify the coordinates are valid, keeps looping otherwise.
            boolean valid = false;
            while (!valid) {
                // Get coordinates
                addScreen("Enter first coordinate: ");
                String a = getInput().toUpperCase();
                addScreen(a);
                if (Objects.equals(a, "RANDOM") || Objects.equals(a, "R")) {
                    player.setRandomShip(ship);
                    break;
                }
                if (!field.isValidCoordinate(a)) {
                    addScreen("\nInvalid coordinate.\n");
                    a = null;
                    continue;
                }
                // Display possible coordinates
                addScreen("\nPossible placements: ");
                int[] coordinates = Field.stringCoordinateToInt(a);
                addScreen(field.printPossibleCoordinates(coordinates[0], coordinates[1], ship.getHealth()));
                addScreen("\nEnter second coordinate: ");
                String b = getInput().toUpperCase();
                addScreen(b + "\n");
                valid = verifyShipPlacement(a, b, ship, player);
            }
        }
    }

    public static boolean verifyShipPlacement(String a, String b, Ship ship, Player player) {
        Field field = player.field;

        // Checks for valid string
        if (!Field.isValidCoordinateString(a) || !Field.isValidCoordinateString(b)) {
            addScreen("Error! Invalid coordinate string\n");
            return false;
        }

        // Convert to array coordinates
        int[] aInt = Field.stringCoordinateToInt(a);
        int[] bInt = Field.stringCoordinateToInt(b);

        // Checks for the boundaries
        if (!field.isValidCoordinate(aInt) || !field.isValidCoordinate(bInt)) {
            addScreen("Error! Coordinates outside boundaries\n");
            return false;
        }

        int w1 = aInt[0];
        int w2 = bInt[0];
        int l1 = aInt[1];
        int l2 = bInt[1];

        // Switch axis so that second axis is the largest
        if (w1 > w2) {
            int temp = w1;
            w1 = w2;
            w2 = temp;
        }
        if (l1 > l2) {
            int temp = l1;
            l1 = l2;
            l2 = temp;
        }

        // Check if diagonal
        if (w2 - w1 > 0 && l2 - l1 > 0) {
            addScreen("Error! Ship is diagonal.\n");
            return false;
        }

        // Check ship length
        int length = Math.max(w2 - w1, l2 - l1) + 1;
        if (length != ship.getHealth()) {
            addScreen("Error, Wrong length of the " + ship.getName() + "! Try again:\n");
            return false;
        }

        // Make sure the ship is not adjacent to another ship
        if (!field.verifyBetween(aInt, bInt)) {
            addScreen("Error! Ship is too close to another ship");
            return false;
        }

        // Place ship between two points
        field.placeShip(aInt, bInt, ship);
        return true;
    }

    public static void battleLoop(Player playerOne, Player playerTwo) {

        // Clear the screen between player movements
        clearScreen(playerTwo.field.printFogField());
        addScreen("\n---------------------\n");
        addScreen(playerOne.field.printVisibleField() + "\n");
        addScreen("\n" + playerOne.getName() + ", it's your turn:");
        String a = getInput();
        addScreen("\n");
        shoot(playerTwo, a);
    }

    public static void shoot(Player player, String coordinates) {
        if (coordinates.length() < 2) {
            addScreen("Improper coordinates\n");
            return;
        }
        int[] wl = Field.stringCoordinateToInt(coordinates);
        int w = wl[0];
        int l = wl[1];

        if (!player.field.isValidCoordinate(wl)) {
            addScreen("Error! You entered the wrong coordinates! Try again:\n");
            return;
        }

        Ship ship = player.field.getVisibleField(new int[]{w, l});
        if (ship != null || player.field.getFogField(new int[]{w, l}) == 'X') {
            player.field.setFogField(new int[]{w, l}, 'X');
            if (ship != null) ship.hitShip();
            player.field.setVisibleField(new int[]{w, l}, null);
            clearScreen(player.field.printFogField());
            addScreen("\n");
            if (player.isLost()) {
                addScreen("You sank the last ship.\n");
            } else if (ship != null && ship.getHealth() == 0) {
                addScreen("You sank a ship! Specify a new target:\n");
            } else {
                addScreen("You hit a ship!  Try again:\n");
            }
        } else {
            player.field.setFogField(new int[]{w, l}, 'M');
            clearScreen(player.field.printFogField());
            addScreen("\nYou missed!  Try again:");
        }
    }

    public static void clearScreen(String text) {
        Main.gui.resetGameBoard(text);
    }

    public static void addScreen(String text) {
        Main.gui.addGameBoard(text);
    }

    public static String getInput() {
        // For testing only
        if (!Main.line.isEmpty()) {
            String input = Main.line.get(0);
            System.out.println(input);
            Main.line.remove(0);
            return input;
        }

        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (Main.gui.buttonText.isEmpty());
        String input = Main.gui.buttonText;
        Main.gui.buttonText = "";
        return input;
    }
}
