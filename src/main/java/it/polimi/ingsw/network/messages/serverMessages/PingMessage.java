package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.network.enumeration.ServerMessageType;
import it.polimi.ingsw.network.messages.clientMessages.PingResponseMessage;

public class PingMessage extends ServerMessage{
    public PingMessage() {
        super(ServerMessageType.PING);
    }

    @Override
    public void useMessage(ClientSocket clientSocket){
        clientSocket.send(new PingResponseMessage(clientSocket.getNickname(),clientSocket.getClient().getServerThreadID()).serialize());
    }
}
