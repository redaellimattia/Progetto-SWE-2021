package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

public interface Deck {
    DevelopmentCard[] getDeck();
    DevelopmentCard getFirst();
}
