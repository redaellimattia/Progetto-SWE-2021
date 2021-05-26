package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class CardInGameException extends MasterOfRenaissanceRuntimeException {
    public CardInGameException(PlayerDashboard p) {
        super("The chosen card is in game, you can't discard it!");
        p.setSideActionError("The chosen card is in game, you can't discard it!");
    }
}
