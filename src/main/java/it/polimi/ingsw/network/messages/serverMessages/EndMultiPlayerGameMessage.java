package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.client.PlayerPoints;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

import java.util.ArrayList;

public class EndMultiPlayerGameMessage extends ServerMessage{
    private ArrayList<PlayerPoints> scoreboard;

    public EndMultiPlayerGameMessage(ArrayList<PlayerDashboard> players) {
        super(ServerMessageType.ENDMULTIPLAYERGAME);
        scoreboard = new ArrayList<>();
        for(PlayerDashboard p:players)
            scoreboard.add(new PlayerPoints(p.getNickname(),p.getPoints()));
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getView().endGame(scoreboard);
    }
}
