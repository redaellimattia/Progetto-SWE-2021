package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class StartGameMessage extends ServerMessage{
    public StartGameMessage() {
        super(ServerMessageType.STARTGAME);
    }
}
