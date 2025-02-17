package bingo;

public class BingoCard {
    private String cardName;
    private String[][] grid;
    private boolean[][] marked; // Tracks marked positions

    public BingoCard(String cardName, String[][] grid) {
        this.cardName = cardName;
        this.grid = grid;
        this.marked = new boolean[5][5]; // Initialize marking grid
        
        
    }

   
    // Method to mark a number if found
    public boolean markNumber(String number) {
        boolean found = false; // Flag to check if number is found
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j].equals(number)) {
                    System.out.println("Marking: " + number + " at [" + i + "][" + j + "]");
                    grid[i][j] = "XX"; // Change the number to "XX" in the grid
                    marked[i][j] = true; // Mark the number
                    found = true;
                }
            }
        }
        return found; // Return whether number was found or not
    }

    // Method to check if the card has a Bingo
    public boolean hasBingo() {
        // Check rows
        for (int i = 0; i < 5; i++) {
            if (isComplete(marked[i])) return true;
        }

        // Check columns
        for (int j = 0; j < 5; j++) {
            boolean columnComplete = true;
            for (int i = 0; i < 5; i++) {
                if (!marked[i][j]) {
                    columnComplete = false;
                    break;
                }
            }
            if (columnComplete) return true;
        }

        // Check diagonals
        boolean diagonal1 = true;
        boolean diagonal2 = true;
        for (int i = 0; i < 5; i++) {
            if (!marked[i][i]) diagonal1 = false;
            if (!marked[i][4 - i]) diagonal2 = false;
        }

        return diagonal1 || diagonal2;
    }

    // Helper method to check if a row is completely marked
    private boolean isComplete(boolean[] row) {
        for (boolean cell : row) {
            if (!cell) return false;
        }
        return true;
    }

    // Display the Bingo card
    public void displayCard() {
        System.out.println("Card: " + cardName);
        System.out.println(" B   I   N   G   O ");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print((grid[i][j] != null ? grid[i][j] : "XX") + " | ");
            }
            System.out.println();
        }
    }

    public String getCardName() {
        return cardName;
    }
}

