package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class IllegalMarketPositionException extends MasterOfRenaissanceRuntimeException {
    public IllegalMarketPositionException(PlayerDashboard p) {
        super("The chosen row or column does not exist");
        PlayerTurnManager.resetMainAction();
        p.setMainActionError("The chosen row or column does not exist");
    }
}
