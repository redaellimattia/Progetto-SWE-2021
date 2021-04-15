package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;

public class LeaderAction extends Action {
    public boolean discardLeaderAction(LeaderCard card, PlayerDashboard player){
        int position = player.getLeaderPos(card);
        if(position==-1)
            return false;
        if(player.getLeaderCards().get(position).isInGame())
            return false;
        player.discardLeader(position);
        return true;
    }
    public boolean playLeader(LeaderCard card, PlayerDashboard player){
        int position = player.getLeaderPos(card);
        if(position==-1)
            return false;
        player.setLeaderInGame(position);
        return true;
    }
}
