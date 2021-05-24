package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

import java.util.ArrayList;

public class LeaderUpdateMessage extends PlayerUpdateMessage{
    private final ArrayList<LeaderCard> updatedLeaderCards;

    public LeaderUpdateMessage(PlayerUpdateType type,String nickname, ArrayList<LeaderCard> updatedLeaderCards) {
        super(type, nickname);
        this.updatedLeaderCards = updatedLeaderCards;
    }

    /**
     * Updates the LeaderCards of the player with the given nickname
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        switch(getPlayerUpdateType()){
            case INGAMELEADER:
            case DISCARDLEADER:
                clientManager.getGameStatus().updateLeaderCards(getNickname(),updatedLeaderCards);
                                break;
        }
    }
}
