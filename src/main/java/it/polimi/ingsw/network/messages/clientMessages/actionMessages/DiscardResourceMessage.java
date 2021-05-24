package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

public class DiscardResourceMessage extends MarketActionMessage {

    public DiscardResourceMessage(String nickname, long serverThreadID) {
        super(nickname, serverThreadID);
    }
}
