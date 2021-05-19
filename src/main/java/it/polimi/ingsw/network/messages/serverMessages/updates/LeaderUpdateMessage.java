package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

import java.util.ArrayList;

public class LeaderUpdateMessage extends PlayerUpdateMessage{
    private ArrayList<LeaderCard> updatedLeaderCards;

    public LeaderUpdateMessage(PlayerUpdateType type,String nickname, ArrayList<LeaderCard> updatedLeaderCards) {
        super(type, nickname);
        this.updatedLeaderCards = updatedLeaderCards;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        switch(getPlayerUpdateType()){
            case INGAMELEADER:  clientManager.getGameStatus().updateLeaderCards(getNickname(),updatedLeaderCards);
                                clientManager.updateViewWithMessage(getNickname()+" played a leader!");
                                break;
            case DISCARDLEADER: clientManager.getGameStatus().updateLeaderCards(getNickname(),updatedLeaderCards);
                                clientManager.updateViewWithMessage(getNickname()+" discarded a leader!");
                                break;
        }
    }
}
