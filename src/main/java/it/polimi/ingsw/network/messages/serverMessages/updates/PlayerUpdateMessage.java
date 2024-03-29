package it.polimi.ingsw.network.messages.serverMessages.updates;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.card.Requirement;
import it.polimi.ingsw.model.card.SpecialAbility;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;
import it.polimi.ingsw.network.enumeration.ServerMessageType;
import it.polimi.ingsw.network.messages.InterfaceAdapter;
import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;

public abstract class PlayerUpdateMessage extends ServerMessage {
    static GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Requirement.class, new InterfaceAdapter()).registerTypeAdapter(SpecialAbility.class, new InterfaceAdapter());
    static Gson gson = builder.create();
    private final PlayerUpdateType playerUpdateType;
    private final String nickname;

    public PlayerUpdateMessage(PlayerUpdateType playerUpdateType, String nickname) {
        super(ServerMessageType.PLAYERUPDATE);
        this.playerUpdateType = playerUpdateType;
        this.nickname = nickname;
    }

    /**
     *
     * @param jsonObj JsonObject passed from the AbstractMessage class
     * @return the specific ActionMessage that need to be executed
     */
    public static PlayerUpdateMessage deserializePlayerUpdate(JsonObject jsonObj) { // /"actionType"/: CARDSHOP
        String playerUpdateType = jsonObj.get("playerUpdateType").getAsString();
        if (playerUpdateType == null)
            throw new IllegalArgumentException("Not a valid player update.");
        PlayerUpdateType type = PlayerUpdateType.valueOf(playerUpdateType);

        switch(type){
            case CHEST:
            case BUFFERPRODUCTION:
                return gson.fromJson(jsonObj, ResourceCountUpdateMessage.class);

            case INITARRAYDEPOSIT:
                return gson.fromJson(jsonObj, InitArrayDepositUpdateMessage.class);
            case ARRAYDEPOSIT:
                return gson.fromJson(jsonObj, ArrayDepositUpdateMessage.class);
            case DEVCARDS:
                return gson.fromJson(jsonObj, DevCardsUpdateMessage.class);
            case DISCARDLEADER:
            case INGAMELEADER:
                return gson.fromJson(jsonObj, LeaderUpdateMessage.class);
            case PATHPOSITION:
                return gson.fromJson(jsonObj, PathPositionUpdateMessage.class);
            case FIRSTROW:
            case SECONDROW:
            case THIRDROW:
                return gson.fromJson(jsonObj, StorageUpdateMessage.class);
            case VICTORYPOINTS:
                return gson.fromJson(jsonObj, VictoryPointsUpdateMessage.class);
            case LEADERCHOICE:
                return gson.fromJson(jsonObj, LeaderChoiceMessage.class);
            default:
                throw new IllegalArgumentException("playerUpdateType not found.");
        }
    }
    public String getNickname(){
        return this.nickname;
    }

    public PlayerUpdateType getPlayerUpdateType() {
        return playerUpdateType;
    }
}
