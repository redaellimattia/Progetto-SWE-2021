package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.move.OrganizeStorage;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.server.SocketConnection;

public class OrganizeStorageMessage extends ActionMessage{
    private int from;
    private int to;

    public OrganizeStorageMessage(String nickname, long serverThreadID) {
        super(ActionType.ORGANIZESTORAGE, nickname, serverThreadID);
    }

    /**
     * move resources on the storage
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection){
        OrganizeStorage action = new OrganizeStorage(from,to);
        useSideActionMessage(action,socketConnection);
    }
}
