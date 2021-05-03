package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class IncompleteListException extends MasterOfRenaissanceRuntimeException {
    public IncompleteListException() {
        super("The list of choices is incomplete.");
    }
}
