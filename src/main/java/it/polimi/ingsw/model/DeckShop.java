package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public class DeckShop extends Deck {

    /**
     *
     * @param cards The cards inside the deck
     * @throws IllegalArgumentException if cards size is not 4 or
     *                                  if cards don't share the same colour and level
     */
    public DeckShop(ArrayList<DevelopmentCard> cards) throws IllegalArgumentException {
        super(cards);
        // Each new DeckShop should contain 4 cards
        if(cards.size() != 4) {
            throw new IllegalArgumentException();
        }
        // Cards in the same DeckShop should have common colour and level
        for(int i = 1; i < cards.size(); i++) {
            if(!(cards.get(i).getColour() == cards.get(0).getColour() &&
                    cards.get(i).getLevel() == cards.get(0).getLevel())) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     *
     * @param card the card to be added
     * @throws IllegalStateException because adding cards to a DeckShop is not allowed
     */
    // Adding cards to a DeckShop is not allowed
    @Override
    public void addCard(DevelopmentCard card) throws IllegalStateException {
        throw new IllegalStateException();
    }
}
