package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerDashboard;

public interface SoloToken {

    void useToken(PlayerDashboard player, Game game);
}
