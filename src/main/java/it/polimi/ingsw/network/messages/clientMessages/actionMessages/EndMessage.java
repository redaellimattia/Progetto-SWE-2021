package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.productionAction.BasicProductionAction;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;

public class EndMessage extends ActionMessage{
    /*public EndMessage(ClientMessageType type, String nickname, long serverThreadID, ActionType actionType) {
        super(type, nickname, serverThreadID, actionType);
    }*/

    /**
     * In case of multiple productionAction, this message is called to close the window of production and empty the ResourceBuffer of the player.
     * a little bit rusty, maybe need to be checked
     * @param socketConnection
     */
    @Override
    public void useMessage(SocketConnection socketConnection){
        ServerThread serverThread = getServerThread(socketConnection);
        PlayerTurnManager turnManager = serverThread.getGameLobby().getGameManager().getTurnManager();
        Action action = new BasicProductionAction(null,new ResourceCount(0,0,0,0,0), new ResourceCount(0,0,0,0,0));
        turnManager.setAction(action);
        turnManager.getAction().endAction(turnManager.getPlayer());
    }
}
