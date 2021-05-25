package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.action.CardShopAction;
import it.polimi.ingsw.controller.action.marketAction.GetResource;
import it.polimi.ingsw.controller.action.marketAction.MarketAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.enumeration.MarketActionType;
import it.polimi.ingsw.network.messages.serverMessages.DoneMessage;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

public class EndMarketActionMessage extends MarketActionMessage {
    private final int rowColType;
    private final int pos;

    public EndMarketActionMessage(String nickname, long serverThreadID, int rowColType, int pos) {
        super(nickname, serverThreadID, MarketActionType.ENDMARKETACTION);
        this.rowColType = rowColType;
        this.pos = pos;
    }

    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        GameManager gameManager = serverLobby.getGameLobby().getGameManager();
        MarketAction action = new MarketAction(rowColType, pos, serverLobby.getTurnManager().getMarketChoices(), gameManager);
        useActionMessage(action, socketConnection, serverLobby);
        serverLobby.sendToAll(new DoneMessage().serialize());
    }
}
