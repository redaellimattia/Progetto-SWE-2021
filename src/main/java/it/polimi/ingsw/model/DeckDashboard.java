package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public class DeckDashboard extends Deck {

    public DeckDashboard(ArrayList<DevelopmentCard> cards) {
        for (DevelopmentCard  card: cards) {
            addCard(card);
        }
    }

    // Insert card on top
    @Override
    public void addCard(DevelopmentCard card) { // TO-DO: Check if the deck is full
        super.addCard(card);
    }
}
