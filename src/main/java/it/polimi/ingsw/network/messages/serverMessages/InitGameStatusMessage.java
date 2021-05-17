package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.MarketDashboard;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Shop;
import it.polimi.ingsw.model.VaticanReport;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

import java.util.ArrayList;

public class InitGameStatusMessage extends ServerMessage{
    private ArrayList<PlayerDashboard> players;
    private Shop shop;
    private MarketDashboard market;
    private VaticanReport[] vReports;

    public InitGameStatusMessage(ArrayList<PlayerDashboard> players, Shop shop, MarketDashboard market,VaticanReport[] vReports) {
        super(ServerMessageType.INITGAMESTATUS);
        this.players = players;
        this.shop = shop;
        this.market = market;
        this.vReports = vReports;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.initGameStatus(players,shop,market,vReports);
    }
}
