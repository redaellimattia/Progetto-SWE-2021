package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class DoneMessage extends ServerMessage{
    public DoneMessage() {
        super(ServerMessageType.DONE);
    }
    @Override
    public void useMessage(ClientManager clientManager){
        if(clientManager.isProductionActionOnGoing())
            clientManager.getView().startProduction();
        else
            clientManager.updateViewWithClear();
    }
}
