package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;


public class InitArrayDepositUpdateMessage extends PlayerUpdateMessage{
    private Resource res;

    public InitArrayDepositUpdateMessage(String nickname, Resource res) {
        super(PlayerUpdateType.INITARRAYDEPOSIT, nickname);
        this.res = res;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getGameStatus().initArrayDeposit(getNickname(),res);
        clientManager.updateViewWithMessage(getNickname()+" has played a leader card with a deposit ability!");
    }
}
