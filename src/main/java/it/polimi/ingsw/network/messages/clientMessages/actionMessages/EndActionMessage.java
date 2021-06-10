package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.messages.serverMessages.DoneMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.logging.Level;

public class EndActionMessage extends ActionMessage{

    public EndActionMessage(String nickname, long serverThreadID) {
        super(ActionType.ENDACTION, nickname, serverThreadID);
    }

    /**
     * In case of multiple productionAction, this message is called to close the window of production and empty the ResourceBuffer of the player.
     * a little bit rusty, maybe need to be checked
     * @param socketConnection socketConnection of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){
        PlayerTurnManager turnManager = getPlayerTurnManager(serverLobby);
        turnManager.endAction(turnManager.getPlayer());
        Server.LOGGER.log(Level.INFO,"LobbyID: "+serverLobby.getLobbyId()+": End Action arrived from: "+getNickname());
        serverLobby.sendToAll(new DoneMessage().serialize(),null);
    }
}
