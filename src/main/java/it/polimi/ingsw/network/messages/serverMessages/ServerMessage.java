package it.polimi.ingsw.network.messages.serverMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.network.enumeration.ServerMessageType;

public abstract class ServerMessage {
    static GsonBuilder builder = new GsonBuilder();
    static Gson gson = builder.create();
    private ServerMessageType type;

    public ServerMessage(ServerMessageType type) {
        this.type = type;
    }

    /**
     *
     * @return this message as a String (ready to be sent via network)
     */
    public String serialize(){
        return gson.toJson(this);
    }
}
