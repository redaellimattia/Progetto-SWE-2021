package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class GameStartedMessage extends ServerMessage{
    public GameStartedMessage() {
        super(ServerMessageType.STARTGAME);
    }

    /**
     * Sets the game as started
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.setGameStarted(true);
    }
}
