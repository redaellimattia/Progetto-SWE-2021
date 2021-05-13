package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

import java.util.ArrayList;

public class EndSinglePlayerGameMessage extends ServerMessage {
    private boolean lorenzoWin;
    private ArrayList<PlayerPoints> scoreboard;

    public EndSinglePlayerGameMessage(boolean lorenzoWin,ArrayList<PlayerDashboard> players) {
        super(ServerMessageType.ENDSINGLEPLAYERGAME);
        this.lorenzoWin = lorenzoWin;
        scoreboard = new ArrayList<>();
        for(PlayerDashboard p:players)
            scoreboard.add(new PlayerPoints(p.getNickname(),p.getPoints()));
    }

    @Override
    public void useMessage(ClientManager clientManager){

    }
}
