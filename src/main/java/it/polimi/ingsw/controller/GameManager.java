package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

public class GameManager {
    private Game game;
    private PlayerTurnManager turnManager;

    public GameManager(Game game, PlayerTurnManager turnManager) {
        this.game = game;
        this.turnManager = turnManager;
    }

    public void initGame() {

    }

    public void nextRound() {

    }
}

