package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.enumeration.Resource;

public class WrongCounterTopException extends MasterOfRenaissanceRuntimeException {
    public WrongCounterTopException(Resource res) {
        super("You cannot add a " + res.name() + " into this CounterTop.");
    }
}
