package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

public class PathPositionUpdateMessage extends PlayerUpdateMessage{
    private int position;

    public PathPositionUpdateMessage(String nickname, int position) {
        super(PlayerUpdateType.PATHPOSITION, nickname);
        this.position = position;
    }

    @Override
    public void useMessage(ClientManager clientManager){

    }
}
