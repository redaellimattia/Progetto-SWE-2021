package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public class DeckDashboard extends Deck {
    //might be easier to create it as an empty arraylist instead of passing a deck of cards;
    public DeckDashboard(){
        super(new ArrayList<DevelopmentCard>());
    }
}
