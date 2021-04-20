package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public abstract class Deck {
    private ArrayList<DevelopmentCard> cardsDeck;

    /**
     *
     * @param cardsDeck the array of cards that are part of the Deck
     */
    public Deck(ArrayList<DevelopmentCard> cardsDeck) {
        this.cardsDeck = cardsDeck;
    }

    /**
     *
     * @return the array of cards that are part of the Deck
     */
    //Return cardsDeck
    public ArrayList<DevelopmentCard> getDeck() {
        return cardsDeck;
    }

    /**
     *
     * @return the card that is on top of the deck (first position)
     */
    public DevelopmentCard getFirst() throws IndexOutOfBoundsException {
        return cardsDeck.get(0);
    }

    // Removes the card on top
    public void removeFirst(){
        cardsDeck.remove(0);
    }

    /**
     *
     * @return the selected card
     * @throws IndexOutOfBoundsException if the selected pos is not present in the Deck
     */
    public DevelopmentCard getCard(int pos) throws IndexOutOfBoundsException {
        return cardsDeck.get(pos);
    }

    /**
     *
     * @param card the card to be added
     * @throws IllegalArgumentException if the card to be added has not the right level
     * @throws IllegalStateException if Deck is full
     */
    // Insert card on top (only for DeckDashboard)
    public void addCard(DevelopmentCard card) throws IllegalStateException, IllegalArgumentException {
        if(this.cardsDeck.size()>=3) {
            throw new IllegalStateException();
        }
        if(card.getLevel() == 1 && !this.cardsDeck.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if(card.getLevel() > 1 && this.cardsDeck.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if(card.getLevel() > 1 && !(this.cardsDeck.get(0).getLevel() == card.getLevel() - 1)) {
            throw new IllegalArgumentException();
        }
        cardsDeck.add(0, card);
    }
}
