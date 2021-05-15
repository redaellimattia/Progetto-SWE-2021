package it.polimi.ingsw.network.messages.serverMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.card.Requirement;
import it.polimi.ingsw.model.card.SpecialAbility;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.enumeration.ServerMessageType;
import it.polimi.ingsw.network.messages.InterfaceAdapter;
import it.polimi.ingsw.network.messages.serverMessages.updates.MarketUpdateMessage;
import it.polimi.ingsw.network.messages.serverMessages.updates.PlayerUpdateMessage;
import it.polimi.ingsw.network.messages.serverMessages.updates.ShopUpdateMessage;

public abstract class ServerMessage {
    static GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Requirement.class, new InterfaceAdapter()).registerTypeAdapter(SpecialAbility.class, new InterfaceAdapter());
    static Gson gson = builder.create();
    private final ServerMessageType type;

    public ServerMessage(ServerMessageType type) {
        this.type = type;
    }

    public static ServerMessage deserializeMessage(String msg){
        JsonObject jsonObj = gson.fromJson(msg, JsonElement.class).getAsJsonObject();
        String msgType = jsonObj.get("type").getAsString();

        if(msgType == null)
            throw new IllegalArgumentException("No type of message was received.");

        ServerMessageType type = ServerMessageType.valueOf(msgType);
        switch(type){
            case LOBBIES:
                return gson.fromJson(msg, ReturnLobbiesMessage.class);
            case JOINEDLOBBY:
                return gson.fromJson(msg,JoinedLobbyMessage.class);
            case YOURTURN:
                return gson.fromJson(msg, YourTurnMessage.class);
            case PREGAME:
                return gson.fromJson(msg,PreGameMessage.class);
            case PRINTMESSAGE:
                return gson.fromJson(msg,PrintMessage.class);
            case SHOPUPDATE:
                return gson.fromJson(msg, ShopUpdateMessage.class);
            case MARKETUPDATE:
                return gson.fromJson(msg, MarketUpdateMessage.class);
            case PLAYERUPDATE:
                return PlayerUpdateMessage.deserializePlayerUpdate(jsonObj);
            case INITGAMESTATUS:
                return gson.fromJson(msg, InitGameStatusMessage.class);
            case WAITYOURTURN:
                return gson.fromJson(msg, WaitYourTurnMessage.class);
            case ENDSINGLEPLAYERGAME:
                return gson.fromJson(msg, EndSinglePlayerGameMessage.class);
            case ENDMULTIPLAYERGAME:
                return gson.fromJson(msg, EndMultiPlayerGameMessage.class);
            default:
                throw new IllegalArgumentException("ServerMessageType not valid.");
        }
    }

    public void useMessage(ClientManager clientManager){}

    /**
     *
     * @return this message as a String (ready to be sent via network)
     */
    public String serialize(){
        return gson.toJson(this);
    }
}

