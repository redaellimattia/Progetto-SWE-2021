package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class CardNotExistsException extends MasterOfRenaissanceRuntimeException {

    public CardNotExistsException(String cardType) {
        super("You don't own this " + cardType);
    }
}
