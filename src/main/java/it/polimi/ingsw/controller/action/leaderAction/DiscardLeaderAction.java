package it.polimi.ingsw.controller.action.leaderAction;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.CardNotExistsException;
import it.polimi.ingsw.exceptions.CardInGameException;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;

public class DiscardLeaderAction extends Action {
    private LeaderCard card;

    /**
     *
     * @param card LeaderCard chosen by the player to discard
     */
    public DiscardLeaderAction(LeaderCard card) {
        this.card = card;
    }

    /**
     *
     * @param player player that is doing the action
     * @return true if ended correctly
     */
    @Override
    public boolean useAction(PlayerDashboard player){
        int position = player.getLeaderPos(card);
        if(position==-1)
            throw new CardNotExistsException("Leader Card");
        if(player.getLeaderCards().get(position).isInGame())
            throw new CardInGameException();
        player.discardLeader(position);
        return true;
    }
}
