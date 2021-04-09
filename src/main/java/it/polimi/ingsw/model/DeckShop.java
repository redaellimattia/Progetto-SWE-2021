package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public class DeckShop extends Deck {

    public DeckShop(ArrayList<DevelopmentCard> cards) throws IllegalArgumentException {
        super(cards);
        if(cards.size() > 4) {
            throw new IllegalArgumentException();
        }
    }

    // Adding cards to a DeckShop is not allowed
    @Override
    public void addCard(DevelopmentCard card) throws IllegalStateException {
        throw new IllegalStateException();
    }
}
