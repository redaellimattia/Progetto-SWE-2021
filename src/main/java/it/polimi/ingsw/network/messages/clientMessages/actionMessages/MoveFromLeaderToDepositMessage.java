package it.polimi.ingsw.network.messages.clientMessages.actionMessages;


import it.polimi.ingsw.controller.action.move.MoveFromLeaderToDeposit;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.messages.serverMessages.DoneMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.logging.Level;

public class MoveFromLeaderToDepositMessage extends ActionMessage{
    private final int from_leader;
    private final int to_deposit;
    private final int number;

    public MoveFromLeaderToDepositMessage(String nickname, long serverThreadID, int from_leader, int to_deposit, int number) {
        super(ActionType.MOVEFROMLEADERTODEPOSIT, nickname, serverThreadID);
        this.from_leader = from_leader;
        this.to_deposit = to_deposit;
        this.number = number;
    }

    /**
     * move resources from a leaderDeposit to a deposit
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        MoveFromLeaderToDeposit action = new MoveFromLeaderToDeposit(from_leader,to_deposit,number);
        useSideActionMessage(action,socketConnection, serverLobby);
        Server.LOGGER.log(Level.INFO,"Move From Leader Action arrived!");
        serverLobby.sendToAll(new DoneMessage().serialize());
    }
}
