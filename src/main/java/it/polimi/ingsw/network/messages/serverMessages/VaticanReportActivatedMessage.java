package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

import java.util.ArrayList;

public class VaticanReportActivatedMessage extends ServerMessage {
    private int victoryPoints;
    private ArrayList<String> nicknames;

    public VaticanReportActivatedMessage(int victoryPoints, ArrayList<String> nicknames) {
        super(ServerMessageType.VATICANREPORT);
        this.victoryPoints = victoryPoints;
        this.nicknames = nicknames;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getGameStatus().updateVaticanReport(victoryPoints);
        clientManager.getView().vaticanReportActivated(victoryPoints,nicknames);
    }
}
