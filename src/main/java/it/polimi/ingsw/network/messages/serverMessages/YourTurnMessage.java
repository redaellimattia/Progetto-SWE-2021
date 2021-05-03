package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class YourTurnMessage extends ServerMessage{
    public YourTurnMessage() {
        super(ServerMessageType.YOURTURN);
    }
}
