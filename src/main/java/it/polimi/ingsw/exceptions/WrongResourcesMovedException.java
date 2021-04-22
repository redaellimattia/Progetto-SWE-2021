package it.polimi.ingsw.exceptions;

public class WrongResourcesMovedException extends MasterOfRenaissanceRuntimeException{
    public WrongResourcesMovedException() {
        super("The switch you tried to make is impossible!");
    }
}
