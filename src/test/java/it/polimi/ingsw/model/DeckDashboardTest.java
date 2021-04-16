package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckDashboardTest {

    DeckDashboard buildDeck() {
        DeckDashboard testDeck = new DeckDashboard();
        ResourceCount testResourceCount = new ResourceCount(0, 0, 0, 7, 0);
        ResourceCount testInput = new ResourceCount(0, 0, 1, 0, 0);
        ResourceCount testOutput = new ResourceCount(1, 0, 0, 0, 3);
        Production testProductionPower = new Production(testInput, testOutput);
        DevelopmentCard testCard = new DevelopmentCard(2, testResourceCount, testProductionPower, 1, CardColour.GREEN);
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
        DeckDashboard testDeckOld = buildDeck();
        ResourceCount testResourceCount = new ResourceCount(0, 2, 0, 0, 0);
        ResourceCount testInput = new ResourceCount(0, 0, 1, 0, 0);
        ResourceCount testOutput = new ResourceCount(0, 0, 0, 0, 1);
        Production testProductionPower = new Production(testInput, testOutput);
        DevelopmentCard testCard = new DevelopmentCard(1, testResourceCount, testProductionPower, 3, CardColour.YELLOW);
        testDeck.addCard(testCard);
        assertEquals(testCard, testDeck.getCard(0));
        assertEquals(testCard, testDeck.getFirst());
        for(int i = 0; i < testDeckOld.getDeck().size(); i++) {
            assertEquals(testDeckOld.getCard(i), testDeck.getCard(i+1));
        }
    }

    @Test
    void removeCardTest() {
        DeckDashboard testDeck = buildDeck();
        DeckDashboard oldDeck = buildDeck();
        testDeck.removeFirst();
        for(int i = 0; i < testDeck.getDeck().size(); i++) {
            assertEquals(testDeck.getCard(i), oldDeck.getCard(i+1));
        }
    }

    @Test
    void addCardInFullDeck() {
        DeckDashboard testDeck = buildDeck();
        ResourceCount testResourceCount = new ResourceCount(0, 0, 0, 7, 0);
        ResourceCount testInput = new ResourceCount(0, 0, 1, 0, 0);
        ResourceCount testOutput = new ResourceCount(1, 0, 0, 0, 3);
        Production testProductionPower = new Production(testInput, testOutput);
        DevelopmentCard testCard = new DevelopmentCard(2, testResourceCount, testProductionPower, 3, CardColour.GREEN);
        testDeck.addCard(testCard);
        assertThrows(IllegalStateException.class, () -> testDeck.addCard(testCard));
    }

    @Test
    void addIllegalCard() {
        DeckDashboard testDeck = buildDeck();
        ResourceCount testResourceCount = new ResourceCount(0, 0, 0, 7, 0);
        ResourceCount testInput = new ResourceCount(0, 0, 1, 0, 0);
        ResourceCount testOutput = new ResourceCount(1, 0, 0, 0, 3);
        Production testProductionPower = new Production(testInput, testOutput);
        DevelopmentCard testCard = new DevelopmentCard(2, testResourceCount, testProductionPower, 1, CardColour.GREEN);
        assertThrows(IllegalArgumentException.class, () -> testDeck.addCard(testCard));

        DeckDashboard testDeck2 = buildDeck();
        testResourceCount = new ResourceCount(0, 0, 0, 7, 0);
        testInput = new ResourceCount(0, 0, 1, 0, 0);
        testOutput = new ResourceCount(1, 0, 0, 0, 3);
        testProductionPower = new Production(testInput, testOutput);
        DevelopmentCard testCard2 = new DevelopmentCard(2, testResourceCount, testProductionPower, 1, CardColour.GREEN);
        assertThrows(IllegalArgumentException.class, () -> testDeck2.addCard(testCard2));

        DeckDashboard testDeck3 = new DeckDashboard();
        testResourceCount = new ResourceCount(0, 0, 0, 7, 0);
        testInput = new ResourceCount(0, 0, 1, 0, 0);
        testOutput = new ResourceCount(1, 0, 0, 0, 3);
        testProductionPower = new Production(testInput, testOutput);
        DevelopmentCard testCard3 = new DevelopmentCard(2, testResourceCount, testProductionPower, 2, CardColour.GREEN);
        assertThrows(IllegalArgumentException.class, () -> testDeck3.addCard(testCard3));
    }
}