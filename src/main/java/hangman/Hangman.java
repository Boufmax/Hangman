package hangman;

import hangman.loader.HangmanDatabaseLoader;
import hangman.loader.HangmanFileLoader;
import hangman.loader.HangmanLoader;
import hangman.loader.HangmanUserLoader;

import java.io.IOException;
import java.util.Scanner;

public class Hangman {
    private Scanner sc;
    private HangmanLoader HL;
    private HangmanController HC;

    public Hangman() {
        this.sc = new Scanner(System.in);
    }

    public void start() throws IOException {

        switch (this.askLoader()) {
            case 1:
                this.HL = new HangmanFileLoader(this.askFileToLoad()+".txt");
                break;
            case 2:
                this.HL = new HangmanUserLoader(sc);
                break;
            case 3:
                this.HL = new HangmanDatabaseLoader();
                break;
        }
        this.HC = new HangmanController(this.sc);
        this.loadWordList();
        this.HC.play();
    }

    private String askFileToLoad() {

        System.out.println("Give the name of the file you want to load (must be a .txt file)");
        String filename = this.sc.nextLine();
        return filename;
    }

    private void loadWordList() throws IOException {
        this.HC.setWordList(this.HL.load());
    }

    private int askLoader() {

        int choice;
        do {
            System.out.println("Choose how you want to load the game :");
            System.out.println("1 - with a file");
            System.out.println("2 - with your own words");
            System.out.println("3 - with a database");
            try {
                choice = Integer.parseInt(this.sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }
        } while (choice != 1 && choice != 2 && choice != 3 && choice == -1);

        return choice;
    }
}
