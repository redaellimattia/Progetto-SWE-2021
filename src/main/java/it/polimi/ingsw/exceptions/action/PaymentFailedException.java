package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;

public class PaymentFailedException extends MasterOfRenaissanceRuntimeException {
    public PaymentFailedException(String count) {
        super("Payment failed using the resources from the " + count);
    }
    public PaymentFailedException() { super("Payment failed");
    }
}
