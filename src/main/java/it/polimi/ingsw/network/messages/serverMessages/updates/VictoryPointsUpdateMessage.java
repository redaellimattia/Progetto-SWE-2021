package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

public class VictoryPointsUpdateMessage extends PlayerUpdateMessage{
    private int victoryPoints;

    public VictoryPointsUpdateMessage(String nickname, int victoryPoints) {
        super(PlayerUpdateType.VICTORYPOINTS, nickname);
        this.victoryPoints = victoryPoints;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getGameStatus().updateVictoryPoints(getNickname(),victoryPoints);
    }
}
