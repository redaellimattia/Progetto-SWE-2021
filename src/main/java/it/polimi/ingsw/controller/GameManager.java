package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.token.SoloToken;
import it.polimi.ingsw.network.server.Observer;

import java.util.ArrayList;


public class GameManager {
    private Game game;
    private PlayerTurnManager turnManager;
    private boolean isSinglePlayer;
    private boolean gameMustEnd;
    private boolean gameEnded;
    private Observer observer;
    /**
     * Adds reference to the observer
     * @param observer ServerLobby that is observing the Player
     */
    public void addObserver(Observer observer) {
        this.observer = observer;
        this.game.addObserver(observer);
    }

    /**
     * Remove reference to the observer
     * @param observer ServerLobby that is observing the Player
     */
    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    public GameManager(Game game, PlayerTurnManager turnManager, boolean isSinglePlayer) {
        this.game = game;
        this.turnManager = turnManager;
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

    public boolean isGameEnded() {
        return gameEnded;
    }



    /**
     * Add a faith point to each player when a player discard resources from the market
     *
     * @param player the player that will be excluded from the path position update
     */
    public void updateOpponentsPathPosition(PlayerDashboard player) {
        for(PlayerDashboard p: game.getPlayers()) {
            if(!p.getNickname().equals(player.getNickname())) {
                p.updatePathPosition();
            }
        }
    }

    /**
     *
     * @param p the player we want to calculate the total points scored
     */
    public void calculatePoints(PlayerDashboard p){
        int points = 0;
        //POINTS GIVEN FROM THE FAITHPATH
        int pos = p.getPathPosition();
        if (pos >= 3 && pos <= 5)
            points += 1;
        if (pos >= 6 && pos <= 8)
            points += 2;
        if (pos >= 9 && pos <= 11)
            points += 4;
        if (pos >= 12 && pos <= 14)
            points += 6;
        if (pos >= 15 && pos <= 17)
            points += 9;
        if (pos >= 18 && pos <= 20)
            points += 12;
        if (pos >= 21 && pos <= 23)
            points += 16;
        if (pos >= 24)
            points += 20;

        //POINTS GIVEN FROM THE LEFT OVER RESOURCES
        int total = 0;
        total += ResourceCount.resCountToInt(p.getTotalResources());
        total += ResourceCount.resCountToInt(p.getAbilityDepositResources());
        points += total % 5;

        p.addVictoryPoints(points);
    }

    /**
     * MULTIPLAYER: SORT THE LIST OF PLAYER ON THE POINTS THEY HAVE;
     * SOLOPLAYER: EITHER LORENZO WINS OR YOU GET YOUR TOTAL OF POINTS SCORED;
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
        gameEnded = true;
        observer.updateEndGame();
    }

    /**
     * Upon being called, switch the PlayerTurnManager to the next player on the list of players
     */
    public void nextRound(boolean singlePlayerFirstRound) {
            PlayerDashboard player = this.turnManager.getPlayer();
            if ((game.getShop().emptyColumn() && isSinglePlayer ) || player.hasSevenDevCards())//If this player has 7 devCards or a shop column is empty, game must end
                setGameMustEnd();
            //this check should work. Lorenzo is in the player list, so the passive changes are made correctly and setGameMustEnd is called correctly.
            //In the case of single player, it doesn't matter which player is last, it will just end the game
            if (((game.isLastPlayer(player)) || isSinglePlayer) && gameMustEnd) //Ending Game if last player has finished his turn and gameMustEnd is true
                endGame();
            else {
                if(isSinglePlayer) {
                    if(player.isPlaying()&&!singlePlayerFirstRound) {
                        SoloToken token = game.pickNextToken();
                        token.useToken(game.getLorenzo(), game, observer);
                    }
                    this.turnManager = new PlayerTurnManager(player); //da controllare se serve svuotare tutto
                }
                else {
                    PlayerDashboard nextPlayer = player;
                    if(isAnyoneConnected()) {
                        do { //Searching nextPlayer that is playing
                            nextPlayer = game.getNextPlayer(nextPlayer);
                        } while (nextPlayer == null || !nextPlayer.isPlaying());
                        this.turnManager = new PlayerTurnManager(nextPlayer);
                    }
                }
            }
    }

    /**
     * @return true if there's at least one player connected playing the game
     */
    public boolean isAnyoneConnected(){
        for(PlayerDashboard p:game.getPlayers())
            if (!p.isLorenzo()&&p.isPlaying())
                return true;
        return false;
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
     * Sets knowPlayer as playing
     *
     * @param playerPosition position of the player in the arraylist
     */
    public void playerComeback(int playerPosition){
        game.getPlayers().get(playerPosition).setPlaying(true);
    }
}