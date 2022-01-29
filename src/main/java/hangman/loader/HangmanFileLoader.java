package hangman.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HangmanFileLoader extends HangmanLoader {

    BufferedReader BR;

    public HangmanFileLoader(String filename) {
        super();
        try {
            this.BR = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    @Override
    public ArrayList<String> load() throws IOException {

        String currentWord = "";
        try {
            while ((currentWord = BR.readLine()) != null) {
                this.wordList.add(currentWord);
            }
        } catch (IOException e) {
            throw new IOException("Problems happened during file reading");
        }
        return wordList;
    }
}
