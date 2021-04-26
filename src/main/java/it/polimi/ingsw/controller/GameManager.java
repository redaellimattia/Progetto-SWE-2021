package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerDashboard;


public class GameManager {
    private Game game;
    private PlayerTurnManager turnManager;
    private VaticanReport[] vReports;
    private boolean gameMustEnd;

    public GameManager(Game game, PlayerTurnManager turnManager) {
        this.game = game;
        this.turnManager = turnManager;
        vReports = new VaticanReport[3];
        vReports[0] = new VaticanReport(2,5,8);
        vReports[1] = new VaticanReport(3,12,16);
        vReports[2] = new VaticanReport(4,19,24);
        gameMustEnd = false;
    }

    public void setGameMustEnd() {
        this.gameMustEnd = true;
    }

    public void checkFaithPath(PlayerDashboard player){
        if(player.getPathPosition() == 8 && !vReports[0].isUsed())
            vReports[0].activateReport(game.getPlayers(),player);
        if(player.getPathPosition() == 12 && !vReports[1].isUsed())
            vReports[1].activateReport(game.getPlayers(),player);
        if(player.getPathPosition() == 19 && !vReports[2].isUsed()) {
            vReports[2].activateReport(game.getPlayers(), player); //Game must end
            setGameMustEnd();
        }
    }

    /**
     *
     * @param player the player that will be excluded from the path position update
     */
    public void updateOpponentsPathPosition(PlayerDashboard player) {
        for(PlayerDashboard i: game.getPlayers()) {
            if(!i.getNickName().equals(player.getNickName())) {
                i.updatePathPosition();
                checkFaithPath(i);
            }
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

        if(player.hasSevenDevCards()) //If this player has 7 devCards, game must end
            setGameMustEnd();

        if(game.isLastPlayer(player)&&gameMustEnd) //Ending Game if last player has finished his turn and gameMustEnd is true
            endGame();

        PlayerDashboard nextPlayer = game.getNextPlayer(player);
        if(nextPlayer!=null) //Increment Turn
            this.turnManager = new PlayerTurnManager(nextPlayer);
    }
}