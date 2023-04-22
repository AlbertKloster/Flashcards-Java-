package flashcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Application {

    private static final Log LOG = Log.getInstance();


    public static void run(String[] args) {
        List<Card> cards = new ArrayList<>();

        Optional<String> importFile = getImportFile(args);
        Optional<String> exportFile = getExportFile(args);

        importFile.ifPresent(s -> importCards(s, cards));

        boolean exit = false;
        while (!exit) {
            LOG.output("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            Command command = null;
            while (command == null) {
                command = Command.getCommand(LOG.input());
            }
            switch (command) {
                case ADD -> add(cards);
                case REMOVE -> remove(cards);
                case IMPORT -> importCards(cards);
                case EXPORT -> exportCards(cards);
                case ASK -> ask(cards);
                case EXIT -> exit = true;
                case LOG -> saveLog();
                case HARDEST_CARD -> hardestCard(cards);
                case RESET_STATS -> resetStats(cards);
            }
        }
        LOG.output("Bye bye!");
        exportFile.ifPresent(s -> exportCards(s, cards));
    }

    private static void add(List<Card> cards) {
        CardHandler.addCard(cards);
    }

    private static void remove(List<Card> cards) {
        CardHandler.remove(cards);
    }

    private static void importCards(List<Card> cards) {
        List<Card> newCards = FileHandler.importCards();
        CardHandler.mergeCards(cards, newCards);
    }

    private static void importCards(String fileName, List<Card> cards) {
        List<Card> newCards = FileHandler.importCards(fileName);
        CardHandler.mergeCards(cards, newCards);
    }

    private static void exportCards(List<Card> cards) {
        FileHandler.exportCards(cards);
    }

    private static void exportCards(String fileName, List<Card> cards) {
        FileHandler.exportCards(fileName, cards);
    }

    private static void ask(List<Card> cards) {
        CardHandler.askCards(cards);
    }

    private static void saveLog() {
        FileHandler.saveLog();
    }

    private static void hardestCard(List<Card> cards) {
        CardHandler.getHardestCards(cards);
    }

    private static void resetStats(List<Card> cards) {
        CardHandler.resetStats(cards);
    }

    private static Optional<String> getImportFile(String[] args) {
        int index = getIndex("-import", args);
        if (index != -1 && args.length > index + 1)
            return Optional.of(args[index + 1]);
        return Optional.empty();
    }

    private static Optional<String> getExportFile(String[] args) {
        int index = getIndex("-export", args);
        if (index != -1 && args.length > index + 1)
            return Optional.of(args[index + 1]);
        return Optional.empty();
    }

    private static int getIndex(String s, String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (s.equals(args[i])) return i;
        }
        return -1;
    }

}
