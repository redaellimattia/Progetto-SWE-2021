package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public class EndSinglePlayerGameMessage extends ServerMessage {
    private final boolean lorenzoWin;
    private final int playerVictoryPoints;

    public EndSinglePlayerGameMessage(boolean lorenzoWin, int playerVictoryPoints) {
        super(ServerMessageType.ENDSINGLEPLAYERGAME);
        this.lorenzoWin = lorenzoWin;
        this.playerVictoryPoints = playerVictoryPoints;
    }

    /**
     * Ends the singlePlayer game
     *
     * @param clientManager clientManager of the client
     */
    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getView().endGame(lorenzoWin,playerVictoryPoints);
    }
}
