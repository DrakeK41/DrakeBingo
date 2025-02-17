package bingo;

import java.util.*;

class BingoCaller {
    //List to store all called numbers
    private final List<String> calledNumbers;
    //Random number generator for bingo calls
    private final Random random;
    //set to track unique calls and avoid duplicates
    private final Set<String> generatedCalls;

    public BingoCaller() {
        this.calledNumbers = new ArrayList<>();
        this.random = new Random();
        this.generatedCalls = new HashSet<>();
    }

    public String callNextNumber() {
        if (generatedCalls.size() == 75) { //check if all numbers have been called
            return "All numbers have been called!";
        }
        
        String call;
        do {
            char letter = getRandomLetter();
            int number = getRandomNumber(letter);
            call = letter + String.valueOf(number);
        } while (generatedCalls.contains(call));
        
        generatedCalls.add(call); //add to set of called numbers
        calledNumbers.add(call); //store the call in the list
        return call;
    }

    private char getRandomLetter() {
        char[] letters = {'B', 'I', 'N', 'G', 'O'};
        return letters[random.nextInt(letters.length)];
    }

    private int getRandomNumber(char letter) {
        switch (letter) {
            case 'B': return random.nextInt(15) + 1;  // 1-15
            case 'I': return random.nextInt(15) + 16; // 16-30
            case 'N': return random.nextInt(15) + 31; // 31-45
            case 'G': return random.nextInt(15) + 46; // 46-60
            case 'O': return random.nextInt(15) + 61; // 61-75
            default: throw new IllegalArgumentException("Invalid letter");
        }
    }
    //method to get the lsit of all called numbers
    public List<String> getCalledNumbers() {
        return calledNumbers;
    }
    // Method to extract only the number part of the called number (removes the letter)
    public String getNumberPart(String calledNumber) {
        return calledNumber.replaceAll("[^0-9]", "");  // Strips non-numeric characters
    }
}

