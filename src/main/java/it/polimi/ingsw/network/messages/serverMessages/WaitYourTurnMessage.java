package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class WaitYourTurnMessage extends ServerMessage{
    public WaitYourTurnMessage() {
        super(ServerMessageType.WAITYOURTURN);
    }

    /**
     * Puts the player in waiting for turn mode
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getView().waitingForTurn();
    }
}
