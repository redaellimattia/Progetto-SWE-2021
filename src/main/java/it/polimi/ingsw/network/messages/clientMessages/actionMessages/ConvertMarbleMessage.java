package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.model.card.LeaderCard;

public class ConvertMarbleMessage extends MarketActionMessage {
    private final int leaderCardId;
    private final int storageRow;

    public ConvertMarbleMessage(String nickname, long serverThreadID, int leaderCardId, int storageRow) {
        super(nickname, serverThreadID);
        this.leaderCardId = leaderCardId;
        this.storageRow = storageRow;
    }
}
