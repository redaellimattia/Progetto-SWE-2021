package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class ProductionAlreadyDoneException extends MasterOfRenaissanceRuntimeException {
    public ProductionAlreadyDoneException() { super("Production already used in this turn!");
    }
}
