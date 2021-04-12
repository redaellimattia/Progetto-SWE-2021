package it.polimi.ingsw.model;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.token.SoloToken;

import java.util.ArrayList;

public class Game {
    private static ArrayList<Player> players;
    private static Shop shop;
    private static MarketDashboard market;
    private static ArrayList<Card> leadersDeck;
    private static ArrayList<SoloToken> tokensDeck;

    public Game(ArrayList<Player> players,Shop shop,MarketDashboard market,ArrayList<Card> leadersDeck,ArrayList<SoloToken> tokensDeck) {
        this.players = players;
        this.shop = shop;
        this.market = market;
        this.leadersDeck = leadersDeck;
        this.tokensDeck = tokensDeck;
    }

    public static void startGame(){

    }

    public static void endGame(){

    }

    public static void addPlayer(){

    }

    public static void nextRound(){

    }

    public static void rollTokens(){

    }

    public static Shop getShop() {
        return shop;
    }

    public static MarketDashboard getMarket() {
        return market;
    }
}
