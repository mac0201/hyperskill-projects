package battleship.ship;

import java.util.ArrayList;

public class Ship {

    private final ShipCore CORE;

    private final int[] START_CELL;
    private final int[] END_CELL;
    private final ArrayList<String> CELLS_TAKEN;
    private final ArrayList<String> DAMAGED_CELLS;

    private int health;
    private boolean destroyed;

    public Ship(ShipCore shipCore, int[] startCell, int[] endCell) {
        CORE = shipCore;
        START_CELL = startCell;
        END_CELL = endCell;
        CELLS_TAKEN = new ArrayList<>();
        DAMAGED_CELLS = new ArrayList<>(shipCore.getSize());
        health = shipCore.getSize();
        destroyed = false;
        fillCellsTaken();
//        System.out.println("Created ship: " + getName());
    }

    private void fillCellsTaken() {
        for (int i = START_CELL[0]; i <= END_CELL[0]; i++) {
            for (int j = START_CELL[1]; j <= END_CELL[1]; j++) {
                CELLS_TAKEN.add(i + "," + j);
            }
        }
    }

    public void updateDamagedCells(int row, int col) {
        DAMAGED_CELLS.add(row + "," + col);
    }

    public ArrayList<String> getDamagedCells() {
        return DAMAGED_CELLS;
    }

    public boolean takeDamage() {
        if (health > 0) health -= 1;
        if (health == 0) {
            setDestroyed();
            return false;
        }
        return true;
    }

    private void setDestroyed() {
        if (!isDestroyed()) destroyed = true;
    }

    public ArrayList<String> getCellsTaken() {
        return CELLS_TAKEN;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
