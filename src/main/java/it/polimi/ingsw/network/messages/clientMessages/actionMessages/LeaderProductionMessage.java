package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.productionAction.LeaderCardProductionAction;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.messages.serverMessages.DoneMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.logging.Level;

public class LeaderProductionMessage extends ActionMessage{
    private final LeaderCard card;
    private final ResourceCount storageCount;
    private final ResourceCount chestCount;
    private final Resource res;

    public LeaderProductionMessage(String nickname, long serverThreadID,LeaderCard card,ResourceCount storageCount, ResourceCount chestCount, Resource res) {
        super(ActionType.LEADERPRODUCTION, nickname, serverThreadID);
        this.card = card;
        this.storageCount = storageCount;
        this.chestCount = chestCount;
        this.res = res;
    }

    /**
     * Create a LeaderCardProductionAction and uses it
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby) {
        LeaderCardProductionAction action = new LeaderCardProductionAction(card, storageCount, chestCount, res);
        PlayerTurnManager turnManager = getPlayerTurnManager(serverLobby);
        Server.LOGGER.log(Level.INFO,"Leader Production arrived!");
        if(turnManager.addLeaderCardProduction(action))
            serverLobby.sendToAll(new DoneMessage().serialize());
    }
}
