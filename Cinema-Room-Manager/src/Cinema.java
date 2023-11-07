package cinema;
import java.util.Scanner;
import java.util.Arrays;

public class Cinema {

    private static int rows;
    private static int seatsPerRow;
    private static final Scanner input = new Scanner(System.in);

    private static int bookedCounter = 0;
    private static int currentIncome = 0;

    private static char[][] cinema;

    public static void main(String[] args) {
        // Ask user for rows and seats
//        Scanner input = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        rows = input.nextInt();

        System.out.println("Enter the number of seats in each row:");
        seatsPerRow = input.nextInt();

        cinema = new char[rows][seatsPerRow];

        fillInitialArray(cinema);

//        printCinemaMap();

        int totalProfit = getTotalProfit();

        do {

            displayMenu();
            int choice = input.nextInt();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    printCinemaMap();
                    break;
                case 2:
                    bookTicket();
                    break;
                case 3:
                    showStatistics();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (true);
    }

    /**
     * Fills the cinema 2-D map (array) with letter 'S'
     * */
    public static void fillInitialArray(char[][] cinema) {
        for (char[] row : cinema) {
            Arrays.fill(row, 'S');
        }
    }

    /**
     * Calculates total generated profit when all seats are booked
     * Return type = int
     * */
    public static int getTotalProfit()
    {
        int totalSeats = rows * seatsPerRow;

        // Smaller room
        if (totalSeats <= 60)
        {
            int seatPrice = 10;
            return totalSeats * seatPrice;
        }
        // Larger room
        // Price Front half = 10, Back half = 8;
        int largerRoomFront = (10 * (int) Math.floor(rows / 2.0)) * seatsPerRow;
        int largerRoomBack = (8 * (int) Math.ceil(rows / 2.0)) * seatsPerRow;
        return largerRoomFront + largerRoomBack;
    }

    /**
     * Prints the cinema map
     * */
    public static void printCinemaMap()
    {
        System.out.println("\nCinema:");
        System.out.print(" "); // left padding
        // Print column numbers
        for (int col = 0; col < seatsPerRow; col++)
        {
            System.out.print(" " + (col + 1));
        }
        System.out.println();

        // Print cinema array
        for (int row = 0; row < rows; row++) {
            System.out.print(row + 1);
            for (int col = 0; col < seatsPerRow; col++) {
                System.out.print(" " + cinema[row][col]);
            }
            System.out.println();
        }

    }

    /**
     * Returns the ticket price for a given row
     * Return type = int
     * */
    public static int getTicketPrice(int row)
    {
        // front = 10, back = 8, small room = 10
        int totalSeats = rows * seatsPerRow; // class variables
        return totalSeats <= 60 ? 10 : row <= (int) Math.floor(rows / 2.0) ? 10 : 8;
    }

    // Could return boolean to indicate chosen seat is already booked
    public static void bookTicket() {

        int row;
        int seat;

        do {
            System.out.println("\nEnter a row number:");
            row = input.nextInt();

            System.out.println("Enter a seat number in that row:");
            seat = input.nextInt();

            if (row - 1 > rows - 1 || seat - 1 > seatsPerRow - 1) {
                System.out.println("\nWrong input!");
            }
            else if (cinema[row - 1][seat - 1] == 'B') {
                System.out.println("\nThat ticket has already been purchased!");
            }
            else {
                break;
            }
        } while (true);

        int ticketPrice = getTicketPrice(row);
        System.out.println("Ticket price: $" + ticketPrice);

        cinema[row - 1][seat - 1] = 'B';
        bookedCounter++;
        currentIncome += ticketPrice;
    }

    public static void showStatistics() {
        System.out.printf("Number of purchased tickets: %d\n", bookedCounter);
        double percentage = ((double) bookedCounter / (rows * seatsPerRow)) * 100;
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n", getTotalProfit());
    }

    public static void displayMenu() {
        // Print options:
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

}