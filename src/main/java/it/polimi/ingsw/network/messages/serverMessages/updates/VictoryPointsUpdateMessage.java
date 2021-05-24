package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

public class VictoryPointsUpdateMessage extends PlayerUpdateMessage{
    private final int victoryPoints;

    public VictoryPointsUpdateMessage(String nickname, int victoryPoints) {
        super(PlayerUpdateType.VICTORYPOINTS, nickname);
        this.victoryPoints = victoryPoints;
    }

    /**
     * Updates the victoryPoints of the player with the given nickname
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getGameStatus().updateVictoryPoints(getNickname(),victoryPoints);
    }
}
