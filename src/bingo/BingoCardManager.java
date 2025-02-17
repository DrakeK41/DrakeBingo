package bingo;

import java.io.*;
import java.util.*;

public class BingoCardManager {
    private List<BingoCard> bingoCards;
    
    public BingoCardManager() {
        this.bingoCards = new ArrayList<>();
    }
    
    // Loads bingo cards from a file
    public List<BingoCard> loadCardsFromFile(String filename) {
        List<BingoCard> bingoCards = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }
                
                // The first line for each card is the card name
                String cardName = line;
                String[][] grid = new String[5][5];
    
                // Read the next 5 lines for the grid
                for (int i = 0; i < 5; i++) {
                    line = reader.readLine();
                    if (line == null) {
                        System.err.println("Insufficient data for Bingo card. Expected 5 rows.");
                        return bingoCards; // Early return or handle as needed
                    }
                    
                    // Split the line into numbers
                    String[] numbers = line.split(",");
                    if (numbers.length != 5) {
                        System.err.println("Invalid row length. Expected 5 numbers in each row.");
                        return bingoCards; // Early return or handle as needed
                    }
                    
                    // Fill the grid
                    for (int j = 0; j < 5; j++) {
                        grid[i][j] = numbers[j].trim();
                    }
                }
                // Add the created Bingo card to the list
                bingoCards.add(new BingoCard(cardName, grid));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return bingoCards;
    }
    public List<BingoCard> getBingoCards() {
        return bingoCards;
    }
}
