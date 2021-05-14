package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.marketAction.AtomicMarketAction;
import it.polimi.ingsw.controller.action.marketAction.MarketAction;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.ArrayList;

public class MarketActionMessage extends ActionMessage{
    private int type;
    private int pos;
    private ArrayList<AtomicMarketAction> choices;

    public MarketActionMessage(String nickname, long serverThreadID) {
        super(ActionType.MARKETACTION, nickname, serverThreadID);
    }

    /**
     * Create a MarketAction and uses it.
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerThread serverThread) {
        MarketAction action = new MarketAction(type, pos, choices, serverThread.getGameLobby().getGameManager().getGame().getMarket());
        useActionMessage(action, socketConnection,serverThread);
    }
}
