package hangman.database;

public class DriverLoader {

    public DriverLoader() throws ClassNotFoundException {
            Class.forName("com.mysql.cj.jdbc.Driver");
    }
}
