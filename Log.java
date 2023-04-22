package flashcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Log {

    private static Log instance;
    private final Scanner SCANNER = new Scanner(System.in);
    private final List<String> log;

    private Log() {
        log = new ArrayList<>();
    }

    public static Log getInstance() {
        if (instance == null)
            instance = new Log();
        return instance;
    }

    public List<String> getLog() {
        return log;
    }

    public void add(String message) {
        log.add(message);
    }

    public void output(String message) {
        System.out.println(message);
        add(message);
    }

    public String input() {
        String input = SCANNER.nextLine();
        add(input);
        return input;
    }

}
