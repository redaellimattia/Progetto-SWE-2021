package it.polimi.ingsw.network.messages.clientMessages.actionMessages;


import it.polimi.ingsw.controller.action.move.MoveFromDepositToLeader;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

public class MoveFromDepositToLeaderMessage extends ActionMessage{
    private final int from_deposit;
    private final int to_leader;
    private final int number;
    public MoveFromDepositToLeaderMessage(String nickname, long serverThreadID, int from_deposit,int to_leader, int number) {
        super(ActionType.MOVEFROMDEPOSITTOLEADER, nickname, serverThreadID);
        this.from_deposit = from_deposit;
        this.to_leader = to_leader;
        this.number = number;
    }

    /**
     * Move of resources from a deposit to a leader deposit
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        MoveFromDepositToLeader action = new MoveFromDepositToLeader(to_leader,from_deposit,number);
        useSideActionMessage(action,socketConnection, serverLobby);
    }
}
