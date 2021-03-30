package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public interface Deck {
    ArrayList<DevelopmentCard> getDeck();
    DevelopmentCard getFirst();
    void removeFirst();
    DevelopmentCard getCard(int pos);
}
