package battleship.ship;

public enum ShipCore {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String NAME;
    private final int SIZE;

    ShipCore(String name, int size) {
        NAME = name;
        SIZE = size;
    }

    public String getName() {
        return this.NAME;
    }

    public int getSize() {
        return SIZE;
    }
}
