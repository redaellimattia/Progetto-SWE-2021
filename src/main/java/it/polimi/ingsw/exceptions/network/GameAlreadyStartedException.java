package it.polimi.ingsw.exceptions.network;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class GameAlreadyStartedException extends MasterOfRenaissanceRuntimeException {
    public GameAlreadyStartedException() {
        super("Cannot Join, game already started.");
    }
}
