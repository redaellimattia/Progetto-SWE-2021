package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public class DeckDashboard extends Deck {

    public DeckDashboard(ArrayList<DevelopmentCard> cards) { //modified because of constructor in super class;
        //for (DevelopmentCard  card: cards) {
         //   addCard(card);
        //}
        super(cards);
    }

    // Insert card on top
    //@Override //It's not a method redefinition, it just specify 2 different thing; maybe it can be deleted;
    //public void addCard(DevelopmentCard card) { // TO-DO: Check if the deck is full
    //    super.addCard(card);
    //}
}
