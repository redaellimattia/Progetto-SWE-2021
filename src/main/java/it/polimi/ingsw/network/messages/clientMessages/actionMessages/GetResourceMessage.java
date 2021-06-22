package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.marketAction.GetResource;
import it.polimi.ingsw.network.enumeration.MarketActionType;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

public class GetResourceMessage extends MarketActionMessage {
    private final int storageRow;

    public GetResourceMessage(String nickname, long serverThreadID, int storageRow) {
        super(nickname, serverThreadID, MarketActionType.GETRESOURCE);
        this.storageRow = storageRow;
    }

    /**
     * Creates a GetResource action and adds it to the MarketChoice buffer.
     * @param socketConnection the connection from which the message has arrived
     * @param serverLobby serverLobby of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby) {
        GetResource action = new GetResource(storageRow);
        serverLobby.getTurnManager().addMarketChoice(action);
    }
}
