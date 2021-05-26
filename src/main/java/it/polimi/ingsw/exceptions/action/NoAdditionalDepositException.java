package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.Resource;

public class NoAdditionalDepositException extends MasterOfRenaissanceRuntimeException {
    public NoAdditionalDepositException(Resource res, PlayerDashboard p) {
        super("You don't have an additional deposit available for the " + res.name() + ".");
        p.setExceptionError("You don't have an additional deposit available for the " + res.name() + ".");
    }
}
