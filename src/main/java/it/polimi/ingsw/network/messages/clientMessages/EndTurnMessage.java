package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.server.SocketConnection;

public class EndTurnMessage extends ClientMessage{
    public EndTurnMessage(String nickname, long serverThreadID) {
        super(ClientMessageType.ENDTURN, nickname, serverThreadID);
    }

    @Override
    public void useMessage(SocketConnection socketConnection){

    }
}
