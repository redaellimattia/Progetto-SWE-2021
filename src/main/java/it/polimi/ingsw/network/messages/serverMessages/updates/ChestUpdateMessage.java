package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

public class ChestUpdateMessage extends PlayerUpdateMessage{
    private ResourceCount chest;

    public ChestUpdateMessage(String nickname, ResourceCount chest) {
        super(PlayerUpdateType.CHESTUPDATE, nickname);
        this.chest = chest;
    }

    @Override
    public void useMessage(ClientManager clientManager){

    }
}
