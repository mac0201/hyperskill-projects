package battleship.core;

import battleship.ship.Ship;
import battleship.ship.ShipCore;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private static final int SIZE = 10;
    private final char[][] BOARD;

    public Board() {
        BOARD = new char[SIZE][SIZE];
        // fill the board with fog
        for (char[] row : BOARD) {
            Arrays.fill(row, Symbol.FOG.getSymbol());
        }
    }

    /**
     * Prints the game board to the console. If parameter is true, then a 'hidden' board will be printed
     *  filled with fog of war. If parameter is false, then board will be printed with all ships revealed.
     * @param toggleFog boolean indicating if hidden version of the board should be printed
     */
    public void printBoard(boolean toggleFog) {
//        System.out.println();
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int row = 0, letter = 'A'; row < SIZE; row++, letter++){
            System.out.print((char) letter);
            for (int col = 0; col < SIZE; col++) {
                if (toggleFog && BOARD[row][col] == Symbol.SHIP.getSymbol()) {
                    System.out.print(" " + Symbol.FOG.getSymbol());
                }
                else {
                    System.out.print(" " + BOARD[row][col]);
                }
            }
            System.out.println();
        }
    }

    /**
     * Validates whether ship can be placed from startCell to endCell
     * @param startCell int array containing row/col coordinates of starting cell
     * @param endCell int array containing row/col coordinates of ending cell
     * @param shipCore ShipCore instance
     * @return true if ship can fit, else false
     * */
    private boolean canPlaceShip(int[] startCell, int[] endCell, ShipCore shipCore) {
        int sumOne = startCell[0] + startCell[1];
        int sumTwo = endCell[0] + endCell[1];
        if ((sumTwo - sumOne + 1) != shipCore.getSize()) {
            System.out.printf("Error! Wrong length of the %s! Try again:\n", shipCore.getName());
            return false;
        }
        if (!checkNeighbours(startCell, endCell)) {
            System.out.println("Error! You placed it too close to another one. Try again:\n");
            return false;
        }
        return true;
    }

    /**
     * Checks if the cells surrounding startCell to endCell are free
     * @param startCell int array containing row/col coordinates of starting cell
     * @param endCell int array containing row/col coordinates of ending cell
     * @return true if all surrounding cells are free
     */
    private boolean checkNeighbours(int[] startCell, int[] endCell) {
        for (int row = startCell[0] - 1; row <= endCell[0] + 1; row++) {
            if (row == -1 || row == SIZE) continue;
            for (int col = startCell[1] - 1; col <= endCell[1] + 1; col++) {
                if (col == -1 || col == SIZE) continue;
                // skip the cells where ship is to be placed
                if ((row >= startCell[0] && row <= endCell[0]) && (col >= startCell[1] && col <= endCell[1])) continue;
                if (BOARD[row][col] != Symbol.FOG.getSymbol()) return false;
            }
        }
        return true;
    }

    /**
     * Validates if ShipCore instance can fit on game board, based on the instance's size.
     * If valid, creates new Ship object and adds it to player's list of ships
     * @param startCell int array containing row/col coordinates of starting cell
     * @param endCell int array containing row/col coordinates of ending cell
     * @param currentShipCoreInstance ShipCore enum instance used to create new Ship object
     * @return true if ship is successfully placed on grid, else false
     */
    boolean placeShip(int[] startCell, int[] endCell, ShipCore currentShipCoreInstance, ArrayList<Ship> shipList) {
        if (!canPlaceShip(startCell, endCell, currentShipCoreInstance)) {
            return false;
        }
        // create Ship object and add to list of player's ships
        Ship ship = new Ship(currentShipCoreInstance, startCell, endCell);
        shipList.add(ship);

        // add ship to game board
        for (int row = startCell[0]; row <= endCell[0]; row++) {
            for (int col = startCell[1]; col <= endCell[1]; col++) {
                BOARD[row][col] = Symbol.SHIP.getSymbol();
            }
        }
        return true;
    }

    // Place MISS symbol in cell
    void placeMissSymbol(int row, int col) {
        if (BOARD[row][col] != Symbol.HIT.getSymbol()) {
            BOARD[row][col] = Symbol.MISS.getSymbol();
        }
    }

    // Place HIT symbol in cell
    void placeHitSymbol(int row, int col) {
        BOARD[row][col] = Symbol.HIT.getSymbol();
    }

    public static int getBoardSize() {
        return SIZE;
    }
}
