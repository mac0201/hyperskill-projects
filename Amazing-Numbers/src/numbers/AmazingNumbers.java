package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AmazingNumbers {

    // LIST OF AVAILABLE PROPERTIES
    private final List<String> availableProperties = NumberProperties.getAllAvailablePropertyNames();
    private final int totalAvailableProperties = availableProperties.size();
    final int MAX_NUMBER_OF_ARGS = 2 + totalAvailableProperties;

    // PROPERTIES OF CURRENT NUMBER
    private boolean even, odd, buzz, duck, palindromic, gapful, spy, square, sunny, jumping, happy, sad = false;
    ArrayList<String> propertiesOfCurrentNumber = new ArrayList<>();



    private void printInstructions() {
        System.out.println("""
                              
        Supported requests:
        - enter a natural number to know its properties;
        - enter two natural numbers to obtain the properties of the list:
              * the first parameter represents a starting number;
              * the second parameter shows how many consecutive numbers are to be printed;
        - two natural numbers and properties to search for;
        - a property preceded by minus must not be present in numbers;
        - separate the parameters with one space;
        - enter 0 to exit.
                """);
    }

    public void start() {
        System.out.println("Welcome to Amazing Numbers!");
        printInstructions();

        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.print("Enter a request: ");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                printInstructions();
                continue;
            }

            // Put the arguments from input inside an ArrayList
            ArrayList<String> args = new ArrayList<>(Arrays.asList(input.split(" ")));

            // Make sure number of passed arguments does not exceed max
            if (args.size() > MAX_NUMBER_OF_ARGS) {
                System.out.println("\nToo many arguments.\n");
                continue;
            }

            System.out.println();

            List<String> propertiesToSearch = null;
            long number;
            int listSize = 1;

            try {
                number = Long.parseLong(args.get(0));

                // Exit if 0
                if (number == 0) {
                    System.out.println("Goodbye!");
                    return;
                }

                // Restart if number not natural
                if (number < 0) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                    continue;
                }

                if (args.size() >= 2){
                    listSize = Integer.parseInt(args.get(1));

                    propertiesToSearch = args.subList(2, args.size());

                    // convert all property arguments to lowercase
                    propertiesToSearch.replaceAll(String::toLowerCase);

                    // are there invalid properties?
                    if (!NumberProperties.validProperties(propertiesToSearch)) {
                        continue;
                    }

                    // are there mutually exclusive properties?
                    if (NumberProperties.mutuallyExclusiveProperties(propertiesToSearch)) {
                        continue;
                    }
                }

                // restart if second parameter not natural number
                if (listSize <= 0) {
                    System.out.println("The second parameter should be a natural number.\n");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("The first parameter should be a natural number or zero.\n");
                continue;
            }

            // Run correct functions depending on number of arguments
            if (args.size() == 1) {
                analyse(number);
                printCurrentNumberProperties(number, false);
            }
            else if (args.size() == 2) {
                analyse(number, listSize);
            }
            else { // more than 2 arguments, but <= to (2 + number of available properties)
                analyse(number, listSize, propertiesToSearch);
            }
            System.out.println();
        }
    }

    private void printCurrentNumberProperties(long number, boolean isList) {
        if (isList) {
            System.out.printf("%,12d is ", number);
            System.out.print(String.join(", ", propertiesOfCurrentNumber));
            System.out.println();
        }
        else {
            System.out.printf("Properties of %,d\n", number);
            System.out.printf("%12s: %b\n", "buzz", buzz);
            System.out.printf("%12s: %b\n", "duck", duck);
            System.out.printf("%12s: %b\n", "palindromic", palindromic);
            System.out.printf("%12s: %b\n", "gapful", gapful);
            System.out.printf("%12s: %b\n", "spy", spy);
            System.out.printf("%12s: %b\n", "even", even);
            System.out.printf("%12s: %b\n", "odd", odd);
            System.out.printf("%12s: %b\n", "square", square);
            System.out.printf("%12s: %b\n", "sunny", sunny);
            System.out.printf("%12s: %b\n", "jumping", jumping);
            System.out.printf("%12s: %b\n", "happy", happy);
            System.out.printf("%12s: %b\n", "sad", sad);
        }
    }

    // Analyse a single number and update class properties
    private void analyse(long number) {
        String numberAsString = String.valueOf(number);
        boolean isDivisible = number % 7 == 0;
        boolean endsWith7 = number % 10 == 7;
        even = number % 2 == 0;
        odd = !even;
        buzz = isDivisible || endsWith7;
        duck = numberAsString.contains("0");
        palindromic = isPalindrome(numberAsString);
        gapful = isGapful(numberAsString);
        spy = isSpy(number);
        square = isSquare(number);
        sunny = isSunny(number);
        jumping = isJumping(number);
        happy = isHappy(number);
        sad = !happy;
        updatePropertiesOfCurrentNumber();
    }

    // Analyse each number from number through number + range
    private void analyse(long number, int range) {
        for (int i = 0; i < range; i++) {
            analyse(number + i);
            printCurrentNumberProperties(number + i, true);
        }
    }

    // Analyse each number, if number has target properties print that number
    // Print numbers until counter reaches listSize
    private void analyse(long number, int listSize, List<String> targetProperties) {
        int sizeCounter = 0;
        int matchCount = 0;

        while (sizeCounter != listSize) {
            analyse(number);
            for (String target : targetProperties) {
                if (target.charAt(0) == '-') {
                    if (!propertiesOfCurrentNumber.contains(target.substring(1))) {
                        matchCount++;
                    }
                }
                else {
                    if (propertiesOfCurrentNumber.contains(target)) {
                        matchCount++;
                    }
                }
            }

            if (matchCount == targetProperties.size()) {
                printCurrentNumberProperties(number, true);
                sizeCounter++;
            }
            matchCount = 0;
            number++;
        }
    }

    // Utility method that cleans properties list of previous number and adds properties to the list for current number
    private void updatePropertiesOfCurrentNumber() {
        propertiesOfCurrentNumber.clear();
        propertiesOfCurrentNumber.add( even ? "even" : "odd" );
        if (buzz) propertiesOfCurrentNumber.add("buzz");
        if (duck) propertiesOfCurrentNumber.add("duck");
        if (palindromic) propertiesOfCurrentNumber.add("palindromic");
        if (gapful) propertiesOfCurrentNumber.add("gapful");
        if (spy) propertiesOfCurrentNumber.add("spy");
        if (square) propertiesOfCurrentNumber.add("square");
        if (sunny) propertiesOfCurrentNumber.add("sunny");
        if (jumping) propertiesOfCurrentNumber.add("jumping");
        propertiesOfCurrentNumber.add( happy ? "happy" : "sad" );
    }

    private boolean isPalindrome(String numAsString) {
        int len = numAsString.length();
        // create loop with two pointers, one going from 0 to half, other from last to half
        for (int left = 0, right = len - 1;
             left < len / 2 && right >= len / 2;
             left++, right--) {
            // if characters at both indices are not same, return false
            if (numAsString.charAt(left) != numAsString.charAt(right)) {
                return false;
            }
        }
        return true;
    }

    private boolean isGapful(String numAsString) {
        int len = numAsString.length();
        if (len < 3) {
            return false;
        }
        int concat = Integer.parseInt("" + numAsString.charAt(0) + numAsString.charAt(len - 1));
        return Long.parseLong(numAsString) % concat == 0;
    }

    private boolean isSpy(long number) {
        long product = 1;
        long sum = 0;
        while (number != 0) {
            long last = number % 10;
            sum += last;
            product = product * last;
            number = number / 10;
        }
        return product == sum;
    }

    private boolean isSquare(long number) {
        long sqrt = (long) Math.sqrt(number);
        return sqrt * sqrt == number;
    }

    private boolean isSunny(long number) {
        return isSquare(number + 1);
    }

    private boolean isJumping(long number) {
        if (number < 10) { return true; }
        long last = -1;
        long current;
        while (number != 0) {
            current = number % 10;
            if (last != -1) {
                if ((current != last + 1) && (current != last - 1)) {
                    return false;
                }
            }
            last = current;
            number = number / 10;
        }
        return true;
    }

    private boolean isHappy(long number) {
        long original = number;
        int trigger = 0;

        while (true) {
            if (number == 1) { return true; }
            // we are in infinite loop IF:
            // this is not the first loop and current number is equal to original AND we have looped over 1000 times
            if ((trigger > 1000) || ((trigger > 0 && number == original))) {
                return false;
            }
            if (number < 10) {
                number = number * number;
            } else {
                long sum = 0;
                while (number != 0) {
                    long current = number % 10;
                    sum += current * current;
                    number /= 10;
                }
                number = sum;
                sum = 0;
                trigger++;
            }
        }
    }
}
