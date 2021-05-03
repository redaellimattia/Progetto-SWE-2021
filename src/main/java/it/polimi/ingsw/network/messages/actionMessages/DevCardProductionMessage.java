package it.polimi.ingsw.network.messages.actionMessages;

import it.polimi.ingsw.controller.action.productionAction.DevCardProductionAction;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.MessageType;
import it.polimi.ingsw.network.server.SocketConnection;

public class DevCardProductionMessage extends ActionMessage{
    DevelopmentCard card;
    ResourceCount storageCount;
    ResourceCount chestCount;

    /*public DevCardProductionMessage(MessageType type, String nickname, long serverThreadID, ActionType actionType) {
        super(type, nickname, serverThreadID, actionType);
    }*/

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
