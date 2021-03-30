package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public class DeckDashboard implements Deck{
    private ArrayList<DevelopmentCard> cardsDeck = new ArrayList<DevelopmentCard>();

    @Override
    public ArrayList<DevelopmentCard> getDeck(){
        return cardsDeck;
    }

    @Override
    public DevelopmentCard getFirst() throws IndexOutOfBoundsException {
        return cardsDeck.get(0);
    }

    @Override
    public void removeFirst() throws IndexOutOfBoundsException {
        cardsDeck.remove(0);
    }

    @Override
    public DevelopmentCard getCard(int pos) throws IndexOutOfBoundsException {
        return cardsDeck.get(pos);
    }
}
