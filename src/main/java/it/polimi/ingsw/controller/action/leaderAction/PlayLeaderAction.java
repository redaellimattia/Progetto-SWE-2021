package it.polimi.ingsw.controller.action.leaderAction;

import it.polimi.ingsw.controller.Parameter;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;

public class PlayLeaderAction extends Action {
    private LeaderCard card;

    public PlayLeaderAction(LeaderCard card) {
        this.card = card;
    }
    @Override
    public boolean useAction(PlayerDashboard player){
        int position = player.getLeaderPos(card);
        if(position==-1)
            return false;
        player.setLeaderInGame(position);
        card.getSpecialAbility().useDepositAbility(player);
        return true;
    }
}
