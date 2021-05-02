package it.polimi.ingsw.network.messages.actionMessages;

import it.polimi.ingsw.controller.action.move.MoveFromLeaderToDeposit;
import it.polimi.ingsw.controller.action.move.OrganizeStorage;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.MessageType;
import it.polimi.ingsw.network.server.SocketConnection;

public class OrganizeStorageMessage extends ActionMessage{
    private int from;
    private int to;
    /*public OrganizeStorageMessage(MessageType type, String nickname, long serverThreadID, ActionType actionType) {
        super(type, nickname, serverThreadID, actionType);
    }*/
    @Override
    public void useMessage(SocketConnection socketConnection){
        OrganizeStorage action = new OrganizeStorage(from,to);
        useActionMessage(action,socketConnection);
    }
}
