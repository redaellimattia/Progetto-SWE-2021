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

    /**
     *
     * @return the game which this object is referring
     */
    public Game getGame() {
        return game;
    }

    /**
     *
     * @return the actual PlayerTurnManager of this round
     */
    public PlayerTurnManager getTurnManager() {
        return turnManager;
    }

    /**
     * set the boolean attribute gameMustEnd as true to indicate that this is the last round of the game
     */
    public void setGameMustEnd() {
        this.gameMustEnd = true;
    }

    /**
     *  check whether the  VaticanReports need to be activated || the game must end
     * @param player the player whom we need to check the FaithPath position
     */
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
     *  Add a faith point to each player when a player discard resources from the market
     * @param player the player that will be excluded from the path position update
     */
    public void updateOpponentsPathPosition(PlayerDashboard player) {
        for(PlayerDashboard i: game.getPlayers()) {
            if(!i.getNickname().equals(player.getNickname())) {
                i.updatePathPosition();
                checkFaithPath(i);
            }
        }
    }

    public void initGame() { //Distribuire risorse + creaz shop etc

    }

    /**
     *
     * @param p the player we want to calculate the total points scored
     */
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

    /**
     * MULTIPLAYER: SORT THE LIST OF PLAYER ON THE POINTS THEY HAVE; SOLOPLAYER: EITHER LORENZO WINS OR YOU GET YOUR TOTAL OF POINTS SCORED;
     */

    public void endGame() {
        if(!isSinglePlayer) {
            for (PlayerDashboard p : game.getPlayers()) {
                calculatePoints(p);
            }
            game.getPlayers().sort(Player::compareTo);
        }
        else{
            if(game.getLorenzo().getPathPosition() == 24 || game.getShop().emptyColumn())
                game.setLorenzoWin();
            else
                //SUM THE TOTAL POINTS OF THE ONLY PLAYER
                calculatePoints(game.getPlayers().get(0));
        }
    }

    /**
     * upon being called, switch the PlayerTurnManager to the next player on the list of players
     */
    public void nextRound() {
            PlayerDashboard player = this.turnManager.getPlayer();
            if (game.getShop().emptyColumn() || player.hasSevenDevCards())//If this player has 7 devCards or a shop column is empty, game must end
                setGameMustEnd();
            //this check should work. Lorenzo is in the player list, so the passive changes are made correctly and setGameMustEnd is called correctly.
            //In the case of single player, it doesn't matter which player is last, it will just end the game
            if (((game.isLastPlayer(player)) || isSinglePlayer) && gameMustEnd) //Ending Game if last player has finished his turn and gameMustEnd is true
                endGame();
            else {
                if(isSinglePlayer) {
                    if(player.isPlaying()) {
                        SoloToken token = game.pickNextToken();
                        token.useToken(game.getLorenzo(), game);
                        this.turnManager = new PlayerTurnManager(player); //da controllare se serve svuotare tutto
                    }
                }
                else {
                    PlayerDashboard nextPlayer = player;
                    do { //Searching nextPlayer that is playing
                        nextPlayer = game.getNextPlayer(nextPlayer);
                    }while(nextPlayer != null&&nextPlayer.isPlaying());
                    this.turnManager = new PlayerTurnManager(nextPlayer);
                }
            }
    }

    /**
     *
     * @param nickName nickName of the player that is trying to reconnect
     * @return true if this player was in Game
     */
    public int wasPlaying(String nickName){
        ArrayList<PlayerDashboard> players = game.getPlayers();
        for(int i=0;i<players.size();i++) {
            PlayerDashboard p = players.get(i);
            if (p.getNickname().equals(nickName) && !p.isPlaying())
                return i;
        }
        return -1;
    }

    /**
     *
     * @param playerPosition position of the player in the arraylist
     * @param nickname nickname of the player
     */
    public void playerComeback(int playerPosition,String nickname){
        game.getPlayers().get(playerPosition).setPlaying(true);
    }
}