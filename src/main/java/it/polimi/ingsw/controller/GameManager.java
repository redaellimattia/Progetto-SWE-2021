package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.token.SoloToken;

import java.util.ArrayList;


public class GameManager {
    private Game game;
    private PlayerTurnManager turnManager;
    private VaticanReport[] vReports;
    private boolean isSinglePlayer;
    private boolean gameMustEnd;
    private PlayerDashboard lorenzo;

    public GameManager(Game game, PlayerTurnManager turnManager,boolean isSinglePlayer) {
        this.game = game;
        this.turnManager = turnManager;
        vReports = new VaticanReport[3];
        vReports[0] = new VaticanReport(2,5,8);
        vReports[1] = new VaticanReport(3,12,16);
        vReports[2] = new VaticanReport(4,19,24);
        gameMustEnd = false;
        this.isSinglePlayer = isSinglePlayer;
    }

    public Game getGame() {
        return game;
    }

    public PlayerTurnManager getTurnManager() {
        return turnManager;
    }

    public void setGameMustEnd() {
        this.gameMustEnd = true;
    }

    public void checkFaithPath(PlayerDashboard player){
        if(player.getPathPosition() == 8 && !vReports[0].isUsed())
            vReports[0].activateReport(game.getPlayers());
        if(player.getPathPosition() == 16 && !vReports[1].isUsed())
            vReports[1].activateReport(game.getPlayers());
        if(player.getPathPosition() == 24 && !vReports[2].isUsed()) {
            vReports[2].activateReport(game.getPlayers()); //Game must end
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

    public void calculatePoints(PlayerDashboard p){
        //ADD POINT GIVEN BY DEVELOPMENTCARDS HE HAS IN GAME
        for (int i = 0; i < 3; i++) {
            ArrayList<DevelopmentCard> deck = p.getDevCards()[i].getDeck();
            for (int j = 0; j < deck.size(); j++)
                p.addPoints(deck.get(j).getVictoryPoints());
        }
        //POINTS GIVEN FROM THE FAITHPATH
        int pos = p.getPathPosition();
        if (pos >= 3 && pos <= 5)
            p.addPoints(1);
        if (pos >= 6 && pos <= 8)
            p.addPoints(2);
        if (pos >= 9 && pos <= 11)
            p.addPoints(4);
        if (pos >= 12 && pos <= 14)
            p.addPoints(6);
        if (pos >= 15 && pos <= 17)
            p.addPoints(9);
        if (pos >= 18 && pos <= 20)
            p.addPoints(12);
        if (pos >= 21 && pos <= 23)
            p.addPoints(16);
        if (pos == 24)
            p.addPoints(20);

        //POINTS GIVEN FROM LEADERCARDS IN GAME
        for (int i = 0; i < p.getLeaderCards().size(); i++) {
            if (p.getLeaderCards().get(i).isInGame())
                p.addPoints(p.getLeaderCards().get(i).getVictoryPoints());
        }
        //POINTS GIVEN FROM THE LEFT OVER RESOURCES
        int total = 0;
        total += ResourceCount.resCountToInt(p.getTotalResources());
        total += ResourceCount.resCountToInt(p.getAbilityDepositResources());
        p.addPoints(total % 5);
    }
    //MULTIPLAYER: SORT THE LIST OF PLAYER ON THE POINTS THEY HAVE; SOLOPLAYER: EITHER LORENZO WINS OR YOU GET YOUR TOTAL OF POINTS SCORED;
    public void endGame() {
        if(!isSinglePlayer) {
            for (PlayerDashboard p : game.getPlayers()) {
                calculatePoints(p);
            }
            game.getPlayers().sort(Player::compareTo);
        }
        else{
            if(lorenzo.getPathPosition() == 24 || game.getShop().emptyColumn())
                game.setLorenzoWin();
            else
                //SUM THE TOTAL POINTS OF THE ONLY PLAYER
                calculatePoints(game.getPlayers().get(0));
        }
    }

    public void nextRound() {
            PlayerDashboard player = this.turnManager.getPlayer();
            //FIXARE LA CHIAMATA DI ENDGAME NEL CASO DI SINGLE PLAYER
            if (game.getShop().emptyColumn() || player.hasSevenDevCards())//If this player has 7 devCards or a shop column is empty, game must end
                setGameMustEnd();
            if (game.isLastPlayer(player) && gameMustEnd) //Ending Game if last player has finished his turn and gameMustEnd is true
                endGame();
            else {
                if(isSinglePlayer) {
                    SoloToken token = game.pickNextToken();
                    token.useToken(lorenzo, game);
                    this.turnManager = new PlayerTurnManager(player); //da controllare se serve svuotare tutto
                }
                else {
                    PlayerDashboard nextPlayer = game.getNextPlayer(player);
                    if (nextPlayer != null) //Increment Turn
                        this.turnManager = new PlayerTurnManager(nextPlayer);
                }
            }
    }
}