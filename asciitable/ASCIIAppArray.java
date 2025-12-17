// Import packages for input and random number generation
import java.util.Random;
import java.util.Scanner;

// Main class
public class ASCIIAppArray {

    private Scanner scan;        // For user input
    private String[][] table;    // 2D array for ASCII strings
    private int rows, cols;      // Table dimensions
    private Random randTable;    // For random character generation

    // Constructor initializes scanner and random
    public ASCIIAppArray() {
        scan = new Scanner(System.in);
        randTable = new Random();
    }

    // Program entry point
    public static void main(String[] args) {
        ASCIIAppArray app = new ASCIIAppArray(); // Create object
        app.dimensionTable(); // Set table size
        app.menu(); // Show main menu
    }

    // Get table dimensions from user
    private void dimensionTable() {
        System.out.print("\nInput table dimension (rowxcols): ");
        String[] dimension = scan.nextLine().split("x"); // Split input by 'x'

        try {
            rows = Integer.parseInt(dimension[0].trim()); // Parse rows
            cols = Integer.parseInt(dimension[1].trim()); // Parse cols

            // Validate input
            if (rows <= 0 || cols <= 0) {
                System.out.println("\nInvalid input! Must be greater than 0.");
                dimensionTable(); // Retry
                return;
            }
        } catch (Exception e) {
            System.out.println("\nInvalid format! Use rowxcol (e.g. 3x3).");
            dimensionTable(); // Retry
            return;
        }

        generateRandomTable(); // Fill table
        printTable(); // Display table
    }

    // Generate random 3-char ASCII strings
    private void generateRandomTable() {
        table = new String[rows][cols]; // Initialize array

        for (int i = 0; i < rows; i++) {    // Loop rows
            for (int j = 0; j < cols; j++) {    // Loop cols
                StringBuilder sb = new StringBuilder(); // Build string
                for (int k = 0; k < 3; k++) {   // 3 random chars
                    char randomChar = (char) (randTable.nextInt(94) + 33); // 33 - 126 printable ASCII (32 is the space)
                    sb.append(randomChar);
                }
                table[i][j] = sb.toString();    // Store string
            }
        }
    }

    // Menu for user actions
    private void menu() {
        while (true) {
            System.out.println("\nMENU:");
            System.out.println("[1] - Search");
            System.out.println("[2] - Edit");
            System.out.println("[3] - Print");
            System.out.println("[4] - Reset");
            System.out.println("[x] - Exit");
            System.out.print("Action: ");
            String action = scan.nextLine().trim(); // Get choice

            switch (action) {
                case "1": search(); break;  // Calls the search text method
                case "2": edit(); break;    // Calls the edit cell method
                case "3": printTable(); break;  // Calls the show table method
                case "4": dimensionTable(); break;  // Calls dimension table to reset table method
                case "x":
                case "X": return;   // Exit
                default: System.out.println("\nInvalid input!");
            }
        }
    }

    // Search for string in table
    private void search() {
        System.out.print("\nEnter string to search: ");
        String searchChar = scan.nextLine(); // Search term
        int totalOccurrences = 0;   // Count matches
        StringBuilder positions = new StringBuilder();  // Match coords

        for (int i = 0; i < rows; i++) {    // Loop rows
            for (int j = 0; j < cols; j++) {    // Loop cols
                String cell = table[i][j];
                int count = countOverlapping(cell, searchChar); // Count hits

                if (count > 0) {    // Found match
                    totalOccurrences += count;
                    positions.append("[").append(i).append(",").append(j).append("] ");
                }
            }
        }

        // Display results
        if (totalOccurrences > 0) {
            System.out.println("\nOutput: " + totalOccurrences + " Occurrence/s at " + positions);
        }
        else {
            System.out.println("\nOutput: No occurrences found.");
        }
    }

    // Count overlapping matches
    private int countOverlapping(String text, String pattern) {
        int count = 0;
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            if (text.startsWith(pattern, i)) count++; // Match found
        }
        return count;
    }

    private void edit() {
        int rowEdit = 0; 
        int colEdit = 0;
        boolean cellTrue = true;

        // Loop until a valid cell position is entered
        while (cellTrue) {
            try {
                System.out.print("\nEnter cell (e.g., 3x3): ");
                String input = scan.nextLine();
                String[] editChar = input.split("x");

                // Check if the input format is correct
                if (editChar.length != 2) {
                    System.out.println("\nInvalid format! Use rowxcol (" + rows + "x" + cols + "). Try again.");
                    continue; // Ask again
                }

                // Parse row and column
                rowEdit = Integer.parseInt(editChar[0].trim());
                colEdit = Integer.parseInt(editChar[1].trim());

                // Validate range
                if (rowEdit < 0 || rowEdit >= rows || colEdit < 0 || colEdit >= cols) {
                    System.out.println("\nInvalid position! Must be between (0x0) and " + "(" + rows + "x" + cols + ")");
                    continue; // Ask again
                }

                cellTrue = false; // Valid input, exit loop

            } catch (NumberFormatException e) {
                System.out.println("\nRow and column must be numbers! Try again.");
            }
        }

        // Declare newValue outside the loop, then assign it properly
        String newValue = "";
        boolean valueTrue = true;

        // Loop until a valid 3-character value is entered
        while (valueTrue) {
            System.out.print("\nEnter new value (3 chars): ");
            newValue = scan.nextLine().trim();

            if (newValue.length() != 3) {
                System.out.println("\nInvalid! Must be exactly 3 characters. Try again.");
            } else {
                valueTrue = false;
            }
        }

        // Update table and show result
        String oldValue = table[rowEdit][colEdit];
        table[rowEdit][colEdit] = newValue;
        System.out.println("\nUpdated: " + oldValue + " → " + newValue);
    }

    // Print entire table
    private void printTable() {
        System.out.println("\n(Generated Table)");
        for (String[] row : table) {    // Loop rows
            for (String cell : row) // Loop cols
                System.out.printf("%-6s", cell);    // Align output
            System.out.println();  
        }
    }
}