package hangman.loader;

import java.io.IOException;
import java.util.ArrayList;

public abstract class HangmanLoader {

    ArrayList<String> wordList = new ArrayList<>();

    public abstract ArrayList<String> load() throws IOException;
}
