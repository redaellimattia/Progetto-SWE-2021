package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

public class DevCardsUpdateMessage extends PlayerUpdateMessage{
    private DevelopmentCard card;
    private int position;

    public DevCardsUpdateMessage(String nickname, DevelopmentCard card, int position) {
        super(PlayerUpdateType.DEVCARDS, nickname);
        this.card = card;
        this.position = position;
    }

    @Override
    public void useMessage(ClientManager clientManager){

    }
}
