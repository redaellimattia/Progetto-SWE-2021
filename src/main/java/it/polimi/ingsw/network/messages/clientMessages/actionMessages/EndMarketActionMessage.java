package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.action.marketAction.MarketAction;
import it.polimi.ingsw.network.enumeration.MarketActionType;
import it.polimi.ingsw.network.messages.serverMessages.PrintMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.logging.Level;

public class EndMarketActionMessage extends MarketActionMessage {
    private final int rowColType;
    private final int pos;

    public EndMarketActionMessage(String nickname, long serverThreadID, int rowColType, int pos) {
        super(nickname, serverThreadID, MarketActionType.ENDMARKETACTION);
        this.rowColType = rowColType;
        this.pos = pos;
    }

    /**
     * Creates a MarketAction with the choices made by the user (list of AtomicMarketAction) and uses it.
     * @param socketConnection the connection from which the message has arrived
     * @param serverLobby serverLobby of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        GameManager gameManager = serverLobby.getGameLobby().getGameManager();
        MarketAction action = new MarketAction(rowColType, pos, serverLobby.getTurnManager().getMarketChoices(), gameManager);
        Server.LOGGER.log(Level.INFO,"LobbyID: "+serverLobby.getLobbyId()+": End Market Action arrived from: "+getNickname());
        if(useActionMessage(action, serverLobby))
            serverLobby.sendToAll(new PrintMessage("Player: "+getNickname()+" took resources from the market!").serialize(),getNickname());
    }
}
