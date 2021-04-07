package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

public class DeckShop extends Deck {

    // Insert card on top
    @Override
    public void addCard(DevelopmentCard card) { // TO-DO: Check if the deck is full
        super.addCard(card);
    }
}
