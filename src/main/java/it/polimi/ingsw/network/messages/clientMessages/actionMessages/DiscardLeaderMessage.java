package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.leaderAction.DiscardLeaderAction;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.messages.serverMessages.DoneMessage;
import it.polimi.ingsw.network.messages.serverMessages.PrintMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.logging.Level;

public class DiscardLeaderMessage extends ActionMessage{
    private final LeaderCard card;

    public DiscardLeaderMessage(String nickname, long serverThreadID,LeaderCard card) {
        super(ActionType.DISCARDLEADER, nickname, serverThreadID);
        this.card = card;
    }

    /**
     * Create a DiscardLeaderAction and uses it.
     * @param socketConnection the connection from which the message has arrived
     * @param serverLobby serverLobby of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        DiscardLeaderAction action = new DiscardLeaderAction(card);
        Server.LOGGER.log(Level.INFO,"LobbyID: "+serverLobby.getLobbyId()+": Discard Leader Action arrived from: "+getNickname());
        if(useSideActionMessage(action, serverLobby))
            serverLobby.sendToAll(new PrintMessage("Player: "+getNickname()+" discarded a leader!").serialize(),getNickname());
        serverLobby.sendToAll(new DoneMessage().serialize(),null);
    }

}
