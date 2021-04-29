package it.polimi.ingsw.network.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.network.enumeration.ActionType;

public abstract class ActionMessage extends Message{
    private String content;
    static GsonBuilder builder = new GsonBuilder();
    static Gson gson = builder.create();

    public static ActionMessage deserializeAction(JsonObject jsonObj) { // /"actionType"/: CARDSHOP
        String actionType = jsonObj.get("actionType").getAsString();
        if (actionType == null)
            throw new IllegalArgumentException("Not a valid action");
        ActionType type = ActionType.valueOf(actionType);

        switch(type){
            case CARDSHOP:
                return gson.fromJson(jsonObj,CardShopMessage.class);
            case PLAYLEADER:
                return gson.fromJson(jsonObj,PlayLeaderMessage.class);
            default:
                throw new IllegalArgumentException("actionType not found");
        }
    }
}
