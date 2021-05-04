package it.polimi.ingsw.exceptions.network;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class UnrecognisedPlayerException extends MasterOfRenaissanceRuntimeException {
    public UnrecognisedPlayerException() {
        super("Unrecognised player in this game!");
    }
}
