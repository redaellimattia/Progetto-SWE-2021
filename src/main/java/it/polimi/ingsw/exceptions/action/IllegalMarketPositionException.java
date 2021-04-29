package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class IllegalMarketPositionException extends MasterOfRenaissanceRuntimeException {
    public IllegalMarketPositionException() {
        super("The chosen row or column does not exist");
    }
}
