package battleship.core;

import battleship.helpers.Utils;
import battleship.helpers.Validators;
import battleship.ship.Ship;
import battleship.ship.ShipCore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Player {

    private final Board BOARD;
    private final ArrayList<Ship> SHIPS;
    private int shipsRemaining;

    public Player() {
        BOARD = new Board();
        shipsRemaining = ShipCore.values().length;
        // create arraylist of ships with capacity of shipsRemaining
        SHIPS = new ArrayList<>(shipsRemaining);
    }

    /**
     * Prompts player for coordinates in order to place all available Ship objects on game board
     */
    public void fillPlayerBoard(Scanner scanner) {
        BOARD.printBoard(false);
        ShipCore[] shipsAvailable = ShipCore.values();

        int shipsPlaced = 0;

        boolean errorOccurred = false;

        while (shipsPlaced != shipsAvailable.length) {

            ShipCore currentShipCoreInstance = shipsAvailable[shipsPlaced];

            if (!errorOccurred) {
                System.out.printf("\nEnter the coordinates of the %s (%d cells):\n",
                        currentShipCoreInstance.getName(), currentShipCoreInstance.getSize());
            }
            else {
                errorOccurred = false; // reset
            }

            System.out.println();
            String input = scanner.nextLine().toUpperCase();
            System.out.println();

            if (!Validators.isValidPlaceFormat(input)) {
                errorOccurred = true;
                continue;
            }

            String[] coordinates = input.split(" ");

            /*
             *   if coordinates are B3 D3, then coordinates[0] = B3, coordinates[1] = D3
             *   first index of parsedCoordinates will contain parsed cell B3 -> {1, 2}
             *   second index will contain parsed cell D3 -> {3, 2}
             *   parsedCoordinates = {{1,2}, {3,2}}
             * */
            int[][] parsedCoordinates = new int[][] {
                    Utils.parseCell(coordinates[0]), Utils.parseCell(coordinates[1])
            };

            // If coordinates are the same, or invalid
            if (!Validators.areValidPlaceCoordinates(parsedCoordinates)) { // || coordinates[0].equals(coordinates[1])
                errorOccurred = true;
                continue;
            }

            // make sure cellOne is lower than cellTwo before continuing, i.e.  A3 A1 -> A1 A3
            int sumOne = Arrays.stream(parsedCoordinates[0]).sum();
            int sumTwo = Arrays.stream(parsedCoordinates[1]).sum();

            int[] startCell = (sumOne < sumTwo) ? parsedCoordinates[0] : parsedCoordinates[1];
            int[] endCell = (sumOne < sumTwo) ? parsedCoordinates[1] : parsedCoordinates[0];

            //? Everything valid, place ship.
            if (!BOARD.placeShip(startCell, endCell, currentShipCoreInstance, SHIPS)) {
                errorOccurred = true;
                continue;
            }

            BOARD.printBoard(false);
            shipsPlaced++;
        }
    }

//    /**
//     * Attacks given cell on the board, placing appropriate symbol on MISS/HIT.
//     * @param row to be attacked
//     * @param col column to be attacked
//
//     * @return true if attack is successful, else false
//     */
    public boolean attack(int row, int col, Player opponent) {
        Ship hitShip = opponent.getShipFromCell(row, col);  // get ship that is on attack cell
        if (hitShip != null) {
            if (!hitShip.getDamagedCells().contains(row + "," + col)) { // if attack cell not in damaged cells
                opponent.getBoard().placeHitSymbol(row, col);
//                opponent.getBoard().printBoard(true);
                hitShip.updateDamagedCells(row, col);
                if (!hitShip.takeDamage()) {    //! the ship has no health left
                    opponent.decreaseShipsRemaining();
                }
            }
            return true;

        } else { // missed
            opponent.getBoard().placeMissSymbol(row, col);
//            opponent.getBoard().printBoard(true);
            return false;
        }
    }

    public Ship getShipFromCell(int row, int col) {
        String x = row + "," + col;
        for (Ship ship : SHIPS) {
            if (ship.getCellsTaken().contains(x)) {
                return ship;
            }
        }
        return null;
    }

    public ArrayList<Ship> getShips() {
        return SHIPS;
    }

    public Board getBoard() {
        return BOARD;
    }

    public int getShipsRemaining() {
        return shipsRemaining;
    }

    public void decreaseShipsRemaining() {
        if (shipsRemaining > 0) shipsRemaining -= 1;
    }

    /*
     * Fills the game board with test values
     * */
    public void testingBoard() {
        BOARD.placeShip(new int[]{0, 0}, new int[]{0, 4}, ShipCore.AIRCRAFT_CARRIER, SHIPS);

        BOARD.placeShip(new int[]{5, 2}, new int[]{5, 5}, ShipCore.BATTLESHIP, SHIPS);

        BOARD.placeShip(new int[]{8, 2}, new int[]{8, 4}, ShipCore.SUBMARINE, SHIPS);

        BOARD.placeShip(new int[]{1, 8}, new int[]{3, 8}, ShipCore.CRUISER, SHIPS);

        BOARD.placeShip(new int[]{7, 9}, new int[]{8, 9}, ShipCore.DESTROYER, SHIPS);

        System.out.println("initialised testing board");

        BOARD.printBoard(false);
    }

}
