package it.polimi.ingsw.model;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.token.SoloToken;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private ArrayList<PlayerDashboard> players;
    private Shop shop;
    private static MarketDashboard market; //DA FIXARE
    private ArrayList<SoloToken> tokensDeck;
    private ArrayList<SoloToken> discardedTokens;

    public Game(ArrayList<PlayerDashboard> players,Shop shop,MarketDashboard market,ArrayList<SoloToken> tokensDeck) {
        this.players = players;
        this.shop = shop;
        this.market = market;
        this.tokensDeck = tokensDeck;
        this.discardedTokens = new ArrayList<>();
    }

    public boolean isLastPlayer(PlayerDashboard player){
        return player.equals(players.get(players.size() - 1));
    }

    public void startGame(){

    }

    public void endGame(){

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

    public static MarketDashboard getMarket() {
        return market;
    } //DA FIXARE PER TOGLIERE STATIC

    public ArrayList<PlayerDashboard> getPlayers() {
        return players;
    }

    public SoloToken pickNextToken() {
        SoloToken picked = tokensDeck.get(0);
        discardedTokens.add(tokensDeck.remove(0));
        return picked;
    }
}
