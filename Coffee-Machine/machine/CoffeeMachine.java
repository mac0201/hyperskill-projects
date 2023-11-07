package machine;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CoffeeMachine {
    private int currentWaterSupply;
    private int currentMilkSupply;
    private int currentCoffeeSupply;
    private int currentCupSupply;
    private int totalCash;

    private final String[] validActions = { "buy", "take", "fill", "remaining", "exit"};

    public CoffeeMachine() {
        this.currentWaterSupply = 400;
        this.currentMilkSupply = 540;
        this.currentCoffeeSupply = 120;
        this.currentCupSupply = 9;
        this.totalCash = 550;
    }

    void startMachine() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Get a list of valid actions and print
            String[] actions = getValidActions();
            System.out.printf("\nWrite action (%s):\n", String.join(", ", actions));

            String input = scanner.nextLine();
            System.out.println();

            // Check if action provided is valid
            if (!Arrays.asList(actions).contains(input.toLowerCase())) {
                System.out.println("Invalid action - " + input);
                continue;
            }

            switch (input) {
                case "buy" -> {
                    String temp;
                    boolean goBack = false;

                    while (true) {
                        printMenu();
                        temp = scanner.nextLine();

                        if (temp.equals("back")) {
                            goBack = true;
                            break;
                        }
                        if (!temp.matches("^\\d$")) {   // If input is not single digit
                            System.out.println("\nInvalid choice.\n");
                            continue;
                        }
                        else {  // If out of bounds
                            if (Coffee.getInstanceCount() < Integer.parseInt(temp)) {
                                System.out.println("\nInvalid choice.\n");
                                continue;
                            }
                        }
                        break;
                    }
                    if (goBack) { continue; }

                    int choice = Integer.parseInt(temp);

                    Coffee coffee = Coffee.getCoffeeInstanceByOrdinal(choice - 1);

                    if (coffee == null) {
                        System.out.println("Problem getting coffee details.");
                        continue;
                    }
                    buy(coffee);
                }
                case "fill" -> {
                    try {
                        System.out.println("Write how many ml of water you want to add: ");
                        int water  = scanner.nextInt();

                        System.out.println("Write how many ml of milk you want to add:");
                        int milk = scanner.nextInt();

                        System.out.println("Write how many grams of coffee beans you want to add:");
                        int coffee = scanner.nextInt();

                        System.out.println("Write how many disposable cups you want to add:");
                        int cups = scanner.nextInt();

                        scanner.nextLine();

                        if (water < 0 || milk < 0 || coffee < 0 || cups < 0) {
                            System.out.println("Negative numbers not allowed.");
                            continue;
                        }

                        fill(water, milk, coffee, cups);

                    } catch (InputMismatchException e) {
                        System.out.println("Invalid argument.");
                        scanner.nextLine();
                    }
                }
                case "take" -> take();
                case "remaining" -> printRemainingSupplies();
                case "exit" -> { return; }
                default -> { System.out.println("Something went wrong."); return; }
            }
        }
    }

    protected void printRemainingSupplies() {
        System.out.println("The coffee machine has:");
        System.out.printf(
                """
                        %d ml of water
                        %d ml of milk
                        %d g of coffee beans
                        %d disposable cups
                        $%d of money
                        """,
                getCurrentWaterSupply(), getCurrentMilkSupply(), getCurrentCoffeeSupply(), getCurrentCupSupply(), getTotalCash()
        );
    }

    void printMenu() {
        String[] menu = Coffee.getAllNames();

        for (int i = 0; i < menu.length; i++) {
            menu[i] = (i + 1) + " - " + menu[i];
        }
        System.out.print("What do you want to buy? ");
        System.out.print(String.join(", ", menu) + "\n");
    }

    void take() {
        System.out.println("I gave you $" + totalCash);
        totalCash = 0;
    }

    void fill(int water, int milk, int coffee, int cups) {
        setCurrentWaterSupply(water);
        setCurrentMilkSupply(milk);
        setCurrentCoffeeSupply(coffee);
        setCurrentCupSupply(cups);
    }

    void buy(Coffee coffee) {
        // check supplies
        if (getCurrentWaterSupply() < coffee.getWaterRequired()) {
            System.out.println("Sorry, not enough water!");
            return;
        }
        if (getCurrentMilkSupply() < coffee.getMilkRequired()) {
            System.out.println("Sorry, not enough milk!");
            return;
        }
        if (getCurrentCoffeeSupply() < coffee.getCoffeeRequired()) {
            System.out.println("Sorry, not enough coffee beans!");
            return;
        }
        if (getCurrentCupSupply() < 1) {
            System.out.println("Sorry, not enough cups!");
            return;
        }

        System.out.println("I have enough resources, making you a coffee!");

        setCurrentWaterSupply(-coffee.getWaterRequired());
        setCurrentMilkSupply(-coffee.getMilkRequired());
        setCurrentCoffeeSupply(-coffee.getCoffeeRequired());
        setCurrentCupSupply(-1);
        setTotalCash(coffee.getCost());
    }

    public String[] getValidActions() {
        return validActions;
    }

    public int getCurrentWaterSupply() {
        return currentWaterSupply;
    }

    public void setCurrentWaterSupply(int additionalSupply) {
        currentWaterSupply += additionalSupply;
    }

    public int getCurrentMilkSupply() {
        return currentMilkSupply;
    }

    public void setCurrentMilkSupply(int additionalSupply) {
        currentMilkSupply += additionalSupply;
    }

    public int getCurrentCoffeeSupply() {
        return currentCoffeeSupply;
    }

    public void setCurrentCoffeeSupply(int additionalSupply) {
        currentCoffeeSupply += additionalSupply;
    }

    public int getCurrentCupSupply() {
        return currentCupSupply;
    }

    public void setCurrentCupSupply(int additionalSupply) {
        currentCupSupply += additionalSupply;
    }

    public int getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(int profit) {
        this.totalCash += profit;
    }
}