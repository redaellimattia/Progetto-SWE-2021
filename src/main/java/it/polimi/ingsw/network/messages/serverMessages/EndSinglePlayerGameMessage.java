package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.client.PlayerPoints;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

import java.util.ArrayList;

public class EndSinglePlayerGameMessage extends ServerMessage {
    private boolean lorenzoWin;
    private int playerVictoryPoints;

    public EndSinglePlayerGameMessage(boolean lorenzoWin, int playerVictoryPoints) {
        super(ServerMessageType.ENDSINGLEPLAYERGAME);
        this.lorenzoWin = lorenzoWin;
        this.playerVictoryPoints = playerVictoryPoints;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getView().endGame(lorenzoWin,playerVictoryPoints);
    }
}
