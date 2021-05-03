package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.ArrayList;

public class AskLobbyMessage extends ClientMessage {

    /**
     *
     * @param socketConnection socketConnection of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection){
        ArrayList<GameLobby> gameLobbies = new ArrayList<>();
        for(Long key: Server.serverThreads.keySet())
            gameLobbies.add(Server.serverThreads.get(key).getGameLobby());
        ReturnLobbiesMessage returnLobbiesMessage = new ReturnLobbiesMessage(gameLobbies);
        socketConnection.send(returnLobbiesMessage.serialize());
    }
}
