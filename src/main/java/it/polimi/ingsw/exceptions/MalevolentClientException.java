package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerThread;

public class MalevolentClientException extends MasterOfRenaissanceRuntimeException{
    public MalevolentClientException(long serverThreadID, String nickname) {

        super("Malevolent client, going to disconnect him.");
        ServerThread serverThread = Server.serverThreads.get(serverThreadID);
        serverThread.onDisconnect(serverThread.getClients().get(nickname));
    }
}
