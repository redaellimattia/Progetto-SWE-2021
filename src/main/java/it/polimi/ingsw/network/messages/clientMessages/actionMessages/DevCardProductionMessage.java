package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.productionAction.DevCardProductionAction;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

public class DevCardProductionMessage extends ActionMessage{
    DevelopmentCard card;
    ResourceCount storageCount;
    ResourceCount chestCount;

    public DevCardProductionMessage(String nickname, long serverThreadID) {
        super(ActionType.DEVCARDPRODUCTION, nickname, serverThreadID);
    }

    /**
     * Create a DevCardProductionAction and uses it
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby) {
        DevCardProductionAction action = new DevCardProductionAction(card, storageCount, chestCount);
        PlayerTurnManager turnManager = getPlayerTurnManager(serverLobby);
        turnManager.addDevCardProduction(action);
    }

}
