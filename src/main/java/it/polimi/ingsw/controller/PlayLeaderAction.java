package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;

public class PlayLeaderAction extends Action {

    @Override
    public boolean useAction(PlayerDashboard player, Parameter param){
        LeaderCard card = param.getLeaderCard();
        int position = player.getLeaderPos(card);
        if(position==-1)
            return false;
        player.setLeaderInGame(position);
        card.getSpecialAbility().useDepositAbility(player);
        return true;
    }
}
