package it.polimi.ingsw.network.messages.actionMessages;

import it.polimi.ingsw.controller.action.productionAction.LeaderCardProductionAction;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.MessageType;
import it.polimi.ingsw.network.server.SocketConnection;

public class LeaderProductionMessage extends ActionMessage{
    LeaderCard card;
    ResourceCount storageCount;
    ResourceCount chestCount;
    Resource res;

    /*public LeaderProductionMessage(MessageType type, String nickname, long serverThreadID, ActionType actionType) {
        super(type, nickname, serverThreadID, actionType);
    }*/

    /**
     * Create a LeaderCardProductionAction and uses it
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection) {
        LeaderCardProductionAction action = new LeaderCardProductionAction(card, storageCount, chestCount, res);
        useActionMessage(action, socketConnection);
    }
}