package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColour;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckDashboardTest {

    DeckDashboard buildDeck() {
        DeckDashboard testDeck = new DeckDashboard();
        ResourceCount testResourceCount = new ResourceCount(0, 0, 0, 7, 0);
        ResourceCount testInput = new ResourceCount(0, 0, 1, 0, 0);
        ResourceCount testOutput = new ResourceCount(1, 0, 0, 0, 3);
        Production testProductionPower = new Production(testInput, testOutput);
        DevelopmentCard testCard = new DevelopmentCard(2, testResourceCount, testProductionPower, 3, CardColour.GREEN);
        testDeck.addCard(testCard);
        testResourceCount = new ResourceCount(4,0,0,0,0);
        testInput = new ResourceCount(0, 0, 1, 0, 0);
        testOutput = new ResourceCount(0,0,0,0,2);
        testProductionPower = new Production(testInput, testOutput);
        testCard = new DevelopmentCard(5, testResourceCount, testProductionPower, 2, CardColour.BLUE);
        testDeck.addCard(testCard);
        return testDeck;
    }

    @Test
    void testDeck() {
        DeckDashboard testDeck = buildDeck();
        assertEquals(4, testDeck.getCard(0).getCost().getCoins());
        assertEquals(0, testDeck.getCard(1).getCost().getCoins());
    }

    @Test
    void addCardTest() {
        DeckDashboard testDeck = buildDeck();
        ResourceCount testResourceCount = new ResourceCount(0, 2, 0, 0, 0);
        ResourceCount testInput = new ResourceCount(0, 0, 1, 0, 0);
        ResourceCount testOutput = new ResourceCount(0, 0, 0, 0, 1);
        Production testProductionPower = new Production(testInput, testOutput);
        DevelopmentCard testCard = new DevelopmentCard(1, testResourceCount, testProductionPower, 1, CardColour.YELLOW);
        testDeck.addCard(testCard);
        assertEquals(testCard, testDeck.getCard(0));
    }

    @Test
    void removeCardTest() {
        DeckDashboard testDeck = buildDeck();
        DeckDashboard oldDeck = buildDeck();
        testDeck.removeFirst();
        assertEquals(oldDeck.getCard(1), testDeck.getCard(0));
    }
}