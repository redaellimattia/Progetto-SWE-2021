package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public abstract class Deck {
    private ArrayList<DevelopmentCard> cardsDeck;

    //Return copy of cardsDeck
    public ArrayList<DevelopmentCard> getDeck() {
        return new ArrayList<DevelopmentCard>(cardsDeck);
    }

    public DevelopmentCard getFirst() throws IndexOutOfBoundsException {
        return cardsDeck.get(0);
    }

    public void removeFirst() throws IndexOutOfBoundsException {
        cardsDeck.remove(0);
    }

    public DevelopmentCard getCard(int pos) throws IndexOutOfBoundsException {
        return cardsDeck.get(pos);
    }

    // Insert card on top
    public void addCard(DevelopmentCard card) {
        cardsDeck.add(0, card);
    }
}
