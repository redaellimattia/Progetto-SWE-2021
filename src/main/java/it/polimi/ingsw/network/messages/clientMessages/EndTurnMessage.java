package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.enumeration.ClientMessageType;

public class EndTurnMessage extends ClientMessage{
    public EndTurnMessage(String nickname, long serverThreadID) {
        super(ClientMessageType.ENDTURN, nickname, serverThreadID);
    }
}
