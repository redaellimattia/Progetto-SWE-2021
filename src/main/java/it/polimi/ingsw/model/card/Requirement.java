package it.polimi.ingsw.model.card;
import it.polimi.ingsw.model.PlayerDashboard;

public interface Requirement {

    /**
     *
     * @param player that is trying to play the card
     * @return true if it's possible to play the card
     */
    boolean isPlayable(PlayerDashboard player);
    String toString();
}
