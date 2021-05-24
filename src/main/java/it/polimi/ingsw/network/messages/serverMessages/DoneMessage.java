package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class DoneMessage extends ServerMessage{
    public DoneMessage() {
        super(ServerMessageType.DONE);
    }

    /**
     * If the production is still going, continue it
     * Otherwise update the view
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        if(clientManager.isProductionActionOnGoing())
            clientManager.getView().startProduction();
        else
            clientManager.updateViewWithClear();
    }
}
