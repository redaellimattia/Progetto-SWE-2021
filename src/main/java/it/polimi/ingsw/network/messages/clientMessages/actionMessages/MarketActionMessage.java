package it.polimi.ingsw.network.messages.clientMessages.actionMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.MarketActionType;

public abstract class MarketActionMessage extends ActionMessage{
    static GsonBuilder builder = new GsonBuilder();
    static Gson gson = builder.create();
    private MarketActionType marketActionType;

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
}
