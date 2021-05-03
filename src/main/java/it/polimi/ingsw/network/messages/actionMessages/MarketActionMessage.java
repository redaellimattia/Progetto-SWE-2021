package it.polimi.ingsw.network.messages.actionMessages;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.action.marketAction.AtomicMarketAction;
import it.polimi.ingsw.controller.action.marketAction.MarketAction;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.MessageType;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.ArrayList;

public class MarketActionMessage extends ActionMessage{
    private int type;
    private int pos;
    private ArrayList<AtomicMarketAction> choices;

    /*public MarketActionMessage(MessageType type, String nickname, long serverThreadID, ActionType actionType) {
        super(type, nickname, serverThreadID, actionType);
    }*/

    /**
     * Create a MarketAction and uses it.
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection) {
        GameManager gameManager = getServerThread(socketConnection).getGameLobby().getGameManager();
        MarketAction action = new MarketAction(type, pos, choices, gameManager);
        useActionMessage(action, socketConnection);
    }
}
