package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class PaymentFailedException extends MasterOfRenaissanceRuntimeException {
    public PaymentFailedException(String count, PlayerDashboard p) {
        super("Payment failed using the resources from the " + count);
        p.setMainActionError("Payment failed using the resources from the " + count);
    }
    public PaymentFailedException(PlayerDashboard p) {
        super("Payment failed");
        PlayerTurnManager.resetMainAction();
        p.setMainActionError("Payment failed, retry the action");
    }
}
