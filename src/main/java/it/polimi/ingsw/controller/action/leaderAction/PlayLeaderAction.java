package it.polimi.ingsw.controller.action.leaderAction;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.CannotPlayCardException;
import it.polimi.ingsw.exceptions.action.CardNotExistsException;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;

public class PlayLeaderAction extends Action {
    private final LeaderCard card;

    /**
     *
     * @param card LeaderCard chosen by the player to play
     */
    public PlayLeaderAction(LeaderCard card) {
        this.card = card;
    }

    /**
     * Plays a leader
     * @param player player that is doing the action
     * @param turnManager turnManager
     */
    @Override
    public boolean useAction(PlayerDashboard player, PlayerTurnManager turnManager){
        int position = player.getLeaderPos(card);
        if(position==-1)
            throw new CardNotExistsException("Leader Card",player);
        if(card.getRequirement().isPlayable(player)) {
            player.setLeaderInGame(position);
            card.getSpecialAbility().useDepositAbility(player);
            return true;
        }
        else
            throw new CannotPlayCardException(player);
    }
}
