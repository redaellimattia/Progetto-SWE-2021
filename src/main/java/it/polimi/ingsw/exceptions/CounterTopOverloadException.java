package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.model.PlayerDashboard;

public class CounterTopOverloadException extends MasterOfRenaissanceRuntimeException{

    public CounterTopOverloadException(String msg,PlayerDashboard p) {
        super("The shelf in position" + msg +" can't contain all the resources");
        p.setExceptionError("The shelf in position" + msg +" can't contain all the resources");
    }
    public CounterTopOverloadException(PlayerDashboard p ){
        super("The chosen destination is already full");
        p.setExceptionError("\"The chosen destination is already full\"");
    }
}
