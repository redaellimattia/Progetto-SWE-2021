package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.network.server.Observer;

public interface SoloToken {

    void useToken(PlayerDashboard player, Game game, Observer observer);
}
