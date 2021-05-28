package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class CardNotExistsException extends MasterOfRenaissanceRuntimeException {

    public CardNotExistsException(String cardType, PlayerDashboard player) {
        super("You don't own this " + cardType);
        player.setSideActionError("You don't own this " + cardType);
    }

    public CardNotExistsException(String cardType, PlayerDashboard player,PlayerTurnManager turnManager) {
        super("You don't own this " + cardType);
        turnManager.resetMainAction();
        player.setMainActionError("You don't own this " + cardType);
    }
}
