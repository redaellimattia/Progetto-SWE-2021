package it.polimi.ingsw.network.messages.serverMessages.updates;

import it.polimi.ingsw.model.DeckShop;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;
import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;

public class ShopUpdateMessage extends ServerMessage {
    private DeckShop[][] shopGrid;

    public ShopUpdateMessage(DeckShop[][] shopGrid) {
        super(ServerMessageType.SHOPUPDATE);
        this.shopGrid = shopGrid;
    }

    @Override
    public void useMessage(ClientManager clientManager){
        clientManager.getGameStatus().updateShop(shopGrid);
    }
}
