package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public interface Deck {
    public ArrayList<DevelopmentCard> getDeck();
    public DevelopmentCard getFirst();
    public void removeFirst();
    public DevelopmentCard getCard(int pos);
    public void addCard(DevelopmentCard card);
}
