package it.polimi.ingsw.exceptions;

public class EmptyDeckException extends MasterOfRenaissanceRuntimeException{
    public EmptyDeckException() {
        super("The chosen Deck is empty!");
    }
}
