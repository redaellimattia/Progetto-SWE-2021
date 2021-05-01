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

        
        String json = "{" +
                "    \"row\":1," +
                "    \"column\":1," +
                "    \"deckPosition\":1," +
                "    \"storageCount\":{" +
                "        \"coins\":1," +
                "        \"rocks\":1," +
                "        \"servants\":1," +
                "        \"shields\":1," +
                "        \"faith\":0" +
                "    }," +
                "    \"chestCount\":{" +
                "        \"coins\":1," +
                "        \"rocks\":1," +
                "        \"servants\":1," +
                "        \"shields\":1," +
                "        \"faith\":0" +
                "    }," +
                "    \"actionType\":\"CARDSHOP\"," +
                "    \"type\":\"ACTION\"," +
                "    \"nickname\":\"giann1\"," +
                "    \"serverThreadID\":0" +
                "}";//= gson.toJson(message);
        //System.out.println(json);
        ResourceCount check = new ResourceCount(1,1,1,1,0);

        Message deserializedMessage = Message.onMessage(json);
        assertTrue(deserializedMessage instanceof CardShopMessage);
        assertEquals("giann1",deserializedMessage.getNickname());
        assertEquals(check,((CardShopMessage) deserializedMessage).getChestCount());
        assertEquals(check,((CardShopMessage) deserializedMessage).getStorageCount());
    }
}