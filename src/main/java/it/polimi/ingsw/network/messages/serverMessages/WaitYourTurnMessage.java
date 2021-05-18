package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class WaitYourTurnMessage extends ServerMessage{
    public WaitYourTurnMessage() {
        super(ServerMessageType.WAITYOURTURN);
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getView().waitingForTurn();
    }
}
