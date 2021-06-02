package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

import java.util.ArrayList;

public class ReturnLobbiesMessage extends ServerMessage {
    private final ArrayList<availableGameLobbies> availableGameLobbies;

    public ReturnLobbiesMessage(ArrayList<GameLobby> gameLobbies) {
        super(ServerMessageType.LOBBIES);
        availableGameLobbies = new ArrayList<>();
        for(GameLobby g:gameLobbies)
            availableGameLobbies.add(new availableGameLobbies(g.getServerThreadID(), g.getNumberOfPlayers(), g.getPlayers()));
    }

    public static class availableGameLobbies {
        private final long serverThreadID;
        private final int numberOfPlayers;
        private final ArrayList<String> players;

        public availableGameLobbies(long serverThreadID,int numberOfPlayers, ArrayList<String> players) {
            this.serverThreadID = serverThreadID;
            this.numberOfPlayers = numberOfPlayers;
            this.players = players;
        }

        public long getServerThreadID() {
            return serverThreadID;
        }

        public int getNumberOfPlayers() {
            return numberOfPlayers;
        }

        public ArrayList<String> getPlayers() {
            return players;
        }

        @Override
        public String toString(){
            return "Players: " + this.getNumberOfPlayers() +"  "+this.getPlayers();
        }
    }

    /**
     * Prints the available lobbies
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getView().printLobbies(availableGameLobbies);
    }
}

