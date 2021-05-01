package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.enumeration.MessageType;

public class ConnectionMessage extends Message{
    private int numberOfPlayers;

    public ConnectionMessage(MessageType type, String nickname, long serverThreadID, int numberOfPlayers) {
        super(type, nickname, serverThreadID);
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }


}
