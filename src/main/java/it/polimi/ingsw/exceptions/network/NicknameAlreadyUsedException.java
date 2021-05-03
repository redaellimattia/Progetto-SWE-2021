package it.polimi.ingsw.exceptions.network;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class NicknameAlreadyUsedException extends MasterOfRenaissanceRuntimeException {
    public NicknameAlreadyUsedException(String msg) {
        super("This username: " + msg +" is already taken!");
    }
}
