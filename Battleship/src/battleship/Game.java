package battleship;

import battleship.core.Player;
import battleship.helpers.Utils;
import battleship.helpers.Validators;

import java.util.Scanner;

public class Game {
    private final Scanner scanner;
    private final Player[] players;
    private int currentPlayer;

    public Game() {
        players = new Player[] { new Player(), new Player() };
        currentPlayer = 0;
        scanner = new Scanner(System.in);
    }

    /**
     * Starts the game - fills board and asks for attack coordinates
     */
    public void play() {
        System.out.printf("Player %d, place your ships on the game field\n", currentPlayer + 1);
//        players[currentPlayer].testingBoard();
        players[currentPlayer].fillPlayerBoard(scanner);

        promptForEnter();

        System.out.printf("Player %d, place your ships on the game field\n", currentPlayer + 1);
//        players[currentPlayer].testingBoard();
        players[currentPlayer].fillPlayerBoard(scanner);

        promptForEnter();

        while (true) {
            printBoardsParallel(players[currentPlayer], players[getOpponent()]);

            System.out.printf("\nPlayer %d, it's your turn.\n", currentPlayer + 1);

            System.out.println();

            String input = scanner.nextLine().toUpperCase();

            if (!Validators.isValidAttackFormat(input)) {
                continue;
            }

            int[] attackCell = Utils.parseCell(input);

            if (!players[currentPlayer].attack(attackCell[0], attackCell[1], players[getOpponent()])) { // if attack failed, i.e. missed target
                System.out.println("\nYou missed. Try again:\n");
            }
            else {
                if (players[getOpponent()].getShipFromCell(attackCell[0], attackCell[1]).isDestroyed()) {
                    players[getOpponent()].getShips().remove(
                            players[getOpponent()].getShipFromCell(attackCell[0], attackCell[1]));
                    if (players[getOpponent()].getShipsRemaining() == 0) {
                        System.out.println("\nYou sank the last ship. You won. Congratulations!");
                        return;
                    }
                    System.out.println("\nYou sank a ship! Specify a new target:\n");
                }
                else {
                    System.out.println("\nYou hit a ship! Try again:\n");
                }
            }

            promptForEnter();

        }
    }

    private void printBoardsParallel(Player current, Player opponent) {
        opponent.getBoard().printBoard(true);
        System.out.println("---------------------");
        current.getBoard().printBoard(false);
    }

    private void switchCurrentPlayer() {
        currentPlayer = currentPlayer == 0 ? 1 : 0;
    }

    private int getOpponent() {
        return currentPlayer == 0 ? 1 : 0;
    }

    private void promptForEnter() {
        System.out.println("\nPress Enter and pass the move to another player");
        while (!scanner.nextLine().isEmpty()) {
            System.out.println("\nPress Enter and pass the move to another player");
        }
        switchCurrentPlayer();
    }
}