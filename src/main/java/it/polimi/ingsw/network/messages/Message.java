package it.polimi.ingsw.network.messages;

import com.google.gson.*;
import it.polimi.ingsw.network.enumeration.MessageType;

public abstract class Message {

    static GsonBuilder builder = new GsonBuilder();
    static Gson gson = builder.create();

    public static Message onMessage(String msg){
        JsonObject jsonObj = gson.fromJson(msg, JsonElement.class).getAsJsonObject();
        String msgType = jsonObj.get("type").getAsString();
        if(msgType == null)
            throw new IllegalArgumentException("No type of message was received;");

        MessageType type = MessageType.valueOf(msgType);
        switch(type){
            case CONNECTION:
                return gson.fromJson(msg,ConnectionMessage.class);
            case DISCONNECTION:
                return gson.fromJson(msg,DisconnectionMessage.class);
            case ACTION:
                return ActionMessage.deserializeAction(jsonObj);
            default:
                throw new IllegalArgumentException("MessageType not valid;");
        }
    }

    public String sendBack(){
        return gson.toJson(this);
    }
}
