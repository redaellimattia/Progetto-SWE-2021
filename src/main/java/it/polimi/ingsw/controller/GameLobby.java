package it.polimi.ingsw.controller;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.Server;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

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

    public ArrayList<LeaderCard> getFourLeaders(){
        return null;
    }

    /**
     * method used in pregame to give a faith point to the 3rd and 4th player, if there are any
     */
    public void addInitialFaith(){
        ArrayList<PlayerDashboard> players = gameManager.getGame().getPlayers();
        if(numberOfPlayers != 1){
            if(numberOfPlayers == 3)
                players.get(2).updatePathPosition();
            if(numberOfPlayers == 4){
                players.get(2).updatePathPosition();
                players.get(3).updatePathPosition();
            }
        }
    }

    public void preGame(String nickname, ArrayList<Resource> chosen, ArrayList<LeaderCard> chosenLeaders){
        CounterTop chosen1;
        CounterTop chosen2;
        for (PlayerDashboard p : gameManager.getGame().getPlayers()) {
            if(p.getNickname().equals(nickname)) {
                if (chosen.size() == 1) {
                    chosen1 = new CounterTop(chosen.get(0),1);
                    p.getStorage().setFirstRow(chosen1);
                }
                if (chosen.size() == 2) {
                    if(chosen.get(0).equals(chosen.get(1))){
                        chosen1 = new CounterTop(chosen.get(0), 2);
                        p.getStorage().setSecondRow(chosen1);
                    }
                    else{
                        chosen1 = new CounterTop(chosen.get(0), 1);
                        p.getStorage().setFirstRow(chosen1);
                        chosen2 = new CounterTop(chosen.get(1), 1);
                        p.getStorage().setSecondRow(chosen2);
                    }
                }
                p.getLeaderCards().add(chosenLeaders.get(0));
                p.getLeaderCards().add(chosenLeaders.get(1));
            }
        }
    }

    public void addReadyPlayer(){
        this.readyPlayers++;
    }

    /**
     *
     * @param filename DevCards, LeaderCards, Tokens
     * @return JsonReader of the specified file can be null
     */
    public JsonReader readJsonFile(String filename){
        JsonReader reader = null;
        String configFilePath = "src/main/resources/json";
        try {
             reader = new JsonReader(new FileReader(configFilePath +filename+".json"));
        }catch(IOException e){
            Server.LOGGER.log(Level.SEVERE,"Can't read file: "+ configFilePath +filename+".json "+e.getMessage());
        }
        return reader;
    }
}
