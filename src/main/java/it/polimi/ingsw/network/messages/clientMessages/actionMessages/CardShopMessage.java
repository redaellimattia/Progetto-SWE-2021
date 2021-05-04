package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import it.polimi.ingsw.controller.action.CardShopAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;

public class CardShopMessage extends ActionMessage {

    private int row;
    private int column;
    private int deckPosition;
    private ResourceCount storageCount;
    private ResourceCount chestCount;

    public CardShopMessage(String nickname, long serverThreadID, int row, int column, int deckPosition, ResourceCount storageCount, ResourceCount chestCount) {
        super(ActionType.CARDSHOP, nickname, serverThreadID);
        this.row = row;
        this.column = column;
        this.deckPosition = deckPosition;
        this.storageCount = storageCount;
        this.chestCount = chestCount;
    }

    /**
     * Create a CardShopAction and uses it.
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection,ServerThread serverThread){
        Game game = serverThread.getGameLobby().getGameManager().getGame();
        CardShopAction action = new CardShopAction(game.getShop(),row,column,deckPosition,storageCount,chestCount);
        useActionMessage(action,socketConnection,serverThread);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getDeckPosition() {
        return deckPosition;
    }

    public ResourceCount getStorageCount() {
        return storageCount;
    }

    public ResourceCount getChestCount() {
        return chestCount;
    }
}
