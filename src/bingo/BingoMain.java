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
        
        // Load all bingo cards (no limit to 4 cards)
        List<BingoCard> allCards = (List<BingoCard>) cardManager.loadCardsFromFile("BingoCards.txt");
        Collections.shuffle(allCards); // Shuffle the cards randomly
        
        // Display all available cards and allow user to select multiple cards
        System.out.println("Select play mode: ");
        System.out.println("1. Automatic (Random caller)");
        System.out.println("2. Manual (Type called number)");
        int playMode = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (playMode == 2) {
            // Manual play mode
            System.out.println("Manual play selected.");
            
            // Display all available cards for the user to pick
            System.out.println("Available cards to choose from:");
            for (int i = 0; i < allCards.size(); i++) {
                System.out.println((i + 1) + ". " + allCards.get(i).getCardName());
            }

            System.out.print("How many cards would you like to select? ");
            int selectedCardsCount = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (selectedCardsCount > allCards.size()) {
                System.out.println("You can select up to " + allCards.size() + " cards. Please try again.");
                return; // Exit if the user tries to select more cards than available
            }
            
            List<BingoCard> selectedCards = selectCards(scanner, allCards, selectedCardsCount);
            System.out.println("You have selected the following cards: ");
            selectedCards.forEach(card -> System.out.println(card.getCardName()));
            
            // Start manual play with selected cards
            playManual(scanner, selectedCards);
        } else {
            // Automatic play mode (random caller)
            System.out.println("Automatic play selected.");
            
            // Automatically select cards for the player
            for (int i = 0; i < numCards && i < allCards.size(); i++) {
                playerCards.add(allCards.get(i));
            }
            
            // Start automatic play with selected cards
            playAutomatic(scanner, caller, playerCards);
        }

        scanner.close();
    }

    // Function to handle automatic play with random calls
    private static void playAutomatic(Scanner scanner, BingoCaller caller, List<BingoCard> playerCards) {
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
            String numberOnly = caller.getNumberPart(calledNumber);
            System.out.println("Marking Number: " + numberOnly);  // Debugging output
            
            // Process each card to mark called number
            boolean numberMarked = false;
            for (BingoCard card : playerCards) {
                if (card.markNumber(numberOnly)) {
                    numberMarked = true;
                }
            }
            
            // Display all cards after marking the number (even if no number is marked)
            System.out.println("\nUpdated Cards:");
            for (BingoCard card : playerCards) {
                card.displayCard();  // Display each card after marking
                if (card.hasBingo()) {
                    System.out.println("Bingo! You win!");
                    System.out.println("Final Card:");
                    card.displayCard();
                    return;
                }
            }
            
            // If no number was marked, inform the user
            if (!numberMarked) {
                System.out.println("Number " + calledNumber + " was not found on any card.");
            }
        }
    }
    
    // Function to handle manual play where user types in the number
    private static void playManual(Scanner scanner, List<BingoCard> selectedCards) {
        System.out.println("Manual play mode activated.");

        // Start manual play for the selected cards
        while (true) {
            System.out.print("Enter the called number (just the number part, e.g., 5, 16, 72) or type 'exit' to quit: ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Game Over.");
                break;
            }
            
            // Validate input
            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Please enter a valid number (1-75).");
                continue;
            }
            
            String calledNumber = input.trim();
            System.out.println("Called Number: " + calledNumber);
            
            // Mark the called number on the selected cards
            boolean numberMarked = false;
            for (BingoCard card : selectedCards) {
                if (card.markNumber(calledNumber)) {
                    numberMarked = true;
                }
            }

            // Display each selected card
            for (BingoCard card : selectedCards) {
                card.displayCard();
                if (card.hasBingo()) {
                    System.out.println("Bingo! You win on card: " + card.getCardName());
                    System.out.println("Final Card:");
                    card.displayCard();
                    return;
                }
            }
            
            if (!numberMarked) {
                System.out.println("Number " + calledNumber + " was not found on any card.");
            }
        }
    }

    // Helper function to allow the user to select multiple cards
    private static List<BingoCard> selectCards(Scanner scanner, List<BingoCard> allCards, int selectedCardsCount) {
        List<BingoCard> selectedCards = new ArrayList<>();
        Set<Integer> selectedIndices = new HashSet<>();
        
        while (selectedCards.size() < selectedCardsCount) {
            System.out.println("Select card number (1-" + allCards.size() + "):");
            int selectedIndex = scanner.nextInt() - 1;  // Adjust for zero-based index
            scanner.nextLine(); // Consume newline
            
            if (selectedIndex < 0 || selectedIndex >= allCards.size() || selectedIndices.contains(selectedIndex)) {
                System.out.println("Invalid selection or card already selected. Try again.");
            } else {
                selectedCards.add(allCards.get(selectedIndex));
                selectedIndices.add(selectedIndex);
            }
        }
        return selectedCards;
    }
}
