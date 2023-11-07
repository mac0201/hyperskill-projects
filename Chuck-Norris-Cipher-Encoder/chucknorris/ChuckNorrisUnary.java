package chucknorris;


import java.util.Scanner;

public class ChuckNorrisUnary {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String operation;
        do {
            System.out.println("\nPlease input operation (encode/decode/exit):");
            operation = scanner.nextLine();
            if (operation.equalsIgnoreCase("encode") ||
                    operation.equalsIgnoreCase("decode") ||
                    operation.equalsIgnoreCase("exit") ) {
                String input;
                operation = operation.toLowerCase();
                switch (operation) {
                    case "encode":
                        System.out.println("Input string:");
                        input = scanner.nextLine();
                        encode(input);
                        break;
                    case "decode":
                        System.out.println("Input encoded string:");
                        input = scanner.nextLine();
                        decode(input);
                        break;
                    case "exit":
                        System.out.println("Bye!");
                        return;
                }
            } else {
                System.out.printf("There is no '%s' operation\n", operation);
            }
        } while (true);
    }

    private void encode(String input) {
        String encodedInput = "";
        String binaryForm = "";

        // Traverse through characters of input, convert its decimal value to binary and add to String
        for (int i = 0, len = input.length(); i < len; i++) {
            binaryForm += charToBinary(input.charAt(i));
        }

        // Split the binary representation into groups of 1's and 0's
        String[] blocks = binaryForm.split("(?<=0)(?=1)|(?<=1)(?=0)");

        for (String block : blocks) {
            encodedInput += processBlock(block) + " ";
        }

        System.out.printf("Encoded string:\n%s\n", encodedInput);
    }

    private void decode(String input) {
        String decodedInput = "";
        String[] blocks = input.split(" ");
        String binary = "";

        // Check if each character is '0' or ' '
        if (!input.matches("^[0 ]*$")) {
            printError();
            return;
        }

        // Check if number of blocks is even
        if (input.split(" ").length % 2 != 0) {
            printError();
            return;
        }

        // Traverse through the blocks array two blocks at a time and generate bits according to specification
        for (int i = 0, len = blocks.length; i < len; i=i+2) {
            // If first block does not begin with '0' or '00' print error and exit
            if (!blocks[i].matches("^([0]{1}|[0]{2})$"))
            {
                printError();
                return;
            }
            binary += String.format("%c", blocks[i].equals("0") ? '1' : '0').repeat(blocks[i + 1].length());
        }

        // Check if length of binary string is multiple of 7
        if (binary.length() % 7 != 0) {
            printError();
            return;
        }

        // Split the binary string into groups of 7 bits
        String[] asciiBlocks = binary.split("(?<=\\G.......)");

        // Convert each group into individual character
        for (String asciiBlock : asciiBlocks) {
            decodedInput += binaryToChar(asciiBlock);
        }
        System.out.printf("Decoded string:\n%s\n", decodedInput);
    }

    private void printError() {
        System.out.println("Encoded string is not valid.");
    }

    private String charToBinary(char c) {
        return String.format("%7s", Integer.toBinaryString(c)).replace(" ", "0");
    }
    private char binaryToChar(String binaryString) {
        return (char) Integer.parseInt(binaryString, 2);
    }

    /**
     * Processes a block of 0's or 1's and returns encrypted String for that block
     *      A sequence of N times 0 is encoded as 00 followed by N times 0
     *      A sequence of N times 1 is encoded as 0 followed by N times 0
     * Example: input: 1111    output: 0 0000
     * */
    private String processBlock(String block) {
        int length = block.length();
        String prefix = block.charAt(0) == '1' ? "0" : "00";
        String suffix = "0".repeat(length);
        return prefix + " " + suffix;
    }
}
