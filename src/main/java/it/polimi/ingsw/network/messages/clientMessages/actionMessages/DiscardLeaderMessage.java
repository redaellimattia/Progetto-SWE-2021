package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.leaderAction.DiscardLeaderAction;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

public class DiscardLeaderMessage extends ActionMessage{
    private LeaderCard card;

    public DiscardLeaderMessage(String nickname, long serverThreadID,LeaderCard card) {
        super(ActionType.DISCARDLEADER, nickname, serverThreadID);
        this.card = card;
    }

    /**
     * Create a DiscardLeaderAction and uses it.
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        DiscardLeaderAction action = new DiscardLeaderAction(card);
        useSideActionMessage(action,socketConnection, serverLobby);
    }

}
