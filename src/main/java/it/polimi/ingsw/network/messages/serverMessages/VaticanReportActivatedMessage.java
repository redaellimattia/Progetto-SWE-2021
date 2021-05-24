package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

import java.util.ArrayList;

public class VaticanReportActivatedMessage extends ServerMessage {
    private final int victoryPoints;
    private final ArrayList<String> nicknames;

    public VaticanReportActivatedMessage(int victoryPoints, ArrayList<String> nicknames) {
        super(ServerMessageType.VATICANREPORT);
        this.victoryPoints = victoryPoints;
        this.nicknames = nicknames;
    }

    /**
     * Updates the vatican report then prints the message
     *
     * @param clientManager clientManager of the player
     */
    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getGameStatus().updateVaticanReport(victoryPoints);
        clientManager.getView().vaticanReportActivated(victoryPoints,nicknames);
    }
}
