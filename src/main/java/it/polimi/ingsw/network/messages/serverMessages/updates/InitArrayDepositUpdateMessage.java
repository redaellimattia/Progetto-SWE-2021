package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;


public class InitArrayDepositUpdateMessage extends PlayerUpdateMessage{
    private CounterTop newArrayDeposit;

    public InitArrayDepositUpdateMessage(String nickname, CounterTop newArrayDeposit) {
        super(PlayerUpdateType.INITARRAYDEPOSIT, nickname);
        this.newArrayDeposit = newArrayDeposit;
    }

    @Override
    public void useMessage(ClientManager clientManager){

    }
}
