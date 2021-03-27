package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public interface Deck {
    public ArrayList<DevelopmentCard> getDeck();
    public DevelopmentCard getFirst();
}
