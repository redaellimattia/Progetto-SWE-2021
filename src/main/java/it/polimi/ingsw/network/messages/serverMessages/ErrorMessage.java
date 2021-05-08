package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class ErrorMessage extends ServerMessage{
    private final String errorMsg;
    public ErrorMessage(String msg){
        super(ServerMessageType.ERROR);
        errorMsg = msg;
    }

    @Override
    public void useMessage(ClientManager clientManager) {
        clientManager.getView().printError(errorMsg);
    }
}
