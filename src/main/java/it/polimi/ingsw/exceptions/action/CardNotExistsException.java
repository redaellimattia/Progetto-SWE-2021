package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class CardNotExistsException extends MasterOfRenaissanceRuntimeException {

    public CardNotExistsException(String cardType, PlayerDashboard player) {
        super("You don't own this " + cardType);
        player.setExceptionError("You don't own this " + cardType);
    }
}
