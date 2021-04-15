package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.token.SoloToken;

public class SoloTokenAction extends Action {

    public boolean drawToken(PlayerDashboard player) {
        SoloToken token = Game.pickNextToken();
        token.useToken(player);
        return true;
    }

}
