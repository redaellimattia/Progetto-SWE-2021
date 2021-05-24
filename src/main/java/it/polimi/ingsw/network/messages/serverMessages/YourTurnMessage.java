package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class YourTurnMessage extends ServerMessage{
    public YourTurnMessage() {
        super(ServerMessageType.YOURTURN);
    }

    /**
     * Puts the player in your turn mode
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.yourTurn();
    }
}
