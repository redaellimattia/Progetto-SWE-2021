package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class PingMessage extends ServerMessage{

    public PingMessage() {
        super(ServerMessageType.PING);
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.pingResponse();
    }
}
