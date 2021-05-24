package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class JoinedLobbyMessage extends ServerMessage{
    private final long lobbyID;

    public JoinedLobbyMessage(long lobbyID){
        super(ServerMessageType.JOINEDLOBBY);
        this.lobbyID = lobbyID;
    }

    /**
     * Updates the client's lobbyID
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager) {
        clientManager.setServerLobbyID(lobbyID);
    }
}
