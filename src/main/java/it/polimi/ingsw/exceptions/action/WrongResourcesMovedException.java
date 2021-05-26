package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class WrongResourcesMovedException extends MasterOfRenaissanceRuntimeException {
    public WrongResourcesMovedException(PlayerDashboard p) {
        super("The switch you tried to make is impossible!");
        p.setSideActionError("The switch you tried to make is impossible!");
    }
}
