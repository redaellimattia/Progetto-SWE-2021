package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class WrongAbilityException extends MasterOfRenaissanceRuntimeException {
    public WrongAbilityException(String abilityType, PlayerDashboard player) {
        super("The Leader Card you selected does not have the " + abilityType + ".");
        player.setMainActionError("The Leader Card you selected does not have the " + abilityType + ".");
    }
}
