package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.logging.Level;

public class KeepAliveMessage extends ClientMessage{
    public KeepAliveMessage(String nickname, long serverThreadID) {
        super(ClientMessageType.KEEPALIVE, nickname, serverThreadID);
    }
    @Override
    public void useMessage(SocketConnection socketConnection){
        Server.LOGGER.log(Level.INFO,"KeepAlive");
    }
}
