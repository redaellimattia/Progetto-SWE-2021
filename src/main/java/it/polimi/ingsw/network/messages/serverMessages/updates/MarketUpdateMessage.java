package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.network.enumeration.ServerMessageType;
import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;

public class MarketUpdateMessage extends ServerMessage {
    MarketMarble[][] structure;
    MarketMarble freeMarble;

    public MarketUpdateMessage(MarketMarble[][] structure, MarketMarble freeMarble) {
        super(ServerMessageType.MARKETUPDATE);
        this.structure = structure;
        this.freeMarble = freeMarble;
    }
}
