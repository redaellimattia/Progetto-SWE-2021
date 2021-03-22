package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

public class DeckDashboard implements Deck{
    private DevelopmentCard[] cardsDeck = new DevelopmentCard[3];

    @Override
    public DevelopmentCard[] getDeck(){
        return cardsDeck;
    }

    @Override
    public DevelopmentCard getFirst() {
        return cardsDeck[0];
    }
}
