package bingo;

import java.io.*;
import java.util.*;
class BingoCard {
    private String id;  //unique identifier for the bingo card
    private String[][] card; //5x5 grid to store bingo card numbers
    
    public BingoCard(String id, String[][] card) {
        this.id = id;
        this.card = card;
    }
    
    public String getId() {
        return id;
    }
    //method to display the cards in a formatted way
    public void displayCard() {
        System.out.println("Card ID: " + id);
        System.out.println("B   I   N   G   O");
        System.out.println("-----------------");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) { //if value is null print XX
                System.out.print((card[i][j] != null ? card[i][j] : "XX") + " | ");
            }
            System.out.println("\n-----------------");
        }
    }
}

//Class responsible for reading bingo cards from a file
class BingoCardReader {
    public static List<BingoCard> readCardsFromFile(String filename) {
        List<BingoCard> cards = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            //Read file line by line
            while ((line = br.readLine()) != null) {
                // Skip blank lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String id = line.trim(); // Read card ID
                String[][] card = new String[5][5];

                for (int i = 0; i < 5; i++) {
                    line = br.readLine();
                    if (line == null || line.trim().isEmpty()) {
                        System.err.println("Error: Incomplete Bingo card data for ID: " + id);
                        break;
                    }

                    // Split by comma and trim each value
                    String[] values = line.trim().split("\\s*,\\s*");

                    if (values.length != 5) {
                        System.err.println("Error: Malformed Bingo card row for ID: " + id + " -> " + Arrays.toString(values));
                        break;
                    }

                    for (int j = 0; j < 5; j++) {
                        card[i][j] = values[j].trim(); // Store values, trimming spaces
                    }
                }

                // Only add the card if all 5 rows were successfully read
                if (line != null && !line.trim().isEmpty()) {
                    cards.add(new BingoCard(id, card));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return cards;
    }
}