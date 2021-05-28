package it.polimi.ingsw.model;
import it.polimi.ingsw.model.token.SoloToken;
import it.polimi.ingsw.network.server.Observer;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private ArrayList<PlayerDashboard> players;
    private Shop shop;
    private MarketDashboard market;
    private ArrayList<SoloToken> tokensDeck;
    private ArrayList<SoloToken> discardedTokens;
    private boolean lorenzoWin;
    private VaticanReport[] vReports;
    private transient Observer observer;

    /**
     * Adds reference to the observer
     * @param observer ServerLobby that is observing the Player
     */
    public void addObserver(Observer observer) {
        this.observer = observer;
        this.vReports[0].addObserver(observer);
        this.vReports[1].addObserver(observer);
        this.vReports[2].addObserver(observer);
    }

    /**
     * Remove reference to the observer
     * @param observer ServerLobby that is observing the Player
     */
    public void removeObserver(Observer observer) {
        this.vReports[0].removeObserver();
        this.vReports[1].removeObserver();
        this.vReports[2].removeObserver();
    }

    public Game(ArrayList<PlayerDashboard> players,Shop shop,MarketDashboard market,ArrayList<SoloToken> tokensDeck) {
        this.players = players;
        this.shop = shop;
        this.market = market;
        this.tokensDeck = tokensDeck;
        this.discardedTokens = new ArrayList<>();
        this.lorenzoWin = false;
        vReports = new VaticanReport[3];
        vReports[0] = new VaticanReport(2,5,8);
        vReports[1] = new VaticanReport(3,12,16);
        vReports[2] = new VaticanReport(4,19,24);

    }

    public boolean isLastPlayer(PlayerDashboard player){
        return player.equals(players.get(players.size() - 1));
    }

    public boolean isLorenzoWin() {
        return lorenzoWin;
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

    public VaticanReport[] getvReports() {
        return vReports;
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

    public ArrayList<SoloToken> getTokensDeck() {
        return tokensDeck;
    }

    public ArrayList<SoloToken> getDiscardedTokens() {
        return discardedTokens;
    }

    /**
     *  check whether the  VaticanReports need to be activated || the game must end
     * @param player the player whom we need to check the FaithPath position
     */
    public void checkFaithPath(PlayerDashboard player){
        if(player.getPathPosition() == 8 && !vReports[0].isUsed())
            vReports[0].activateReport(players);
        if(player.getPathPosition() == 16 && !vReports[1].isUsed())
            vReports[1].activateReport(players);
        if(player.getPathPosition() == 24 && !vReports[2].isUsed()) {
            vReports[2].activateReport(players); //Game must end
            observer.setGameMustEnd();
        }
    }
}
