package it.polimi.ingsw.network.messages.actionMessages;

import it.polimi.ingsw.controller.action.leaderAction.PlayLeaderAction;
import it.polimi.ingsw.controller.action.move.MoveFromDepositToLeader;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.MessageType;
import it.polimi.ingsw.network.server.SocketConnection;

public class MoveFromDepositToLeaderMessage extends ActionMessage{
    private int from_deposit;
    private int to_leader;
    private int number;
    /*public MoveFromDepositToLeaderMessage(MessageType type, String nickname, long serverThreadID, ActionType actionType) {
        super(type, nickname, serverThreadID, actionType);
    }*/
    @Override
    public void useMessage(SocketConnection socketConnection){
        MoveFromDepositToLeader action = new MoveFromDepositToLeader(to_leader,from_deposit,number);
        useActionMessage(action,socketConnection);
    }
}
