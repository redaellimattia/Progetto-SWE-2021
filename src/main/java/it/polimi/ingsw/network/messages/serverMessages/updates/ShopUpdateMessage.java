package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;
import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;

public class ShopUpdateMessage extends ServerMessage {
    private Deck[][] shopGrid;

    public ShopUpdateMessage(Deck[][] shopGrid) {
        super(ServerMessageType.SHOPUPDATE);
        this.shopGrid = shopGrid;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.updateShop(shopGrid);
    }
}
