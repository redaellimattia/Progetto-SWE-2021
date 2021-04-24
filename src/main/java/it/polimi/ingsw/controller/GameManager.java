package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerDashboard;

import java.util.ArrayList;

public class GameManager {
    private Game game;
    private PlayerTurnManager turnManager;
    private VaticanReport[] vReports;
    private boolean gameMustEnd;

    public GameManager(Game game, PlayerTurnManager turnManager) {
        this.game = game;
        this.turnManager = turnManager;
        vReports = new VaticanReport[3];
        vReports[0] = new VaticanReport(false,2,5,8);
        vReports[1] = new VaticanReport(false,3,12,16);
        vReports[2] = new VaticanReport(false,4,19,24);
        gameMustEnd = false;
    }

    public void endingGame() {
        this.gameMustEnd = true;
    }

    public void checkFaithPath(PlayerDashboard player){
        if(player.getPathPosition() == 8 && !vReports[0].isUsed())
            vReports[0].activateReport(game.getPlayers(),player);
        if(player.getPathPosition() == 12 && !vReports[1].isUsed())
            vReports[1].activateReport(game.getPlayers(),player);
        if(player.getPathPosition() == 19 && !vReports[2].isUsed()) {
            vReports[2].activateReport(game.getPlayers(), player); //Game must end
            endingGame();
        }
    }
    public void initGame() { //Distribuire risorse + creaz shop etc

    }

    public void createAction(Action action){
        this.turnManager.setAction(action);
    }

    public void endGame(){
        //game.setScoreboard();
    }
    public void nextRound() {
        PlayerDashboard player = this.turnManager.getPlayer();

        if(game.isLastPlayer(player)&&gameMustEnd) //Ending Game
            endGame();

        PlayerDashboard nextPlayer = game.getNextPlayer(player);
        if(nextPlayer!=null) //Increment Turn
            this.turnManager = new PlayerTurnManager(nextPlayer);
    }
}