package rockpaperscissors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {

    private final Computer computer;
    private final Scoreboard scoreboard;
    private ArrayList<String> chosenShapes;
    private boolean defaultMode = true;

    private final int WIN = 100;
    private final int DRAW = 50;
    private int scoreP1;

    public Game() {
        this.computer = new Computer();
        this.scoreboard = new Scoreboard();
        this.scoreP1 = 0;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String player = scanner.nextLine();

        System.out.printf("Hello, %s\n", player);

        scoreP1 = scoreboard.getPlayerScore(player);

        String shapesOptions = scanner.nextLine();

        if (shapesOptions.isEmpty()) {
            chosenShapes = DefaultShape.getAllDefaultShapeNames();
        }
        else {
            defaultMode = false;
            chosenShapes = new ArrayList<>(Arrays.asList(
                    shapesOptions.replace(" ", "").split(",")));
        }

        System.out.println("Okay, let's start!");

        while (true) {
            String playerChoice = scanner.nextLine();

            if (playerChoice.equals("!exit")) {
                System.out.println("Bye!");
//                scoreboard.setPlayerScore(player, scoreP1);
                return;
            }

            if (playerChoice.equals("!rating")) {
                printRating(scoreP1);
                continue;
            }

            /*
            * Input is invalid if:
            *   contains characters other than letters
            *   is not a valid default shape, when game mode is default
            *   is not contained in shapes provided by user, when game mode is not default
            * */
            if (!playerChoice.matches("(^[a-z]*$)") ||
                    (!DefaultShape.isValidDefaultShape(playerChoice) && defaultMode) ||
                    (!chosenShapes.contains(playerChoice) && !defaultMode)) {
                System.out.println("Invalid input");
                continue;
            }

            if (defaultMode) {
                String computerChoice = computer.generateRandomShape();
                analyseDefault(playerChoice, computerChoice);
            }
            else {
                // Generate a random index based on number of provided shapes and analyse
                String computerChoice = chosenShapes.get(
                        computer.generateRandomIndex(chosenShapes.size())
                );
                analyse(playerChoice, computerChoice);
            }
        }
    }

    /**
     * Analyses game with custom shapes and prints result
     * @param player player's symbol
     * @param computer computer's symbol
     */
    private void analyse(String player, String computer) {
        int pIndex = chosenShapes.indexOf(player);
        int cIndex = chosenShapes.indexOf(computer);

        if (pIndex == cIndex) {
            System.out.printf("There is a draw (%s)\n", player);
            updateScoreOne(DRAW);
            return;
        }

        // If the player's winning symbols contains computer's choice, then player wins
        if (getWinningIndices(pIndex).contains(cIndex)) {
            System.out.printf("Well done. The computer chose %s and failed\n", computer);
            updateScoreOne(WIN);
        }
        else {
            System.out.println("Sorry, but the computer chose " + computer);
        }
    }

    /**
    * Analyses game with default shapes and prints result
    * */
    private void analyseDefault(String player, String computer) {
        if (player.equals(computer)) {
            System.out.printf("There is a draw (%s)\n", player);
            updateScoreOne(DRAW);
        }
        else if (DefaultShape.getCounter(player).equals(computer)) {
            System.out.println("Sorry, but the computer chose " + computer);
        }
        else {
            System.out.printf("Well done. The computer chose %s and failed\n", computer);
            updateScoreOne(WIN);
        }
    }

    private void updateScoreOne(int n) {
        this.scoreP1 += n;
    }

    private void printRating(int score) {
        System.out.printf("Your rating: %d\n", score);
    }

    /**
     * This function determines, based on player's choice, the indices (symbols) which the
     *  player's choice would defeat and saves these indices is ArrayList
     * @param chosenIndex location of player's choice in chosenShapes array
     * @return ArrayList of Integers, containing the winning indices
     * */
    public ArrayList<Integer> getWinningIndices(int chosenIndex) {
        // return indices that will defeat current index
        int len = chosenShapes.size();
        int half = len / 2;

        ArrayList<Integer> wins = new ArrayList<>();

        /*
        * winning index (win) = chosen index - 1
        * Count backwards from (win) to (win - half of size of array)
        * If current (win) is negative, add to wins array index at (len + -win)
        * If current (win) is not negative, add to wins array
        * variable 'count' will make sure we do not add more than half of the indices
        * Example:
        *       [0, 1, 2, 3, 4, 5, 6]    len: 7, half: 3
        *       chosen index: 1
        *       loop from 0 to (0 - 3 exclusive)
        *           0 is not negative, add 0 to wins
        *          -1 is negative, add (7 + -1) 6 to wins
        *          -2 is negative, add (7 + -2) 5 to wins
        *
        *       once loop is done, we have correct winning indices [0, 5, 6]
        *       so, if array was [a, b, c, d, e, f, g], then 'b' wins against [a, f, g]
        * */
        for (int count = 0, win = chosenIndex - 1; count < half; win--, count++) {
            if (win < 0) {
                wins.add(len + win);
            }
            else {
                wins.add(win);
            }
        }
        return wins;
    }

    // First version of the getWinningIndices method. A lot of unnecessary code
    @Deprecated
    public ArrayList<ArrayList<Integer>> OldGetWinningIndices(int chosenIndex) {
        int len = chosenShapes.size();
        int half = len / 2;
        ArrayList<ArrayList<Integer>> indices = new ArrayList<>();
        ArrayList<Integer> loses = new ArrayList<>();
        ArrayList<Integer> wins = new ArrayList<>();
        int diffRight = 0;
        int diffLeft = 0;
        if (chosenIndex + half > len - 1) {
            // lose difference
            diffRight = (chosenIndex + half) - len + 1;
        }
        if (chosenIndex - half < 0) {
            diffLeft = half - (chosenIndex);
        }
        for (int c = 0, lose = chosenIndex + 1; c < half && lose < len; lose++, c++) {
            loses.add(lose);
        }
        for (int c = 0, win = chosenIndex - 1; c < half && win >= 0; win--, c++) {
            wins.add(win);
        }
        if (diffRight > 0) {
            for (int j = 0; j < diffRight; j++) {
                loses.add(j);
            }
        }
        if (diffLeft > 0) {
            for (int j = len - 1; j >= len - diffLeft; j--) {
                wins.add(j);
            }
        }
        indices.add(loses);
        indices.add(wins);
        return indices;
    }
}
