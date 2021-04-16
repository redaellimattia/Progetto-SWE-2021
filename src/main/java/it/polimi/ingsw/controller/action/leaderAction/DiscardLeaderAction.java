package it.polimi.ingsw.controller.action.leaderAction;

import it.polimi.ingsw.controller.Parameter;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;

public class DiscardLeaderAction extends Action {
    @Override
    public boolean useAction(PlayerDashboard player, Parameter param){
        LeaderCard card = param.getLeaderCard();
        int position = player.getLeaderPos(card);
        if(position==-1)
            return false;
        if(player.getLeaderCards().get(position).isInGame())
            return false;
        player.discardLeader(position);
        return true;
    }
}
