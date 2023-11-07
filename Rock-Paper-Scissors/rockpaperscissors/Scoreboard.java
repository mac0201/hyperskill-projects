package rockpaperscissors;

import java.io.*;
import java.util.Scanner;

public class Scoreboard {
    private final File ratingFile;

    /**
     * Default constructor - initialises new File() used to read/write player scores
     * The file 'rating.txt' is created in current working directory
     * */
    public Scoreboard() {
//        String ratingFilepath = "./Rock-Paper-Scissors (Java)/task/src/rockpaperscissors/rating.txt";
        String ratingFilepath = "./rating.txt"; // to pass tests
        this.ratingFile = new File(ratingFilepath);
    }

    /**
     * Reads the rating file and checks whether argument name exists in the file, returning the score
     * @param name player's name
     * @return int player's score if exists, else 0
     * */
    int getPlayerScore(String name) {
        try (Scanner scanner = new Scanner(ratingFile)) {
            while (scanner.hasNext()) {
                String current = scanner.next();
                if (current.equals(name)) {
                    return scanner.nextInt();
                }
//                scanner.next();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Could not open 'rating.txt'; setting score to 0.");
        }
        return 0;
    }

    /*
    * Functions for later:
    *   setting/updating player score using Writer
    *   get filepath
    *   set filepath
    *   print info about file (if just created, exists, can write, etc)
    * */
}
