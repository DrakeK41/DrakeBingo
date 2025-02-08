package bingo;
import java.util.*;

public class BingoMain {
    public static void main(String[] args) {
        String filename = "BingoCards.txt";
        //Read bingo cards using  BingoCardReader
        List<BingoCard> cards = BingoCardReader.readCardsFromFile(filename);
        
        if (cards.isEmpty()) {
            System.out.println("No Bingo cards found.");
            return;
        }
        
        System.out.println("Loaded Bingo Cards:");
        for (BingoCard card : cards) {
            card.displayCard();
            System.out.println();
        }
        Scanner scanner = new Scanner(System.in);
        BingoCaller caller = new BingoCaller();

        System.out.println("Welcome to Bingo!");
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
        }

        scanner.close();
    }
}