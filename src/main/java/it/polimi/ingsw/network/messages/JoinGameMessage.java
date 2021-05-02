package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.server.SocketConnection;

import java.util.ArrayList;

public class JoinGameMessage extends Message{
    private long serverThreadID;
    private ArrayList<String> players;
    private int numberOfPlayers;

    @Override
    public void useMessage(SocketConnection socketConnection){

    }
}
