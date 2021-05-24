package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

public class PathPositionUpdateMessage extends PlayerUpdateMessage{
    private final int position;

    public PathPositionUpdateMessage(String nickname, int position) {
        super(PlayerUpdateType.PATHPOSITION, nickname);
        this.position = position;
    }

    /**
     * Updates the position on the faith path of the player with the given nickname
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getGameStatus().updatePathPosition(getNickname(),position);
    }
}
