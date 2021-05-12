package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.network.enumeration.ServerMessageType;
import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;

public class ShopUpdateMessage extends ServerMessage {
    Deck[][] shopGrid;

    public ShopUpdateMessage(Deck[][] shopGrid) {
        super(ServerMessageType.SHOPUPDATE);
        this.shopGrid = shopGrid;
    }
}
