package it.polimi.ingsw.model.card;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.model.DeckShop;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.token.AdvanceToken;
import it.polimi.ingsw.model.token.DiscardToken;
import it.polimi.ingsw.model.token.SoloToken;
import org.junit.jupiter.api.Test;

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
                case "advanceToken": tokensDeck.add(new AdvanceToken(gson.fromJson(s, AdvanceToken.class).isReRoll())); break;
            }
        }
        for (SoloToken s: tokensDeck) {
            System.out.println(s.getClass());
        }
    }


    @Test
    public void importLeaders() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        JsonReader json = new GameLobby(0, 0).readJsonFile("/LeaderCards");
        JsonArray array = gson.fromJson(json, JsonElement.class);
        //JsonArray array = jsonObj.getAsJsonArray();
        ArrayList<LeaderCard> leadersList = new ArrayList<LeaderCard>();
        for (JsonElement s: array) {
            int id = s.getAsJsonObject().get("id").getAsInt();
            int victoryPoints = s.getAsJsonObject().get("victoryPoints").getAsInt();
            Requirement requirement;
            SpecialAbility specialAbility = null;
            switch(s.getAsJsonObject().get("requirementType").getAsString()) {
                case "resource": {
                    ResourceCount resource = gson.fromJson(s.getAsJsonObject().get("resources"), ResourceCount.class);
                    requirement = new ResourceRequirement(resource);
                } break;
                case "cardLevel": {
                    CardColour cardColour = gson.fromJson(s.getAsJsonObject().get("colour"), CardColour.class);
                    int level = s.getAsJsonObject().get("level").getAsInt();
                    requirement = new CardLevelRequirement(cardColour, level);
                } break;
                case "typeOfCard": {
                    ColourCount colourCount = gson.fromJson(s.getAsJsonObject().get("cardColours"), ColourCount.class);
                    requirement = new TypeOfCardRequirement(colourCount);
                } break;
                default:
                    throw new IllegalStateException("Unexpected value: " + s.getAsJsonObject().get("requirementType").getAsString());
            }
            Resource resource = gson.fromJson(s.getAsJsonObject().get("resourceType"), Resource.class);
            switch(s.getAsJsonObject().get("abilityType").getAsString()) {
                case "whiteChange": {
                    specialAbility = new WhiteChangeAbility(resource);
                } break;
                case "production": {
                    specialAbility = new ProductionAbility(resource);
                } break;
                case "discount": {
                    specialAbility = new DiscountAbility(resource);
                } break;
                case "deposit": {
                    specialAbility = new DepositAbility(resource);
                } break;
            }
            leadersList.add(new LeaderCard(id, victoryPoints, requirement, specialAbility));
        }
        leadersList.size();
    }
}
