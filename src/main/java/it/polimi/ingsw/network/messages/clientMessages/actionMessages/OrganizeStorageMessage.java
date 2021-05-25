package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.move.OrganizeStorage;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.messages.serverMessages.DoneMessage;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

public class OrganizeStorageMessage extends ActionMessage{
    private final int from;
    private final int to;

    public OrganizeStorageMessage(String nickname, long serverThreadID, int from, int to) {
        super(ActionType.ORGANIZESTORAGE, nickname, serverThreadID);
        this.from = from;
        this.to= to;
    }

    /**
     * move resources on the storage
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        OrganizeStorage action = new OrganizeStorage(from,to);
        useSideActionMessage(action,socketConnection, serverLobby);
        serverLobby.sendToAll(new DoneMessage().serialize());
    }
}
