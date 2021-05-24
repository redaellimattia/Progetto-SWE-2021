package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.action.marketAction.AtomicMarketAction;
import it.polimi.ingsw.controller.action.marketAction.MarketAction;
import it.polimi.ingsw.model.card.Requirement;
import it.polimi.ingsw.model.card.SpecialAbility;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.MarketActionType;
import it.polimi.ingsw.network.messages.InterfaceAdapter;
import it.polimi.ingsw.network.server.ServerLobby;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.ArrayList;

public abstract class MarketActionMessage extends ActionMessage{
    static GsonBuilder builder = new GsonBuilder();
    static Gson gson = builder.create();
    private MarketActionType marketActionType;
    //private final int rowColType;
    //private final int pos;
    //private final ArrayList<AtomicMarketAction> choices;

    public MarketActionMessage(String nickname, long serverThreadID, MarketActionType marketActionType) {
        super(ActionType.MARKETACTION, nickname, serverThreadID);
        this.marketActionType = marketActionType;
    }

    /**
     *
     * @param jsonObj JsonObject passed from the ActionMessage class
     * @return the specific MarketActionMessage that need to be executed
     */
    public static MarketActionMessage deserializeMarketAction(JsonObject jsonObj) {
        String marketActionType = jsonObj.get("marketActionType").getAsString();
        if (marketActionType == null)
            throw new IllegalArgumentException("Not a valid action.");
        MarketActionType type = MarketActionType.valueOf(marketActionType);

        switch(type){
            case GETRESOURCE:
                return gson.fromJson(jsonObj, GetResourceMessage.class);
            case DISCARDRESOURCE:
                return gson.fromJson(jsonObj, DiscardResourceMessage.class);
            case CONVERTMARBLE:
                return gson.fromJson(jsonObj, ConvertMarbleMessage.class);
            case ENDMARKETACTION:
                return gson.fromJson(jsonObj, EndMarketActionMessage.class);
            default:
                throw new IllegalArgumentException("actionType not found.");
        }
    }

    /*
    /**
     * Create a MarketAction and uses it.
     * @param socketConnection the connection from which the message has arrived
     */
    /*
    @Override
    public void useMessage(SocketConnection socketConnection, ServerLobby serverLobby) {
        MarketAction action = new MarketAction(rowColType, pos, choices, serverLobby.getGameLobby().getGameManager());
        useActionMessage(action, socketConnection, serverLobby);
    } */
}
