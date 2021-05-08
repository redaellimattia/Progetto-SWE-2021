package it.polimi.ingsw.model.card;

import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.model.DeckShop;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.CardColour;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public void importCards() {
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
}
