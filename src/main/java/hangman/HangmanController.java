package hangman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class HangmanController {

    private final int MAX_HP = 10;
    private ArrayList<String> wordList;
    private ArrayList<Character> usedLetter;
    private Random RNG = new Random();
    private int playerHP;
    private String currentWord;
    private char[] foundLetter;
    private Scanner sc;

    public HangmanController(Scanner sc) {
        this.playerHP = MAX_HP;
        this.wordList = new ArrayList<>();
        this.usedLetter = new ArrayList<>();
        this.currentWord = null;
        this.sc = sc;
    }

    public void play() {

        this.chooseRandomWord();

        while (!this.isGameDone()) {

            this.showUsedLetterState();
            this.showWordToFindState();

            char newLetter = this.askPlayerLetter();
            HashSet<Integer> indexes = this.checkLetterInWord(newLetter);

            if (indexes.isEmpty()) {
                this.reducePlayerHP();
            } else {
                this.updateLetterFound(indexes, newLetter);
            }

            if (this.countFoundLetter() == this.currentWord.length()) {
                int numberAttempt = this.MAX_HP-this.playerHP;
                System.out.println("Congrats ! You found the word " + this.currentWord +
                        " in "+ numberAttempt +  " attempts");
                this.askPlayerForANewGame();
            } else if (this.playerHP == 0) {
                System.out.println("Oh no, you loose !");
                this.askPlayerForANewGame();
            }
        }
    }

    private void chooseRandomWord() {

        int randomIndex = this.RNG.nextInt(this.wordList.size());
        String chosenWord = this.wordList.get(randomIndex);

        while (chosenWord.equals(this.currentWord)) {
            randomIndex = this.RNG.nextInt(this.wordList.size());
            chosenWord = this.wordList.get(randomIndex);
        }

        this.currentWord = chosenWord;
        this.foundLetter = this.initFoundLetterArray(chosenWord);
    }

    private char[] initFoundLetterArray(String chosenWord) {

        char[] foundLetter = new char[chosenWord.length()];
        for (int i = 0 ; i < foundLetter.length ; i++) {
            foundLetter[i] = '_';
        }
        return foundLetter;
    }

    private char askPlayerLetter() {
        System.out.println("Play a letter");
        System.out.println("Health point remaining : "+this.playerHP);
        String input = this.sc.nextLine();

        while (!isCorrectLetter(input)) {
            if (this.playerHP <= 0) {
                break;
            } else {
                System.out.println("Play a letter");
                System.out.println("Health point remaining : "+this.playerHP);
                input = this.sc.nextLine();
            }
        }

        char letter = input.charAt(0);
        this.usedLetter.add(letter);
        return letter;
    }

    private boolean isGameDone() {
        int nbFoundLetter = this.countFoundLetter();
        return this.playerHP == 0 || nbFoundLetter == this.currentWord.length();
    }

    private void askPlayerForANewGame() {

        System.out.println("Do you want to find another word ? (Y/N)");
        String input = this.sc.nextLine();
        Boolean isEqualToY = input.equals("Y");
        Boolean isEqualToN = input.equals("N");

        while (!isEqualToN && !isEqualToY) {

            System.out.println("Do you want to find another word ? (Y/N)");
            input = this.sc.nextLine();
            isEqualToY = input.equals("Y");
            isEqualToN = input.equals("N");
        }

        if (isEqualToY) {
            System.out.println("New game start !");
            this.chooseRandomWord();
            this.usedLetter.clear();
        } else {
            System.out.println("Good Bye !");
        }
    }

    private void updateLetterFound(HashSet<Integer> indexes, char newLetter) {
        for (Integer i : indexes) {
            this.foundLetter[i] = newLetter;
        }
    }

    private int countFoundLetter() {

        int count = 0;
        for (char c : this.foundLetter) {
            if (c != '_') {
                count++;
            }
        }
        return count;
    }

    private HashSet<Integer> checkLetterInWord(char newLetter) {

        HashSet<Integer> indexes = new HashSet<>();
        String currentWordLowerCase = this.currentWord.toLowerCase();
        char letter = String.valueOf(newLetter).toLowerCase().charAt(0);
        for (int i = 0 ; i < this.currentWord.length() ; i++) {
            if (currentWordLowerCase.charAt(i) == letter) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    private void showWordToFindState() {
        String wordState = "Word state : ";

        for (Character c : this.foundLetter)
            wordState+=c;

        System.out.println(wordState);
    }

    private void showUsedLetterState() {
        int usedLetterListSize = this.usedLetter.size();

        if (usedLetterListSize != 0) {

            String usedLetterState;
            if (usedLetterListSize > 1) {
                usedLetterState = "Letters used : ";
            } else {
                usedLetterState = "Letter used : ";
            }

            for(Character c : this.usedLetter) {
                usedLetterState+=c;
            }
            System.out.println(usedLetterState);
        }
    }

    private boolean isCorrectLetter(String input) {

        if (isInputAString(input) || isInputANumber(input)) {
            System.out.println("This is not letter, please type a letter");
            return false;
        } else if (isLetterAlreadyUsed(input)) {
            System.out.println("Letter already used !");
            this.reducePlayerHP();
            return false;
        }
        return true;
    }

    private boolean isLetterAlreadyUsed(String input) {
        char letter = input.charAt(0);
        return this.usedLetter.contains(letter);
    }

    private boolean isInputAString(String input) {
        return input.length() > 1 ;
    }

    private boolean isInputANumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void setWordList(ArrayList<String> wordList) {
        this.wordList = wordList;
    }

    private void reducePlayerHP() {
        this.playerHP = this.playerHP-1;
    }
}
