package it.polimi.ingsw.network.messages.actionMessages;

import it.polimi.ingsw.controller.action.leaderAction.DiscardLeaderAction;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.server.SocketConnection;

public class DiscardLeaderMessage extends ActionMessage{
    private LeaderCard card;
    /*public DiscardLeaderMessage(MessageType type, String nickname, long serverThreadID, ActionType actionType) {
        super(type, nickname, serverThreadID, actionType);
    }*/

    /**
     * Create a DiscardLeaderAction and uses it.
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection){
        DiscardLeaderAction action = new DiscardLeaderAction(card);
        useActionMessage(action,socketConnection);
    }

}
