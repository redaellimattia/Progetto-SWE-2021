package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public abstract class ServerMessage {
    private ServerMessageType type;
    private long serverThreadID;

    public ServerMessage(ServerMessageType type, long serverThreadID) {
        this.type = type;
        this.serverThreadID = serverThreadID;
    }
}
