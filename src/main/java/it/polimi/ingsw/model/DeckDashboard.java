package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public class DeckDashboard implements Deck{
    private ArrayList<DevelopmentCard> cardsDeck = new ArrayList<DevelopmentCard>();

    @Override
    public ArrayList<DevelopmentCard> getDeck(){
        return new ArrayList<DevelopmentCard>(cardsDeck);
    } //Return copy of cardsDeck

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

    // Insert card on top
    @Override
    public void addCard(DevelopmentCard card) {
        cardsDeck.add(0, card);
    }
}
