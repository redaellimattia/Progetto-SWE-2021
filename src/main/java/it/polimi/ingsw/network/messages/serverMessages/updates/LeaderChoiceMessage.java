package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

import java.util.ArrayList;

public class LeaderChoiceMessage extends PlayerUpdateMessage{
    private ArrayList<LeaderCard> leaderCards;

    public LeaderChoiceMessage(String nickname, ArrayList<LeaderCard> leaderCards) {
        super(PlayerUpdateType.LEADERCHOICE, nickname);
        this.leaderCards = leaderCards;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getGameStatus().updateLeaderCards(getNickname(),leaderCards);
    }
}
