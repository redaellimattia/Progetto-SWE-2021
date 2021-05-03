package it.polimi.ingsw.network.messages.clientMessages.actionMessages;


import it.polimi.ingsw.controller.action.move.MoveFromDepositToLeader;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.server.SocketConnection;

public class MoveFromDepositToLeaderMessage extends ActionMessage{
    private int from_deposit;
    private int to_leader;
    private int number;
    public MoveFromDepositToLeaderMessage(String nickname, long serverThreadID) {
        super(ActionType.MOVEFROMDEPOSITTOLEADER, nickname, serverThreadID);
    }

    /**
     * Move of resources from a deposit to a leader deposit
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection){
        MoveFromDepositToLeader action = new MoveFromDepositToLeader(to_leader,from_deposit,number);
        useSideActionMessage(action,socketConnection);
    }
}
