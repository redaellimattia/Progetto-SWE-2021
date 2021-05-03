package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.SocketConnection;

import java.util.ArrayList;

public class JoinGameMessage extends ClientMessage {
    private long serverThreadID;
    private ArrayList<String> players;
    private int numberOfPlayers;

    @Override
    public void useMessage(SocketConnection socketConnection){

    }
}
