package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class NoWhiteMarbleException extends MasterOfRenaissanceRuntimeException {
    public NoWhiteMarbleException() {
        super("You must select a white marble to use the Leader Card ability.");
    }
}
