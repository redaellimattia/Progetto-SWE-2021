package it.polimi.ingsw.network.messages;

import com.google.gson.*;
import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.network.enumeration.MessageType;
import it.polimi.ingsw.network.messages.actionMessages.ActionMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;

import java.util.HashMap;
import java.util.Map;

public abstract class Message {

    static GsonBuilder builder = new GsonBuilder();
    static Gson gson = builder.create();
    private String nickname;
    private long serverThreadID;

    /**
     *
     * @return nickname of the player who sent the message
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @param msg message that needs to be deserialized
     * @return deserialized message
     */
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

    public void useMessage(SocketConnection socketConnection){};

    /**
     * if exists, return the corret serverThread for that socketConnection
     * @param socketConnection socket from which the message came from
     * @return the serverThread associated with that specific socketConnection
     */
    public ServerThread getServerThread(SocketConnection socketConnection){

        if(Server.serverThreads.containsKey(serverThreadID)) {

            ServerThread serverThread = Server.serverThreads.get(serverThreadID);
            Map<String,SocketConnection> clients = serverThread.getClients();

            if (clients.containsKey(nickname) && clients.get(nickname).equals(socketConnection))
                return serverThread;
        }
        return null;
    }


    /**
     *
     * @return this message as a String (ready to be sent via network)
     */
    public String serialize(){
        return gson.toJson(this);
    }

}
