package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

public class DeckShop implements Deck{
    private DevelopmentCard[] cardsDeck = new DevelopmentCard[4];
    @Override
    public DevelopmentCard[] getDeck() {
        return cardsDeck;
    }

    @Override
    public DevelopmentCard getFirst() {
        return cardsDeck[0];
    }
}
