package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

import java.util.ArrayList;

public class ReturnLobbiesMessage extends ServerMessage {
    private ArrayList<availableGameLobbies> availableGameLobbies = new ArrayList<>();

    public ReturnLobbiesMessage(ArrayList<GameLobby> gameLobbies) {
        super(ServerMessageType.LOBBIES);

        for(GameLobby g:gameLobbies)
            availableGameLobbies.add(new availableGameLobbies(g.getServerThreadID(),g.getNumberOfPlayers(),g.getPlayers()));
    }

    private class availableGameLobbies {
        private final long serverThreadID;
        private final int numberOfPlayers;
        private final ArrayList<String> players;

        public availableGameLobbies(long serverThreadID,int numberOfPlayers, ArrayList<String> players) {
            this.serverThreadID = serverThreadID;
            this.numberOfPlayers = numberOfPlayers;
            this.players = players;
        }
    }
}

