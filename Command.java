package flashcards;

public enum Command {
    ADD("add"),
    REMOVE("remove"),
    IMPORT("import"),
    EXPORT("export"),
    ASK("ask"),
    EXIT("exit"),
    LOG("log"),
    HARDEST_CARD("hardest card"),
    RESET_STATS("reset stats");

    private final String name;

    Command(String name) {
        this.name = name;
    }

    public static Command getCommand(String input) {
        for (Command command : Command.values()) {
            if (command.name.equals(input)) return command;
        }
        return null;
    }

}
