package battleship;

enum Ships {
    AIRCRAFT_CARRIER(5),
    BATTLESHIP(4),
    SUBMARINE(3),
    CRUISER(3),
    DESTROYER(2);

    private final int length;

    Ships(int length) {
        this.length = length;
    }

    // Restructures the toString for customer name return.
    @Override
    public String toString() {
        String[] name = name().split("_");
        for (int i = 0; i < name.length; i++) {
            name[i] = name[i].toLowerCase();
            name[i] = name[i].substring(0, 1).toUpperCase() + name[i].substring(1);
        }
        return String.join(" ", name);
    }

    public int getLength() {
        return this.length;
    }
}

public class Ship {
    private int health;
    private final String name;

    // Constructor
    public Ship(Ships ship) {
        this.health = ship.getLength();
        this.name = ship.toString();
    }

    // Get health by number of cells left
    public int getHealth() {
        return this.health;
    }

    // Reduce health to minimum of zero
    public void hitShip() {
        --this.health;
    }

    // Retrieve the ship name
    public String getName() {
        return this.name;
    }
}
