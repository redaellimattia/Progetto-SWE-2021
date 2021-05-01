package it.polimi.ingsw.network.messages.actionMessages;

import it.polimi.ingsw.controller.action.CardShopAction;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.Shop;
import it.polimi.ingsw.network.server.SocketConnection;

public class CardShopMessage extends ActionMessage {

    private Shop shop;
    private int row;
    private int column;
    private int deckPosition;
    private ResourceCount storageCount;
    private ResourceCount chestCount;

    @Override
    public void useMessage(SocketConnection socketConnection){

        CardShopAction action = new CardShopAction(shop,row,column,deckPosition,storageCount,chestCount);
        useAction(action,socketConnection);
    }
}
