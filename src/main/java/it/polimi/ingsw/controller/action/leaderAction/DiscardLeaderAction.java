package it.polimi.ingsw.controller.action.leaderAction;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.CardNotExistsException;
import it.polimi.ingsw.exceptions.action.CardInGameException;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;

public class DiscardLeaderAction extends Action {
    private final LeaderCard card;

    /**
     *
     * @param card LeaderCard chosen by the player to discard
     */
    public DiscardLeaderAction(LeaderCard card) {
        this.card = card;
    }

    /**
     * Discards a leader
     * @param player player that is doing the action
     * @param turnManager turnManager
     */
    @Override
    public boolean useAction(PlayerDashboard player, PlayerTurnManager turnManager){
        int position = player.getLeaderPos(card);
        if(position==-1)
            throw new CardNotExistsException("Leader Card",player);
        if(player.getLeaderCards().get(position).isInGame())
            throw new CardInGameException(player);

        player.discardLeader(position);
        return true;
    }
}
