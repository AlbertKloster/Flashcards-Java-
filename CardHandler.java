package flashcards;

import java.util.*;

public class CardHandler {
    private static final Log LOG = Log.getInstance();

    public static void addCard(List<Card> cards) {
        LOG.output("The card");
        String term = LOG.input();
        if (termExist(term, cards)) {
            LOG.output(String.format("The card \"%s\" already exists.", term));
            return;
        }
        LOG.output("The definition of the card:");
        String definition = LOG.input();
        if (definitionExist(definition, cards)) {
            LOG.output(String.format("The definition \"%s\" already exists.", definition));
            return;
        }
        cards.add(new Card(term, definition, 0));
        LOG.output(String.format("The pair (\"%s\":\"%s\") has been added.", term, definition));
    }

    public static void remove(List<Card> cards) {
        LOG.output("Which card?");
        String term = LOG.input();
        Optional<Card> byTerm = findByTerm(term, cards);
        if (byTerm.isEmpty()) {
            LOG.output(String.format("Can't remove \"%s\": there is no such card.", term));
        }
        else {
            cards.remove(byTerm.get());
            LOG.output("The card has been removed.");
        }
    }

    public static void mergeCards(List<Card> cards, List<Card> newCards) {
        newCards.forEach(card -> {
            Optional<Card> byTerm = findByTerm(card.getTerm(), cards);
            byTerm.ifPresent(cards::remove);
            cards.add(card);
        });
    }

    public static void askCards(List<Card> cards) {
        LOG.output("How many times to ask?");
        int times = Integer.parseInt(LOG.input());
        Random random = new Random();
        for (int i = 0; i < times; i++) {
            Card card = cards.get(random.nextInt(cards.size()));
            LOG.output(String.format("Print the definition of \"%s\":", card.getTerm()));
            String definition = LOG.input();
            Optional<Card> byDefinition = findByDefinition(definition, cards);
            if (card.getDefinition().equals(definition)) {
                LOG.output("Correct!");
            }
            else if (byDefinition.isEmpty()) {
                LOG.output(String.format("Wrong. The right answer is \"%s\".", card.getDefinition()));
                card.increaseErrors();
            }
            else {
                LOG.output(String.format("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".", card.getDefinition(), byDefinition.get().getTerm()));
                card.increaseErrors();
            }
        }
    }

    public static void getHardestCards(List<Card> cards) {
        Optional<Card> max = cards.stream().max(Comparator.comparing(Card::getErrors));
        if (max.isEmpty()) {
            LOG.output("There are no cards with errors.");
            return;
        }
        int errors = max.get().getErrors();
        if (errors == 0) {
            LOG.output("There are no cards with errors.");
            return;
        }
        List<Card> hardestCards = cards.stream().filter(card -> card.getErrors() == errors).toList();

        if (hardestCards.size() == 1) {
            Card card = hardestCards.get(0);
            LOG.output(String.format("The hardest card is \"%s\". You have %d errors answering it.", card.getTerm(), card.getErrors()));
            return;
        }

        StringBuilder builder = new StringBuilder("The hardest cards are");
        builder.append(" \"").append(cards.get(0).getTerm()).append("\"");
        for (int i = 1; i < hardestCards.size(); i++) {
            builder.append(", \"").append(hardestCards.get(i).getTerm()).append("\"");
        }
        builder.append(". You have ").append(hardestCards.get(0).getErrors()).append(" errors answering them.");
        LOG.output(builder.toString());
    }

    private static boolean termExist(String term, List<Card> cards) {
        return cards.stream().anyMatch(card -> card.getTerm().equals(term));
    }

    private static boolean definitionExist(String definition, List<Card> cards) {
        return cards.stream().anyMatch(card -> card.getDefinition().equals(definition));
    }

    private static Optional<Card> findByDefinition(String definition, List<Card> cards) {
        return cards.stream().filter(card -> card.getDefinition().equals(definition)).findAny();
    }

    private static Optional<Card> findByTerm(String term, List<Card> cards) {
        return cards.stream().filter(card -> card.getTerm().equals(term)).findAny();
    }

    public static void resetStats(List<Card> cards) {
        cards.forEach(Card::resetErrors);
        LOG.output("Card statistics have been reset.");
    }
}
