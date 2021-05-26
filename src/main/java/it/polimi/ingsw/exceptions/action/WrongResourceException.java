package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class WrongResourceException extends MasterOfRenaissanceRuntimeException {
    public WrongResourceException(String res, PlayerDashboard p) {
        super("You have chosen the wrong resource type: " + res);
        p.setExceptionError("You have chosen the wrong resource type: " + res);
    }
}
