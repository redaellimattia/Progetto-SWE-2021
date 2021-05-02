package it.polimi.ingsw.network.messages.actionMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.DeckShop;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.network.enumeration.ActionType;
import it.polimi.ingsw.network.enumeration.MessageType;
import it.polimi.ingsw.network.messages.Message;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
    @Test
    void genericJsonTesting(){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        Production prod = new Production(new ResourceCount(0,0,0,0,0),new ResourceCount(0,0,0,0,0));
        //GREEN CARDS
        DevelopmentCard cardGreen1 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.GREEN);
        DevelopmentCard cardGreen2 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.GREEN);
        DevelopmentCard cardGreen3 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.GREEN);
        DevelopmentCard cardGreen4 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.GREEN);
        DevelopmentCard cardGreen5 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.GREEN);
        DevelopmentCard cardGreen6 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.GREEN);
        DevelopmentCard cardGreen7 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.GREEN);
        DevelopmentCard cardGreen8 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.GREEN);
        DevelopmentCard cardGreen9 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.GREEN);
        DevelopmentCard cardGreen10 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.GREEN);
        DevelopmentCard cardGreen11 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.GREEN);
        DevelopmentCard cardGreen12 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.GREEN);
        ArrayList<DevelopmentCard> testG3 = new ArrayList<>();
        testG3.add(cardGreen1);
        testG3.add(cardGreen2);
        testG3.add(cardGreen3);
        testG3.add(cardGreen4);
        DeckShop deckGreen3 = new DeckShop(testG3);

        String message = gson.toJson(deckGreen3);
        System.out.println(message);
    }
}