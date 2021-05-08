package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class JoinedLobbyMessage extends ServerMessage{
    private long serverThreadID;
    public JoinedLobbyMessage(long serverThreadID){
        super(ServerMessageType.JOINEDLOBBY);
        this.serverThreadID = serverThreadID;
    }

    @Override
    public void useMessage(ClientManager clientManager) {
        clientManager.setServerThreadID(serverThreadID);
    }
}
