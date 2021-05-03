package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.server.SocketConnection;

public class DisconnectionMessage extends ClientMessage {
    public DisconnectionMessage(String nickname, long serverThreadID) {
        super(ClientMessageType.DISCONNECTION, nickname, serverThreadID);
    }

    @Override
    public void useMessage(SocketConnection socketConnection){

    }
}
