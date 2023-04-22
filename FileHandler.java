package flashcards;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {

    private static final Log LOG = Log.getInstance();

    public static List<Card> importCards() {
        LOG.output("File name:");
        String fileName = LOG.input();
        return importCards(fileName);
    }

    public static List<Card> importCards(String fileName) {
        List<Card> cards = new ArrayList<>();
        String unitSeparator = String.valueOf((char) 31);

        try(Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split(unitSeparator);
                if (split.length == 3)
                    cards.add(new Card(split[0], split[1], Integer.parseInt(split[2])));
            }
            LOG.output(String.format("%d cards have been loaded.", cards.size()));
        } catch (IOException e) {
            LOG.output("File not found.");
        }

        return cards;
    }

    public static void exportCards(List<Card> cards) {
        LOG.output("File name:");
        String fileName = LOG.input();
        exportCards(fileName, cards);
    }

    public static void exportCards(String fileName, List<Card> cards) {

        try(FileWriter fileWriter = new FileWriter(fileName);
                PrintWriter printWriter = new PrintWriter(fileWriter)) {
            cards.forEach(printWriter::println);
            LOG.output(String.format("%d cards have been saved.", cards.size()));
        } catch (IOException e) {
            System.out.println("Something went wrong...");
        }
    }

    public static void saveLog() {
        LOG.output("File name:");
        String fileName = LOG.input();
        try(FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter)) {
            LOG.output("The log has been saved"); // this message should be included in the log
            LOG.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // timestamp at the end of the log
            LOG.getLog().forEach(printWriter::println);
        } catch (IOException e) {
            LOG.output("Something went wrong...");
        }
    }
}
