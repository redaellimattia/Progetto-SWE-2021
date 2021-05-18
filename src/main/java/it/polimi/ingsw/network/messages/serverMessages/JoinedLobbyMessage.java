package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class JoinedLobbyMessage extends ServerMessage{
    private long serverThreadID;
    private String msg;

    public JoinedLobbyMessage(long serverThreadID){
        super(ServerMessageType.JOINEDLOBBY);
        this.serverThreadID = serverThreadID;
        this.msg = "Joined correctly the game with Lobby ID: " + serverThreadID;
    }

    @Override
    public void useMessage(ClientManager clientManager) {
        clientManager.setServerThreadID(serverThreadID);

        clientManager.getView().printMsg(msg);
    }
}
