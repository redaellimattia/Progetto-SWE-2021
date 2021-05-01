package it.polimi.ingsw.network.messages.actionMessages;

import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.MessageType;

public class DevCardProductionMessage extends ActionMessage{
    public DevCardProductionMessage(MessageType type, String nickname, long serverThreadID, ActionType actionType) {
        super(type, nickname, serverThreadID, actionType);
    }
}
