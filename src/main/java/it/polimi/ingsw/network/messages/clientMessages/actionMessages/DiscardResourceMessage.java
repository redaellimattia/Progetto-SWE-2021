package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.marketAction.DiscardResource;
import it.polimi.ingsw.network.enumeration.MarketActionType;
import it.polimi.ingsw.network.messages.serverMessages.DoneMessage;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

public class DiscardResourceMessage extends MarketActionMessage {

    public DiscardResourceMessage(String nickname, long serverThreadID) {
        super(nickname, serverThreadID, MarketActionType.DISCARDRESOURCE);
    }

    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby) {
        DiscardResource action = new DiscardResource();
        serverLobby.getTurnManager().addMarketChoice(action);
    }
}
