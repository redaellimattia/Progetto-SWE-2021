package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

public class StorageUpdateMessage extends PlayerUpdateMessage{
    private CounterTop row;

    public StorageUpdateMessage(PlayerUpdateType type,String nickname, CounterTop row) {
        super(type, nickname);
        this.row = row;
    }

    @Override
    public void useMessage(ClientManager clientManager){

    }
}
