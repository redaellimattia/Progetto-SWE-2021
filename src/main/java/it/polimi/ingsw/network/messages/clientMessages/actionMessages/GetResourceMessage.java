package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

public class GetResourceMessage extends MarketActionMessage {
    private final int storageRow;

    public GetResourceMessage(String nickname, long serverThreadID, int storageRow) {
        super(nickname, serverThreadID);
        this.storageRow = storageRow;
    }
}
