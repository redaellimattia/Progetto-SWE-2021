package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.productionAction.DevCardProductionAction;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.server.SocketConnection;

public class DevCardProductionMessage extends ActionMessage{
    DevelopmentCard card;
    ResourceCount storageCount;
    ResourceCount chestCount;

    public DevCardProductionMessage(String nickname, long serverThreadID) {
        super(ActionType.DEVCARDPRODUCTION, nickname, serverThreadID);
    }

    /**
     * Create a DevCardProductionAction and uses it
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection) {
        DevCardProductionAction action = new DevCardProductionAction(card, storageCount, chestCount);
        useActionMessage(action, socketConnection);
    }

}
