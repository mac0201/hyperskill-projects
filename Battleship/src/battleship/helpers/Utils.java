package battleship.helpers;

public abstract class Utils {
    /**
     * Translates coordinate provided by user (e.g. A5) into appropriate row and column number within game grid
     * e.g., B6 -> {1, 5},  J10 -> {9, 9}
     * @param cell string representing cell
     * @return int array containing row and column number
     * */
    public static int[] parseCell(String cell) {
        int row = cell.charAt(0) - 65; // A = 65, so subtract to get correct row
        int column = Integer.parseInt(cell.substring(1)) - 1; // - 1, because columns start from 1 to 10
        return new int[] { row, column };
    }

    @Deprecated
    static String getDirection(int[][] parsedCoordinates) {
        int[] first = parsedCoordinates[0];
        int[] second = parsedCoordinates[1];
        return (first[0] == second[0] && first[1] != second[1]) ? "row" : "column";
    }
}
