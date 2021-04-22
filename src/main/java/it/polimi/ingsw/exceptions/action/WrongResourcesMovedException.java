package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class WrongResourcesMovedException extends MasterOfRenaissanceRuntimeException {
    public WrongResourcesMovedException() {
        super("The switch you tried to make is impossible!");
    }
}
