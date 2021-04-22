package it.polimi.ingsw.exceptions;

public class CardNotExistsException extends MasterOfRenaissanceRuntimeException{

    public CardNotExistsException(String cardType) {
        super("You don't own this " + cardType);
    }
}
