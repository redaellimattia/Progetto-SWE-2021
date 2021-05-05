package it.polimi.ingsw.network.messages.clientMessages;

import com.google.gson.*;

import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.messages.clientMessages.actionMessages.ActionMessage;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;

public abstract class ClientMessage {

    static GsonBuilder builder = new GsonBuilder();
    static Gson gson = builder.create();
    private ClientMessageType type;
    private String nickname;
    private long serverThreadID;

    /**
     *
     * @return nickname of the player who sent the message
     */
    public String getNickname() {
        return nickname;
    }

    public long getServerThreadID() {
        return serverThreadID;
    }

    public ClientMessageType getType() {
        return type;
    }

    public ClientMessage(ClientMessageType type, String nickname, long serverThreadID) {
        this.type = type;
        this.nickname = nickname;
        this.serverThreadID = serverThreadID;
    }

    /**
     *
     * @param msg message that needs to be deserialized
     * @return deserialized message
     */
    public static ClientMessage onMessage(String msg){
        JsonObject jsonObj = gson.fromJson(msg,JsonElement.class).getAsJsonObject();
        String msgType = jsonObj.get("type").getAsString();

        if(msgType == null)
            throw new IllegalArgumentException("No type of message was received.");

        ClientMessageType type = ClientMessageType.valueOf(msgType);
        switch(type){
            case ASKLOBBIES:
                return gson.fromJson(msg,AskLobbyMessage.class);
            case CREATEGAME:
                return gson.fromJson(msg,CreateGameMessage.class);
            case JOINGAME:
                return gson.fromJson(msg,JoinGameMessage.class);
            case DISCONNECTION:
                return gson.fromJson(msg,DisconnectionMessage.class);
            /*case ACTION:
                return ActionMessage.deserializeAction(jsonObj);*/
            default:
                throw new IllegalArgumentException("ClientMessageType not valid.");
        }
    }

    /**
     * Method that will be overridden
     *
     * @param socketConnection method that will be Overrided in the sub-classes
     */
    public void useMessage(SocketConnection socketConnection){};

    /**
     * Method that will be overridden
     *
     * @param socketConnection the connection from which the message has arrived
     * @param serverThread serverThread of the client
     */
    public void useMessage(SocketConnection socketConnection,ServerThread serverThread){};


    /**
     *
     * @return this message as a String (ready to be sent via network)
     */
    public String serialize(){
        return gson.toJson(this);
    }

}
