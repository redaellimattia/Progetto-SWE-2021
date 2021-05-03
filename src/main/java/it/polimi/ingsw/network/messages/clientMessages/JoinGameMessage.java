package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;


public class JoinGameMessage extends ClientMessage {

    public JoinGameMessage(String nickname, long serverThreadID) {
        super(ClientMessageType.JOINGAME, nickname, serverThreadID);
    }

    @Override
    public void useMessage(SocketConnection socketConnection){
        ServerThread serverThread = Server.serverThreads.get(getServerThreadID());
        serverThread.playerLogin(getNickname(),socketConnection);
    }
}
