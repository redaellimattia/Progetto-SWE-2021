package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public abstract class Deck {
    private ArrayList<DevelopmentCard> cardsDeck;

    public Deck(ArrayList<DevelopmentCard> cardsDeck) {
        this.cardsDeck = cardsDeck;
    }

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

    // Insert card on top (only for DeckDashboard)
    public void addCard(DevelopmentCard card) throws IllegalStateException, IllegalArgumentException {
        if(this.cardsDeck.size()>=3) {
            throw new IllegalStateException();
        }
        if(card.getLevel() == 1 && !this.cardsDeck.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if(card.getLevel() > 1 && !(this.cardsDeck.get(0).getLevel() == card.getLevel() - 1)) {
            throw new IllegalArgumentException();
        }
        cardsDeck.add(0, card);
    }
}
