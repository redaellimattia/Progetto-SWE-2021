package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.model.PlayerDashboard;

public class CounterTopOverloadException extends MasterOfRenaissanceRuntimeException{

    public CounterTopOverloadException(PlayerDashboard p){
        super("The chosen destination is already full");
        p.setSideActionError("The chosen destination is already full");
    }

    public CounterTopOverloadException(PlayerDashboard p,PlayerTurnManager turnManager){
        super("The chosen destination is already full");
        turnManager.resetMainAction();
        p.setMainActionError("The chosen destination is already full");
    }
}
