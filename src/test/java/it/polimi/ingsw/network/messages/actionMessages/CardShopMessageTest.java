package it.polimi.ingsw.network.messages.actionMessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.MessageType;
import it.polimi.ingsw.network.messages.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardShopMessageTest {

    @Test
    void useMessage() {
       /* String JSON_DATA =
                "{"
                        + "  \"message\": ["
                        + "    {"
                        + "      \"serverThreadID\": \"0\","
                        + "      \"nickname\": \"prova\","
                        + "      \"type\" : \"ACTION\","
                        + "      \"actionType\" : \"CARDSHOP\","
                        + "      \"row\" : \"1\","
                        + "      \"column\" : \"2\","
                        + "      \"deckPosition\" : \"1\","
                        + "      \"storageCount\" : ["
                        + "         {"
                        + "      \"coins\": \"1\","
                        + "      \"rocks\": \"2\","
                        + "      \"servants\": \"3\","
                        + "      \"shields\": \"4\","
                        + "      \"faith\": \"5\""
                        + "     }"
                        + " ],"
                        + "      \"chestCount\" : ["
                        + "         {"
                        + "      \"coins\": \"1\","
                        + "      \"rocks\": \"2\","
                        + "      \"servants\": \"3\","
                        + "      \"shields\": \"4\","
                        + "      \"faith\": \"5\""
                        + "     }"
                        + " ]"
                        + "   }" +
                        "]"
                        + "}";*/
        CardShopMessage message = new CardShopMessage(MessageType.ACTION,"giann1",0, ActionType.CARDSHOP,1,1,1,new ResourceCount(1,1,1,1,0),new ResourceCount(1,1,1,1,0));
        Gson gson = new Gson();
        String json = gson.toJson(message);
        ResourceCount check = new ResourceCount(1,1,1,1,0);

        Message deserializedMessage = Message.onMessage(json);
        assertTrue(deserializedMessage instanceof CardShopMessage);
        assertEquals("giann1",deserializedMessage.getNickname());
        assertEquals(check,((CardShopMessage) deserializedMessage).getChestCount());
        assertEquals(check,((CardShopMessage) deserializedMessage).getStorageCount());
    }
}