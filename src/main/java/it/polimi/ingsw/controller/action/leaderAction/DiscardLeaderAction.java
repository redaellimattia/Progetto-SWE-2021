package it.polimi.ingsw.controller.action.leaderAction;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;

public class DiscardLeaderAction extends Action {
    private LeaderCard card;

    public DiscardLeaderAction(LeaderCard card) {
        this.card = card;
    }

    @Override
    public boolean useAction(PlayerDashboard player){
        int position = player.getLeaderPos(card);
        if(position==-1)
            return false;
        if(player.getLeaderCards().get(position).isInGame())
            return false;
        player.discardLeader(position);
        return true;
    }
}
