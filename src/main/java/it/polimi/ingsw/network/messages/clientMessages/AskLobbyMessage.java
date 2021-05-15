package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.ArrayList;

public class AskLobbyMessage extends ClientMessage {

    public AskLobbyMessage(String nickname, long serverThreadID) {
        super(ClientMessageType.ASKLOBBIES, nickname, serverThreadID);
    }

    /**
     *
     * @param socketConnection socketConnection of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection){
        socketConnection.send(createReturnLobbiesMessage().serialize());
    }
}
