package it.polimi.ingsw.exceptions;

public class CardInGameException extends MasterOfRenaissanceRuntimeException{
    public CardInGameException() {
        super("The chosen card is in game, you can't discard it!");
    }
}
