package battleship.helpers;


import battleship.core.Board;

public abstract class Validators {

    public static boolean isValidPlaceFormat(String input) {
        if (!input.matches("\\b[A-J]\\d\\d? [A-J]\\d\\d?\\b")) {
            System.out.println("\nError! Incorrect input format! Correct format: '[A-J][1-10] [A-J][1-10]'\n");
            return false;
        }
        return true;
    }

    public static boolean areValidPlaceCoordinates(int[][] parsedCoordinates) {
        boolean error = false;
        if (isDiagonal(parsedCoordinates)) {
            error = true;
        }
        else {
            for (int[] coordinate : parsedCoordinates) {
                if (!Validators.isValidCell(coordinate[0], coordinate[1])) {
                    error = true;
                    break;
                }
            }
        }
        if (!error) return true;

        System.out.println("\nError: Wrong ship location! Try again:\n");
        return false;
    }

    private static boolean isValidCell(int row, int col) {
        int size = Board.getBoardSize();
        return (row >= 0 && row < size) && (col >= 0 && col < size);
    }

    private static boolean isDiagonal(int[][] parsedCoordinates) {
        int[] first = parsedCoordinates[0];
        int[] second = parsedCoordinates[1];
        if (first[0] == second[0] && first[1] != second[1]) {
            return false;
        }
        else return first[1] != second[1] || first[0] == second[0];
    }

    public static boolean isValidAttackFormat(String input) {
        if (input.matches("\\b[A-J]\\d\\d?\\b")) {
            int[] parsed = Utils.parseCell(input);
            if (Validators.isValidCell(parsed[0], parsed[1])) {
                return true;
            }
        }
        System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
        return false;
    }
}
