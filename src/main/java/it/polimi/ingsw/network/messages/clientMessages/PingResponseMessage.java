package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;

public class PingResponseMessage extends ClientMessage{
    public PingResponseMessage(String nickname, long serverThreadID) {
        super(ClientMessageType.PINGRESPONSE, nickname, serverThreadID);
    }

    @Override
    public void useMessage(SocketConnection socketConnection, ServerThread serverThread){
        System.out.println("RISPOSTA PING"); //------------DEBUG------------------
        serverThread.resetTimer();
    }
}
