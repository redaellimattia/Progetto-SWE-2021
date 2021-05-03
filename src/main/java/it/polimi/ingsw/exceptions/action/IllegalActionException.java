package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class IllegalActionException extends MasterOfRenaissanceRuntimeException {
    public IllegalActionException() {
        super("This action is not permitted, you already did one main action.");
    }
}
