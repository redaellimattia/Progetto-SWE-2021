package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.marketAction.GetResource;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

public class GetResourceMessage extends MarketActionMessage {
    private final int storageRow;

    public GetResourceMessage(String nickname, long serverThreadID, int storageRow) {
        super(nickname, serverThreadID);
        this.storageRow = storageRow;
    }

    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby) {
        GetResource action = new GetResource(storageRow);
        serverLobby.getTurnManager().addMarketChoice(action);
    }
}
