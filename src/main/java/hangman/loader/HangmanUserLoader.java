package hangman.loader;

import java.util.ArrayList;
import java.util.Scanner;

public class HangmanUserLoader extends HangmanLoader {

    Scanner sc;

    public HangmanUserLoader(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public ArrayList<String> load() {

        String word;
        Boolean isEmpty = false;

        do {
            System.out.println("Enter words you want to add");
            System.out.println("If you're done, press ENTER key");
            word = sc.nextLine();
            isEmpty = word.isEmpty() ? true : false;
            if (!isEmpty) {
                this.wordList.add(word);
                System.out.println(word+" added");
            }
        } while (!isEmpty);

        this.showList();
        return wordList;
    }

    private void showList() {

        String wordsAdded = "";
        int wordListSize = this.wordList.size();

        for (int i = 0 ; i < wordListSize ; i++) {
            String currentWord = this.wordList.get(i);
            if (i == wordListSize-1) {
                wordsAdded+=currentWord;
            } else {
                wordsAdded+=currentWord+", ";
            }
        }
        System.out.println("List of words added : "+wordsAdded);
    }
}
