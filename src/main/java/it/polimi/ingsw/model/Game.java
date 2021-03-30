package it.polimi.ingsw.model;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.token.SoloToken;

import java.util.ArrayList;

public class Game {
    private static ArrayList<Player> players = new ArrayList<>();
    private static Shop shop = new Shop();
    private static MarketDashboard market;
    private static ArrayList<Card> leadersDeck = new ArrayList<>();
    private static ArrayList<SoloToken> tokensDeck = new ArrayList<SoloToken>();

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
}
