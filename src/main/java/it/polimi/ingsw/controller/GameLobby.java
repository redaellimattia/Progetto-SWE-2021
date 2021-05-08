package it.polimi.ingsw.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.token.DiscardToken;
import it.polimi.ingsw.model.token.SoloToken;
import it.polimi.ingsw.network.server.Server;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
        ArrayList<PlayerDashboard> playerDashboards = new ArrayList<>();
        int playerTurnPosition = 0;
        for (String s: players) {
            playerDashboards.add(createPlayer(s, playerTurnPosition, false));
            playerTurnPosition++;
        }
        Shop shop = new Shop(initShopGrid());
        MarketDashboard market = initMarketDashboard();

        if(singlePlayer){
            // TO-DO: Check that Lorenzo name is not used by player
            playerDashboards.add(createPlayer("Lorenzo", 1, true));
            Game game = new Game(playerDashboards, shop, market, initTokensDeck());
            gameManager = new GameManager(game, new PlayerTurnManager(playerDashboards.get(0)), true);
        }
        else {
            Game game = new Game(playerDashboards, shop, market, null);
            gameManager = new GameManager(game, new PlayerTurnManager(playerDashboards.get(0)), false);
        }
        this.gameStarted = true;
    }

    private PlayerDashboard createPlayer(String nickname, int playerTurnPosition, boolean isLorenzo) {
        Storage storage = new Storage(new CounterTop(Resource.COIN, 0), new CounterTop(Resource.COIN, 0), new CounterTop(Resource.COIN, 0));
        ResourceCount chest = new ResourceCount(0,0,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        for(int i = 0; i < 3; i++) {
            devCards[i] = new DeckDashboard();
        }
        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        return new PlayerDashboard(storage, chest, devCards, leaderCards, playerTurnPosition, nickname, 0, isLorenzo);
    }

    private Deck[][] initShopGrid() {
        // Create empty grid
        Deck[][] shopGrid = new Deck[3][4];
        // Create a matrix of lists of DevelopmentCard (each list will be converted to a deck once completed)
        ArrayList<DevelopmentCard>[][] tempDeckGrid = new ArrayList[3][4];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                tempDeckGrid[i][j] = new ArrayList<>();
            }
        }
        // Insert each card in the correct deck
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        JsonReader json = readJsonFile("/DevCards");
        DeckShop fromJson = gson.fromJson(json, DeckShop.class);
        int row; int col = 0;
        for (DevelopmentCard card: fromJson.getDeck()) {
            row = 3 - card.getLevel();
            switch(card.getColour()) {
                case GREEN: col = 0; break;
                case BLUE: col = 1; break;
                case YELLOW: col = 2; break;
                case PURPLE: col = 3; break;
            }
            tempDeckGrid[row][col].add(card);
        }
        // Shuffle cards in each deck
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                Collections.shuffle(tempDeckGrid[i][j]);
            }
        }
        // Insert decks in shopGrid
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                shopGrid[i][j] = new DeckShop(tempDeckGrid[i][j]);
            }
        }
        return shopGrid;
    }

    private MarketDashboard initMarketDashboard() {
        // Create an empty structure
        MarketMarble[][] structure = new MarketMarble[3][4];
        // Get marbles from JSON and add them to a list
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        JsonReader json = readJsonFile("/Marbles");
        JsonArray marblesJsonArray = gson.fromJson(json, JsonElement.class);
        ArrayList<MarketMarble> marbleList = new ArrayList<>();
        for (JsonElement j: marblesJsonArray) {
            marbleList.add(gson.fromJson(j, MarketMarble.class));
        }
        // Shuffle marbles
        Collections.shuffle(marbleList);

        // Convert list to matrix
        MarketMarble[] marbleArray = new MarketMarble[13];
        marbleArray = marbleList.toArray(marbleArray);

        // Convert array to matrix
        int cur = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                structure[i][j] = marbleArray[cur];
                cur++;
            }
        }
        MarketMarble freeMarble = marbleArray[cur];
        return new MarketDashboard(structure, freeMarble);
    }

    private ArrayList<SoloToken> initTokensDeck() {
        // Get tokens from JSON and add them to a list
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        JsonReader json = readJsonFile("/Tokens");
        JsonArray tokensJsonArray = gson.fromJson(json, JsonElement.class);
        ArrayList<SoloToken> tokenList = new ArrayList<>();
        for (JsonElement j: tokensJsonArray) {
            tokenList.add(gson.fromJson(j, SoloToken.class));
        }
        // Shuffle tokens
        Collections.shuffle(tokenList);
        return tokenList;
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
