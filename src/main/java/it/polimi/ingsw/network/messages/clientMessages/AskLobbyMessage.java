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
        ArrayList<ReturnLobbiesMessage> gameLobbies = new ArrayList<>();
        for(Long key: Server.serverThreads.keySet()) {
            GameLobby g = Server.serverThreads.get(key).getGameLobby();
            gameLobbies.add(new ReturnLobbiesMessage(g.getServerThreadID(),g.getNumberOfPlayers(),g.getPlayers()));
        }
        String json =  gson.toJson(gameLobbies);
        socketConnection.send(json);
    }
}
