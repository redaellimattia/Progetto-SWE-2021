package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;

import java.util.ArrayList;

public class ArrayDepositUpdateMessage extends PlayerUpdateMessage{
    private ArrayList<CounterTop> arrayDeposit;

    public ArrayDepositUpdateMessage(String nickname, ArrayList<CounterTop> arrayDeposit) {
        super(PlayerUpdateType.ARRAYDEPOSIT, nickname);
        this.arrayDeposit = arrayDeposit;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getGameStatus().updateArrayDeposit(getNickname(),arrayDeposit);
    }
}
