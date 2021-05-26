package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.productionAction.BasicProductionAction;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.messages.serverMessages.DoneMessage;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

public class BasicProductionMessage extends ActionMessage{
    private final Resource res;
    private final ResourceCount storageCount;
    private final ResourceCount chestCount;

    public BasicProductionMessage(String nickname, long serverThreadID,Resource res,ResourceCount storageCount,ResourceCount chestCount) {
        super(ActionType.BASICPRODUCTION,nickname, serverThreadID);
        this.res = res;
        this.storageCount = storageCount;
        this.chestCount = chestCount;
    }

    /**
     * Create a BasicProductionAction and uses it
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby) {
        BasicProductionAction action = new BasicProductionAction(res, storageCount, chestCount);
        PlayerTurnManager turnManager = getPlayerTurnManager(serverLobby);
        turnManager.addBasicProduction(action);
        serverLobby.sendToAll(new DoneMessage().serialize());
    }
}
