package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.marketAction.DiscardResource;
import it.polimi.ingsw.network.enumeration.MarketActionType;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

public class DiscardResourceMessage extends MarketActionMessage {

    public DiscardResourceMessage(String nickname, long serverThreadID) {
        super(nickname, serverThreadID, MarketActionType.DISCARDRESOURCE);
    }

    /**
     * Creates a DiscardResource atomic action and adds it to the MarketChoice buffer.
     * @param socketConnection the connection from which the message has arrived
     * @param serverLobby serverLobby of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby) {
        DiscardResource action = new DiscardResource();
        serverLobby.getTurnManager().addMarketChoice(action);
    }
}
