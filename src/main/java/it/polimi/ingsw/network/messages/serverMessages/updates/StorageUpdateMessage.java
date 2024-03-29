package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

public class StorageUpdateMessage extends PlayerUpdateMessage{
    private final CounterTop row;

    public StorageUpdateMessage(PlayerUpdateType type,String nickname, CounterTop row) {
        super(type, nickname);
        this.row = row;
    }

    /**
     * Updates a given row of the storage
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        switch(getPlayerUpdateType()){
            case FIRSTROW: clientManager.getGameStatus().updateFirstRow(getNickname(),row);
                            break;
            case SECONDROW: clientManager.getGameStatus().updateSecondRow(getNickname(),row);
                            break;
            case THIRDROW:  clientManager.getGameStatus().updateThirdRow(getNickname(),row);
                            break;
        }
    }
}
