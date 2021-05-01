package it.polimi.ingsw.network.messages;

public class ConnectionMessage extends Message{
    private String nickname;
    private int numberOfPlayers;

    public String getNickname() {
        return nickname;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }


}
