package it.polimi.ingsw.exceptions;

public class WrongResourceException extends MasterOfRenaissanceRuntimeException{
    public WrongResourceException(String res) {
        super("You have chosen the wrong resource type: " + res);
    }
}
