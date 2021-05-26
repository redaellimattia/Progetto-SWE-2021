package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class InvalidRowException extends MasterOfRenaissanceRuntimeException {
    public InvalidRowException(PlayerDashboard p) {
        super("Invalid storage row number.");
        p.setExceptionError("Invalid storage row number.");
    }
}
