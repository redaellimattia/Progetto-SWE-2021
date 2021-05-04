package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.productionAction.BasicProductionAction;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;

public class BasicProductionMessage extends ActionMessage{
    private Resource res;
    private ResourceCount storageCount;
    private ResourceCount chestCount;

    public BasicProductionMessage(String nickname, long serverThreadID) {
        super(ActionType.BASICPRODUCTION,nickname, serverThreadID);
    }

    /**
     * Create a BasicProductionAction and uses it
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerThread serverThread) {
        BasicProductionAction action = new BasicProductionAction(res, storageCount, chestCount);
        useActionMessage(action, socketConnection,serverThread);
    }
}
