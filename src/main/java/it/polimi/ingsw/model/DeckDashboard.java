package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public class DeckDashboard extends Deck {

    public DeckDashboard(ArrayList<DevelopmentCard> cards) throws IllegalArgumentException {
        super(cards);
        if(cards.size() > 4) {
            throw new IllegalArgumentException();
        }
    }
}
