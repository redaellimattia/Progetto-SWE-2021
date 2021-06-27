package it.polimi.ingsw.network.messages.clientMessages.actionMessages;


import it.polimi.ingsw.controller.action.leaderAction.PlayLeaderAction;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.messages.serverMessages.DoneMessage;
import it.polimi.ingsw.network.messages.serverMessages.PrintMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.logging.Level;

public class PlayLeaderMessage extends ActionMessage {
    private final LeaderCard card;

    public PlayLeaderMessage(String nickname, long serverThreadID,LeaderCard card) {
        super(ActionType.PLAYLEADER, nickname, serverThreadID);
        this.card = card;
    }

    /**
     * Set a specific leader card in game
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        Server.LOGGER.log(Level.INFO,"LobbyID: "+serverLobby.getLobbyId()+": Play Leader Action arrived from: "+getNickname());
        PlayLeaderAction action = new PlayLeaderAction(card);
        if(useSideActionMessage(action, serverLobby)) {
            serverLobby.sendToAll(new PrintMessage("Player: " + getNickname() + " played a leader!").serialize(), getNickname());
            serverLobby.sendToAll(new DoneMessage().serialize(),null);
        }

    }
}
