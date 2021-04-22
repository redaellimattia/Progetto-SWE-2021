package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class WrongResourceException extends MasterOfRenaissanceRuntimeException {
    public WrongResourceException(String res) {
        super("You have chosen the wrong resource type: " + res);
    }
}
