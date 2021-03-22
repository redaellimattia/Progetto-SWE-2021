package it.polimi.ingsw.model;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.token.SoloToken;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private Shop shop = new Shop();
    private MarketDashboard market = new MarketDashboard();
    private ArrayList<Card> leadersDeck = new ArrayList<>();
    private ArrayList<SoloToken> tokensDeck = new ArrayList<SoloToken>();

    public void startGame(){

    }

    public void endGame(){

    }

    public void addPlayer(){

    }

    public void nextRound(){

    }

    public void rollTokens(){

    }
}
