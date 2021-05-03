package it.polimi.ingsw.network.messages.clientMessages.actionMessages;


import it.polimi.ingsw.controller.action.move.MoveFromLeaderToDeposit;
import it.polimi.ingsw.network.server.SocketConnection;

public class MoveFromLeaderToDepositMessage extends ActionMessage{
    private int from_leader;
    private int to_deposit;
    private int number;
    /*public MoveFromLeaderToDepositMessage(ClientMessageType type, String nickname, long serverThreadID, ActionType actionType) {
        super(type, nickname, serverThreadID, actionType);
    }*/

    /**
     * move resources from a leaderDeposit to a deposit
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection){
        MoveFromLeaderToDeposit action = new MoveFromLeaderToDeposit(from_leader,to_deposit,number);
        useSideActionMessage(action,socketConnection);
    }
}