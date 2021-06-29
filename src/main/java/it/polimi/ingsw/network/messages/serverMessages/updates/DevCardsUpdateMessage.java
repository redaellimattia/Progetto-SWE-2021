package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.DeckDashboard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

public class DevCardsUpdateMessage extends PlayerUpdateMessage{
    private final DeckDashboard[] devCards;

    public DevCardsUpdateMessage(String nickname,DeckDashboard[] devCards) {
        super(PlayerUpdateType.DEVCARDS, nickname);
        this.devCards = devCards;
    }

    /**
     * Updates the model with the new DevCards of the player with the given nickname
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.updateDevCards(getNickname(),devCards);
    }
}
