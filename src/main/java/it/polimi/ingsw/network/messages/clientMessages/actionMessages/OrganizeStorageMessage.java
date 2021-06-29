package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.move.OrganizeStorage;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.messages.serverMessages.DoneMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.logging.Level;

public class OrganizeStorageMessage extends ActionMessage{
    private final int from;
    private final int to;

    public OrganizeStorageMessage(String nickname, long serverThreadID, int from, int to) {
        super(ActionType.ORGANIZESTORAGE, nickname, serverThreadID);
        this.from = from;
        this.to= to;
    }

    /**
     * Move resources on the storage
     * @param socketConnection the connection from which the message has arrived
     * @param serverLobby serverLobby of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        OrganizeStorage action = new OrganizeStorage(from,to);
        useSideActionMessage(action, serverLobby);
        Server.LOGGER.log(Level.INFO,"LobbyID: "+serverLobby.getLobbyId()+": Organize Storage arrived from: "+getNickname());
        serverLobby.sendToAll(new DoneMessage().serialize(),null);
    }
}
