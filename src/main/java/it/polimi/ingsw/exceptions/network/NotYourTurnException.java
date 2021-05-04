package it.polimi.ingsw.exceptions.network;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class NotYourTurnException extends MasterOfRenaissanceRuntimeException {
    public NotYourTurnException() {
        super("This is not your turn!");
    }
}
