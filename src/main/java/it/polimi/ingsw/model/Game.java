package it.polimi.ingsw.model;
import it.polimi.ingsw.model.token.SoloToken;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private ArrayList<PlayerDashboard> players;
    private Shop shop;
    private MarketDashboard market;
    private ArrayList<SoloToken> tokensDeck;
    private ArrayList<SoloToken> discardedTokens;
    private boolean lorenzoWin;

    public Game(ArrayList<PlayerDashboard> players,Shop shop,MarketDashboard market,ArrayList<SoloToken> tokensDeck) {
        this.players = players;
        this.shop = shop;
        this.market = market;
        this.tokensDeck = tokensDeck;
        this.discardedTokens = new ArrayList<>();
        this.lorenzoWin = false;
    }

    public boolean isLastPlayer(PlayerDashboard player){
        return player.equals(players.get(players.size() - 1));
    }

    public PlayerDashboard getLorenzo(){
        for (PlayerDashboard p: players) {
            if(p.isLorenzo())
                return p;
        }
        return null;
    }
    public void setLorenzoWin(){
        this.lorenzoWin = true;
    }

    public void rollTokens(){
        tokensDeck.addAll(discardedTokens);
        Collections.shuffle(tokensDeck);
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

    public Shop getShop() {
        return shop;
    }

    public MarketDashboard getMarket() {
        return market;
    }

    public ArrayList<PlayerDashboard> getPlayers() {
        return players;
    }

    public SoloToken pickNextToken() {
        SoloToken picked = tokensDeck.get(0);
        discardedTokens.add(tokensDeck.remove(0));
        return picked;
    }
}
