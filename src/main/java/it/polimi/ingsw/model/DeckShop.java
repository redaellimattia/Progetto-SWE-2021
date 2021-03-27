package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public class DeckShop implements Deck{
    private ArrayList<DevelopmentCard> cardsDeck = new ArrayList<DevelopmentCard>();

    @Override
    public ArrayList<DevelopmentCard> getDeck() {
        return cardsDeck;
    }

    @Override
    public DevelopmentCard getFirst() {
        return cardsDeck.get(0);
    }
}
