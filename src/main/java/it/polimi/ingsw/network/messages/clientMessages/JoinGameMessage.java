package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.server.SocketConnection;


public class JoinGameMessage extends ClientMessage {

    public JoinGameMessage(String nickname, long serverThreadID) {
        super(ClientMessageType.JOINGAME, nickname, serverThreadID);
    }

    @Override
    public void useMessage(SocketConnection socketConnection){

    }
}
