package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.MarketDashboard;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

import java.util.ArrayList;

public class InitKnownPlayerMessage extends ServerMessage{
    private ArrayList<PlayerDashboard> players;
    private Deck[][] shopGrid;
    private MarketDashboard market;

    public InitKnownPlayerMessage(ArrayList<PlayerDashboard> players, Deck[][] shopGrid, MarketDashboard market) {
        super(ServerMessageType.INITKNOWNPLAYER);
        this.players = players;
        this.shopGrid = shopGrid;
        this.market = market;
    }

    @Override
    public void useMessage(ClientManager clientManager){

    }
}
