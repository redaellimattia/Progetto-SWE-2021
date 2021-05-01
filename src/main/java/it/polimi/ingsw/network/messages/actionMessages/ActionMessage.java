package it.polimi.ingsw.network.messages.actionMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;

public abstract class ActionMessage extends Message {
    private String content;
    static GsonBuilder builder = new GsonBuilder();
    static Gson gson = builder.create();

    public static ActionMessage deserializeAction(JsonObject jsonObj) { // /"actionType"/: CARDSHOP
        String actionType = jsonObj.get("actionType").getAsString();
        if (actionType == null)
            throw new IllegalArgumentException("Not a valid action");
        ActionType type = ActionType.valueOf(actionType);

        switch(type){
            case BASICPRODUCTION:
                return gson.fromJson(jsonObj, BasicProductionMessage.class);
            case CARDSHOP:
                return gson.fromJson(jsonObj, CardShopMessage.class);
            case DEVCARDPRODUCTION:
                return gson.fromJson(jsonObj, DevCardProductionMessage.class);
            case DISCARDLEADER:
                return gson.fromJson(jsonObj, DiscardLeaderMessage.class);
            case LEADERPRODUCTION:
                return gson.fromJson(jsonObj, LeaderProductionMessage.class);
            case MARKETACTION:
                return gson.fromJson(jsonObj, MarketActionMessage.class);
            case MOVEFROMDEPOSITTOLEADER:
                return gson.fromJson(jsonObj, MoveFromDepositToLeaderMessage.class);
            case MOVEFROMLEADERTODEPOSIT:
                return gson.fromJson(jsonObj, MoveFromLeaderToDepositMessage.class);
            case ORGANIZESTORAGE:
                return gson.fromJson(jsonObj, OrganizeStorageMessage.class);
            case PLAYLEADER:
                return gson.fromJson(jsonObj, PlayLeaderMessage.class);
            default:
                throw new IllegalArgumentException("actionType not found");
        }
    }

    @Override
    public void useMessage(SocketConnection socketConnection){}

    /**
     *  call the turnManager to set and use the action;
     *
     * @param action the action that needs to be done
     * @param socketConnection the connection from which the message has arrived
     */
    public void useAction(Action action, SocketConnection socketConnection){
        ServerThread serverThread = getServerThread(socketConnection);
        PlayerTurnManager turnManager = serverThread.getGameLobby().getGameManager().getTurnManager();
        turnManager.setAction(action);
        turnManager.useAction();
    }
}
