package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerLobby;

public class MalevolentClientException extends MasterOfRenaissanceRuntimeException{
    public MalevolentClientException(long serverThreadID, String nickname) {

        super("Malevolent client, going to disconnect him.");
        ServerLobby serverLobby = Server.serverThreads.get(serverThreadID);
        serverLobby.onDisconnect(serverLobby.getClients().get(nickname));
    }
}
