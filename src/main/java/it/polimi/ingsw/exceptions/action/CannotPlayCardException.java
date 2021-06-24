package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class CannotPlayCardException extends MasterOfRenaissanceRuntimeException {
    public CannotPlayCardException(PlayerDashboard p){
        super("You can't play this card!");
        p.setSideActionError("You can't play this card!");
    }
}
