package it.polimi.ingsw.model;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.token.SoloToken;

import java.util.ArrayList;

public class Game {
    private static ArrayList<PlayerDashboard> players;
    private static Shop shop;
    private static MarketDashboard market;
    private static ArrayList<Card> leadersDeck;
    private static ArrayList<SoloToken> tokensDeck;

    public Game(ArrayList<PlayerDashboard> players,Shop shop,MarketDashboard market,ArrayList<Card> leadersDeck,ArrayList<SoloToken> tokensDeck) {
        this.players = players;
        this.shop = shop;
        this.market = market;
        this.leadersDeck = leadersDeck;
        this.tokensDeck = tokensDeck;
    }

    public boolean isLastPlayer(PlayerDashboard player){
        return player.equals(players.get(players.size() - 1));
    }

    public static void startGame(){

    }

    public static void endGame(){

    }

    public static void rollTokens(){

    }

    public PlayerDashboard getNextPlayer(PlayerDashboard player){
        for(int i=0;i<players.size();i++){
            if(players.get(i).equals(player)&&i<players.size()-1)
                return players.get(i+1);
            else
                if(players.get(i).equals(player))
                    return players.get(0);
        }
        return null;
    }
    
    public static Shop getShop() {
        return shop;
    }

    public static MarketDashboard getMarket() {
        return market;
    }

    public static ArrayList<PlayerDashboard> getPlayers() {
        return players;
    }

    public static SoloToken pickNextToken() {
        SoloToken picked = tokensDeck.get(0);
        tokensDeck.remove(0);
        return picked;
    }
}
