package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

public class EndMarketActionMessage extends MarketActionMessage {
    private final int rowColType;
    private final int pos;

    public EndMarketActionMessage(String nickname, long serverThreadID, int rowColType, int pos) {
        super(nickname, serverThreadID);
        this.rowColType = rowColType;
        this.pos = pos;
    }
}
