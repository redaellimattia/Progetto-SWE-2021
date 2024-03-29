package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.card.Requirement;
import it.polimi.ingsw.model.card.SpecialAbility;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.messages.InterfaceAdapter;
import it.polimi.ingsw.network.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.DoneMessage;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

public abstract class ActionMessage extends ClientMessage {
    static GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Requirement.class, new InterfaceAdapter()).registerTypeAdapter(SpecialAbility.class, new InterfaceAdapter());
    static Gson gson = builder.create();
    private final ActionType actionType;

    public ActionMessage(ActionType actionType,String nickname, long serverThreadID) {
        super(ClientMessageType.ACTION, nickname, serverThreadID);
        this.actionType = actionType;
    }

    /**
     *
     * @param jsonObj JsonObject passed from the AbstractMessage class
     * @return the specific ActionMessage that need to be executed
     */
    public static ActionMessage deserializeAction(JsonObject jsonObj) { // /"actionType"/: CARDSHOP
        String actionType = jsonObj.get("actionType").getAsString();
        if (actionType == null)
            throw new IllegalArgumentException("Not a valid action.");
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
                return MarketActionMessage.deserializeMarketAction(jsonObj);
            case MOVEFROMDEPOSITTOLEADER:
                return gson.fromJson(jsonObj, MoveFromDepositToLeaderMessage.class);
            case MOVEFROMLEADERTODEPOSIT:
                return gson.fromJson(jsonObj, MoveFromLeaderToDepositMessage.class);
            case ORGANIZESTORAGE:
                return gson.fromJson(jsonObj, OrganizeStorageMessage.class);
            case PLAYLEADER:
                return gson.fromJson(jsonObj, PlayLeaderMessage.class);
            case ENDACTION:
                return gson.fromJson(jsonObj, EndActionMessage.class);
            default:
                throw new IllegalArgumentException("actionType not found.");
        }
    }

    /**
     * Method that will be overridden
     *
     * @param socketConnection the connection from which the message has arrived
     */
    @Override
    public void useMessage(SocketConnection socketConnection){}

    /**
     * Method that will be overridden
     *
     * @param socketConnection the connection from which the message has arrived
     * @param serverLobby serverLobby of the client
     */
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby){}

    public PlayerTurnManager getPlayerTurnManager(ServerLobby serverLobby){
        return serverLobby.getGameLobby().getGameManager().getTurnManager();
    }

    /**
     *  Call the turnManager to set and use the action;
     *
     * @param action the action that needs to be done
     * @param serverLobby serverLobby of the game where the player is playing
     */
    public boolean useActionMessage(Action action, ServerLobby serverLobby){
        PlayerTurnManager turnManager = getPlayerTurnManager(serverLobby);
        turnManager.setAction(action);
        if(turnManager.useAction()) {
            serverLobby.sendToAll(new DoneMessage().serialize(), null);
            return true;
        }
        return false;
    }

    /**
     * Call the turnManager to set and use the action;
     * @param action the action that needs to be done
     * @param serverLobby serverLobby of the game where the player is playing
     */
    public boolean useSideActionMessage(Action action, ServerLobby serverLobby){
        PlayerTurnManager turnManager = getPlayerTurnManager(serverLobby);
        turnManager.setSideAction(action);
        return turnManager.useSideAction();
    }
}
