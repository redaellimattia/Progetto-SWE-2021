package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.enumeration.ServerMessageType;
import it.polimi.ingsw.network.messages.clientMessages.ClientMessage;

import java.util.ArrayList;

public class ReturnLobbiesMessage extends ServerMessage {
    private int numberOfPlayers;
    private ArrayList<String> players;

    public ReturnLobbiesMessage(long serverThreadID, int numberOfPlayers, ArrayList<String> players) {
        super(ServerMessageType.LOBBIES,serverThreadID);
        this.numberOfPlayers = numberOfPlayers;
        this.players = players;
    }
}
