package it.polimi.ingsw.network.messages.serverMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public abstract class ServerMessage {
    static GsonBuilder builder = new GsonBuilder();
    static Gson gson = builder.setPrettyPrinting().create();
    private final ServerMessageType type;

    public ServerMessage(ServerMessageType type) {
        this.type = type;
    }

    public static ServerMessage onMessage(String msg){
        JsonObject jsonObj = gson.fromJson(msg, JsonElement.class).getAsJsonObject();
        String msgType = jsonObj.get("type").getAsString();

        if(msgType == null)
            throw new IllegalArgumentException("No type of message was received.");

        ServerMessageType type = ServerMessageType.valueOf(msgType);
        switch(type){
            case LOBBIES:
                return gson.fromJson(msg, ReturnLobbiesMessage.class);
            default:
                throw new IllegalArgumentException("ServerMessageType not valid.");
        }
    }

    /**
     *
     * @return this message as a String (ready to be sent via network)
     */
    public String serialize(){
        return gson.toJson(this);
    }
}
