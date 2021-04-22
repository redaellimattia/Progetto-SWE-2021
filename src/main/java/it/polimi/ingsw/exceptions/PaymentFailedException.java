package it.polimi.ingsw.exceptions;

public class PaymentFailedException extends MasterOfRenaissanceRuntimeException{
    public PaymentFailedException(String count) {
        super("Payment failed using the resources from the " + count);
    }
    public PaymentFailedException() { super("Payment failed");
    }
}
