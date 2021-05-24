package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.marketAction.AtomicMarketAction;
import it.polimi.ingsw.controller.action.marketAction.MarketAction;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.ArrayList;

public class MarketActionMessage extends ActionMessage{
    private final int type;
    private final int pos;
    private final ArrayList<AtomicMarketAction> choices;

    public MarketActionMessage(String nickname, long serverThreadID,int type, int pos, ArrayList<AtomicMarketAction> choices) {
        super(ActionType.MARKETACTION, nickname, serverThreadID);
        this.type = type;
        this.pos = pos;
        this.choices = choices;
    }

    /**
     * Create a MarketAction and uses it.
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby) {
        MarketAction action = new MarketAction(type, pos, choices, serverLobby.getGameLobby().getGameManager());
        useActionMessage(action, socketConnection, serverLobby);
    }
}
