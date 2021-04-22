package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerDashboard;

public class GameManager {
    private Game game;
    private PlayerTurnManager turnManager;

    public GameManager(Game game, PlayerTurnManager turnManager) {
        this.game = game;
        this.turnManager = turnManager;
    }

    public void initGame() { //Distribuire risorse + creaz shop etc

    }

    public void createAction(Action action){
        this.turnManager.setAction(action);
    }

    public void nextRound() {
        PlayerDashboard player = this.turnManager.getPlayer();
        PlayerDashboard nextPlayer = game.getNextPlayer(player);
        if(nextPlayer!=null)
            this.turnManager = new PlayerTurnManager(nextPlayer);
    }
}