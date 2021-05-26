package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.model.PlayerDashboard;

public class EmptyDeckException extends MasterOfRenaissanceRuntimeException{
    public EmptyDeckException(PlayerDashboard p) {
        super("The chosen Deck is empty!");
        p.setMainActionError("The chosen Deck is empty!");
    }
}
