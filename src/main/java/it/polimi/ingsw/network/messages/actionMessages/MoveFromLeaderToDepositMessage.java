package it.polimi.ingsw.network.messages.actionMessages;

import it.polimi.ingsw.controller.action.move.MoveFromDepositToLeader;
import it.polimi.ingsw.controller.action.move.MoveFromLeaderToDeposit;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.MessageType;
import it.polimi.ingsw.network.server.SocketConnection;

public class MoveFromLeaderToDepositMessage extends ActionMessage{
    private int from_leader;
    private int to_deposit;
    private int number;
    /*public MoveFromLeaderToDepositMessage(MessageType type, String nickname, long serverThreadID, ActionType actionType) {
        super(type, nickname, serverThreadID, actionType);
    }*/
    @Override
    public void useMessage(SocketConnection socketConnection){
        MoveFromLeaderToDeposit action = new MoveFromLeaderToDeposit(from_leader,to_deposit,number);
        useActionMessage(action,socketConnection);
    }
}
