package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.model.PlayerDashboard;

public class EmptyDeckException extends MasterOfRenaissanceRuntimeException{
    public EmptyDeckException(PlayerDashboard p,PlayerTurnManager turnManager) {
        super("The chosen Deck is empty!");
        turnManager.resetMainAction();
        p.setMainActionError("The chosen Deck is empty!");
    }
}
