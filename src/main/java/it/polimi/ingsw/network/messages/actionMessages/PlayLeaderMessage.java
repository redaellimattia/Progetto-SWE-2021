package it.polimi.ingsw.network.messages.actionMessages;

import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.MessageType;
import it.polimi.ingsw.network.messages.actionMessages.ActionMessage;

public class PlayLeaderMessage extends ActionMessage {
    public PlayLeaderMessage(MessageType type, String nickname, long serverThreadID, ActionType actionType) {
        super(type, nickname, serverThreadID, actionType);
    }
}
