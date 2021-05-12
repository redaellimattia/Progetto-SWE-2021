package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.MarketDashboard;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Shop;

import java.util.ArrayList;

public class ClientGameStatus {
    /**
     * Ordered as on Server
     */
    private ArrayList<PlayerDashboard> players;
    private Shop shop;
    private MarketDashboard market;

    public ClientGameStatus(ArrayList<PlayerDashboard> players, Shop shop, MarketDashboard market) {
        this.players = players;
        this.shop = shop;
        this.market = market;
    }

    public ArrayList<PlayerDashboard> getPlayers() {
        return players;
    }

    public Shop getShop() {
        return shop;
    }

    public MarketDashboard getMarket() {
        return market;
    }

    public void setPlayers(ArrayList<PlayerDashboard> players) {
        this.players = players;
    }

    public void setShop(Deck[][] shopGrid) {
        this.shop.setShopGrid(shopGrid);
    }

    public void setMarket(MarketDashboard market) {
        this.market = market;
    }

    public PlayerDashboard getClientDashboard(String nickname){
        for(PlayerDashboard p:players){
            if(p.getNickname().equals(nickname))
                return p;
        }
        return null;
    }
}
