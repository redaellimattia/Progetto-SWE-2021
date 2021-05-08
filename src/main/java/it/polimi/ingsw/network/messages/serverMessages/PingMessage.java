package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.network.enumeration.ServerMessageType;
import it.polimi.ingsw.network.messages.clientMessages.PingResponseMessage;

public class PingMessage extends ServerMessage{
    public PingMessage() {
        super(ServerMessageType.PING);
    }

    @Override
    public void useMessage(ClientManager clientManager){
        ClientSocket clientSocket = clientManager.getClientSocket();
        clientSocket.send(new PingResponseMessage(clientSocket.getNickname(),clientManager.getServerThreadID()).serialize());
    }
}
