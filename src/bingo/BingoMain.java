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
    }
}