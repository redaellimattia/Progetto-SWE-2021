package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.DeckDashboard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public class GameLobby {
    private ArrayList<String> players;
    private GameManager gameManager;
    private final long serverThreadID;
    private final int numberOfPlayers;
    private int readyPlayers;
    private boolean gameStarted;

    public GameLobby(long serverThreadID,int numberOfPlayers) {
        this.players = new ArrayList<>();
        this.serverThreadID = serverThreadID;
        this.numberOfPlayers = numberOfPlayers;
        this.readyPlayers = 0;
        this.gameStarted = false;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public long getServerThreadID() {
        return serverThreadID;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void addPlayer(String nickname) {
        this.players.add(nickname);
    }
    /**
     *
     * @param chosenNickname chosenNickname by the view while trying to access the game
     * @return true if the nickname isn't used yet
     */
    public boolean checkNickname(String chosenNickname) {
        for (String nickname: players)
            if(chosenNickname.equals(nickname))
                return false;
        return true;
    }

    public void removePlayer(String nickname){
        players.remove(nickname);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void initGame(boolean singlePlayer) {
        if(singlePlayer){
            ArrayList<PlayerDashboard> p = new ArrayList<>();
            p.add(new PlayerDashboard(null,null,new DeckDashboard[3],new ArrayList<>(),0,players.get(0),0,false));
            Game game = new Game(p,null,null,null);
            gameManager = new GameManager(game,new PlayerTurnManager(p.get(0)),true);
        }
        //else
            //games.add(new GameManager(new Game(players,...),null));
        this.gameStarted = true;
    }

    public boolean readyToStartGame(){
        return numberOfPlayers == readyPlayers;
    }
    //LETTURA DA FILE JSON
}
