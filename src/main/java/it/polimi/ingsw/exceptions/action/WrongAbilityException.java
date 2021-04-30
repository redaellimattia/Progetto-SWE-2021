package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class WrongAbilityException extends MasterOfRenaissanceRuntimeException {
    public WrongAbilityException(String abilityType) {
        super("The Leader Card you selected does not have the " + abilityType + ".");
    }
}
