package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.enumeration.ClientMessageType;

public class KeepAliveMessage extends ClientMessage{
    public KeepAliveMessage(String nickname, long serverThreadID) {
        super(ClientMessageType.KEEPALIVE, nickname, serverThreadID);
    }
}
