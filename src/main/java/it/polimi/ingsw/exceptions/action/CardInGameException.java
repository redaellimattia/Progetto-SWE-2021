package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class CardInGameException extends MasterOfRenaissanceRuntimeException {
    public CardInGameException() {
        super("The chosen card is in game, you can't discard it!");
    }
}
