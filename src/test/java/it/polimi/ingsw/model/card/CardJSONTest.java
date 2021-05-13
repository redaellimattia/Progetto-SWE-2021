package it.polimi.ingsw.model.card;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.model.DeckShop;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.token.AdvanceToken;
import it.polimi.ingsw.model.token.DiscardToken;
import it.polimi.ingsw.model.token.SoloToken;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.util.ArrayList;

public class CardJSONTest {
    @Test
    public void testCard() {
        ResourceCount cost = new ResourceCount(0,0,0,2,0);
        ResourceCount prodInput = new ResourceCount(1,0,0,0,0);
        ResourceCount prodOutput = new ResourceCount(0,0,0,0,1);
        DevelopmentCard card = new DevelopmentCard(1, 1, cost, new Production(prodInput, prodOutput), 1, CardColour.GREEN);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        ArrayList<Card> deck = new ArrayList<Card>();
        deck.add(card);
        String message = gson.toJson(deck);
        System.out.println(message);

    }

    @Test
    public void importDevCards() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        JsonReader json = new GameLobby(0, 0).readJsonFile("/DevCards");
        DeckShop fromJson = gson.fromJson(json, DeckShop.class);
        System.out.println(fromJson.getDeck().size());
        for (DevelopmentCard d: fromJson.getDeck()) {
            System.out.println("Id " + d.getId() + " Colore: " + d.getColour());
        }
        int count = 0;
        for (DevelopmentCard d: fromJson.getDeck()) {
            if (d.getColour() == CardColour.GREEN) {
                count++;
            }
        }
        System.out.println("Carte verdi: " + count);
        count = 0;
        for (DevelopmentCard d: fromJson.getDeck()) {
            if (d.getLevel() == 3) {
                count++;
            }
        }
        System.out.println("Carte liv 3: " + count);
    }

    @Test
    public void importSoloTokens() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        JsonReader json = new GameLobby(0, 0).readJsonFile("/Tokens");
        JsonArray array = gson.fromJson(json, JsonElement.class);
        //JsonArray array = jsonObj.getAsJsonArray();
        ArrayList<SoloToken> tokensDeck = new ArrayList<SoloToken>();
        for (JsonElement s: array) {
            switch(s.getAsJsonObject().get("type").getAsString()) {
                case "discardToken": tokensDeck.add(gson.fromJson(s, DiscardToken.class)); break;
                // In this case we need to use the constructor to build the AdvanceToken object
                // because steps value is not in JSON file (it can be inferred by reRoll value)
                case "advanceToken": tokensDeck.add(new AdvanceToken(gson.fromJson(s, AdvanceToken.class).isReRoll(), null)); break;
            }
        }
        for (SoloToken s: tokensDeck) {
            System.out.println(s.getClass());
        }
    }

    /*
    @Test
    public void importLeaders() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        JsonReader json = new GameLobby(0, 0).readJsonFile("/LeaderCards");
        JsonArray array = gson.fromJson(json, JsonElement.class);
        //JsonArray array = jsonObj.getAsJsonArray();
        ArrayList<LeaderCard> leadersList = new ArrayList<LeaderCard>();
        for (JsonElement s: array) {
            switch(s.getAsJsonObject().get("requirementType").getAsString()) {
                case "resource": leadersList.add(new LeaderCard(0, )); break;
                // In this case we need to use the constructor to build the AdvanceToken object
                // because steps value is not in JSON file (it can be inferred by reRoll value)
                case "advanceToken": tokensDeck.add(new AdvanceToken(gson.fromJson(s, AdvanceToken.class).isReRoll(), null)); break;
            }
        }
        for (SoloToken s: tokensDeck) {
            System.out.println(s.getClass());
        }
    }
     */
}
