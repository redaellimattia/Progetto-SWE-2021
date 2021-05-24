package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

public class ResourceCountUpdateMessage extends PlayerUpdateMessage{
    private final ResourceCount resourceCount;

    public ResourceCountUpdateMessage(PlayerUpdateType type,String nickname, ResourceCount resourceCount) {
        super(type, nickname);
        this.resourceCount = resourceCount;
    }

    /**
     * Updates the chest or bufferProduction of the player with the given nickname
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        switch(getPlayerUpdateType()){
            case CHEST: clientManager.getGameStatus().updateChest(getNickname(),resourceCount);
                        break;
            case BUFFERPRODUCTION: clientManager.getGameStatus().updateBufferProduction(getNickname(),resourceCount);
                                   break;
        }
    }
}
