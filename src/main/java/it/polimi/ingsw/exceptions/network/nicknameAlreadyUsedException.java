package it.polimi.ingsw.exceptions.network;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class nicknameAlreadyUsedException extends MasterOfRenaissanceRuntimeException {
    public nicknameAlreadyUsedException(String msg) {
        super("This username: " + msg +" is already taken!");
    }
}
