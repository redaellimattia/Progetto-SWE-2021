package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.client.PlayerPoints;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

import java.util.ArrayList;

public class EndSinglePlayerGameMessage extends ServerMessage {
    private boolean lorenzoWin;
    private PlayerPoints scoreboard;

    public EndSinglePlayerGameMessage(boolean lorenzoWin,PlayerDashboard player) {
        super(ServerMessageType.ENDSINGLEPLAYERGAME);
        this.lorenzoWin = lorenzoWin;
        scoreboard = new PlayerPoints(player.getNickname(),player.getPoints());
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getView().endGame(lorenzoWin,scoreboard);
    }
}
