package bingo;

import java.util.*;

public class BingoMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BingoCaller caller = new BingoCaller();
        
        System.out.println("Welcome to Bingo!");
        System.out.println("How many cards would you like to play with? (1-4)");
        int numCards = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        List<BingoCard> playerCards = new ArrayList<>();
        BingoCardManager cardManager = new BingoCardManager();
        
        // Load bingo cards and randomly select for the player
        List<BingoCard> allCards = (List<BingoCard>) cardManager.loadCardsFromFile("BingoCards.txt");
        Collections.shuffle(allCards);
        for (int i = 0; i < numCards && i < allCards.size(); i++) {
            playerCards.add(allCards.get(i));
        }
        
        System.out.println("Press Enter to call the next number or type 'exit' to quit.");
        
        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Game Over. Here are the called numbers:");
                System.out.println(caller.getCalledNumbers());
                break;
            }
            
            String calledNumber = caller.callNextNumber();
            System.out.println("Called Number: " + calledNumber);
             // Extract only the number part from the called number
             String numberOnly = caller.getNumberPart(calledNumber);
             System.out.println("Marking Number: " + numberOnly);  // Debugging output
            
            // Process each card to mark called number
            for (BingoCard card : playerCards) {
                
                card.markNumber(numberOnly);
                card.displayCard();
                
                if (card.hasBingo()) {
                    System.out.println("Bingo! You win!");
                    System.out.println("Final Card:");
                    card.displayCard();
                    return;
                }
            }
        }
        
        scanner.close();
        
    }
}
