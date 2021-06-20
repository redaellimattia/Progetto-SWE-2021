package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.network.server.Observer;

public interface SoloToken {

    /**
     * Executes the action represented by the token
     * @param player Lorenzo's dashboard
     * @param game the current Game object
     * @param observer
     */
    void useToken(PlayerDashboard player, Game game, Observer observer);
}
