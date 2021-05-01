package it.polimi.ingsw.exceptions;

public class CounterTopOverloadException extends MasterOfRenaissanceRuntimeException{

    public CounterTopOverloadException(String msg) {
        super("The shelf in position" + msg +" can't contain all the resources");
    }
    public CounterTopOverloadException(){
        super("The chosen destination is already full");
    }
}
