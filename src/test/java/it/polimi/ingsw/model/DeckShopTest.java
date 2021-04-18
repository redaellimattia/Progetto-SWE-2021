package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckShopTest {

    DeckShop buildDeck(int level, CardColour colour) {
        ArrayList<DevelopmentCard> testArray = new ArrayList<DevelopmentCard>();
        ResourceCount testResourceCount = new ResourceCount(0, 0, 0, 7, 0);
        ResourceCount testInput = new ResourceCount(0, 0, 1, 0, 0);
        ResourceCount testOutput = new ResourceCount(1, 0, 0, 0, 3);
        Production testProductionPower = new Production(testInput, testOutput);
        DevelopmentCard testCard1 = new DevelopmentCard(2, testResourceCount, testProductionPower, level, colour);
        DevelopmentCard testCard2 = new DevelopmentCard(2, testResourceCount, testProductionPower, level, colour);
        DevelopmentCard testCard3 = new DevelopmentCard(2, testResourceCount, testProductionPower, level, colour);
        DevelopmentCard testCard4 = new DevelopmentCard(2, testResourceCount, testProductionPower, level, colour);
        testArray.add(testCard1);
        testArray.add(testCard2);
        testArray.add(testCard3);
        testArray.add(testCard4);
        DeckShop testShop = new DeckShop(testArray);
        return testShop;
    }

    @Test
    void testDeck() {
        DeckShop testDeck = buildDeck(3, CardColour.GREEN);
        assertTrue(testDeck.getDeck().size() == 4);
        for(int i = 0; i < testDeck.getDeck().size(); i++) {
            assertEquals(CardColour.GREEN, testDeck.getCard(i).getColour());
            assertEquals(3, testDeck.getCard(i).getLevel());
        }
    }

    @Test
    void illegalDeck() {
        // Adding a card with different level should fail
        ArrayList<DevelopmentCard> testArray = new ArrayList<DevelopmentCard>();
        ResourceCount testResourceCount = new ResourceCount(0, 0, 0, 7, 0);
        ResourceCount testInput = new ResourceCount(0, 0, 1, 0, 0);
        ResourceCount testOutput = new ResourceCount(1, 0, 0, 0, 3);
        Production testProductionPower = new Production(testInput, testOutput);
        DevelopmentCard testCard1 = new DevelopmentCard(2, testResourceCount, testProductionPower, 3, CardColour.GREEN);
        DevelopmentCard testCard2 = new DevelopmentCard(2, testResourceCount, testProductionPower, 3, CardColour.GREEN);
        DevelopmentCard testCard3 = new DevelopmentCard(2, testResourceCount, testProductionPower, 2, CardColour.GREEN);
        DevelopmentCard testCard4 = new DevelopmentCard(2, testResourceCount, testProductionPower, 3, CardColour.GREEN);
        testArray.add(testCard1);
        testArray.add(testCard2);
        testArray.add(testCard3);
        testArray.add(testCard4);
        assertThrows(IllegalArgumentException.class, () -> new DeckShop(testArray));

        // Adding a card of different colour should fail
        ArrayList<DevelopmentCard> testArray2 = new ArrayList<DevelopmentCard>();
        testCard4 = new DevelopmentCard(2, testResourceCount, testProductionPower, 3, CardColour.PURPLE);
        testArray2.add(testCard1);
        testArray2.add(testCard2);
        testArray2.add(testCard3);
        testArray2.add(testCard4);
        assertThrows(IllegalArgumentException.class, () -> new DeckShop(testArray2));
    }

    @Test
    void addCardTest() {
        DeckShop testDeck = buildDeck(3, CardColour.GREEN);
        ResourceCount testResourceCount = new ResourceCount(0, 2, 0, 0, 0);
        ResourceCount testInput = new ResourceCount(0, 0, 1, 0, 0);
        ResourceCount testOutput = new ResourceCount(0, 0, 0, 0, 1);
        Production testProductionPower = new Production(testInput, testOutput);
        DevelopmentCard testCard = new DevelopmentCard(1, testResourceCount, testProductionPower, 3, CardColour.GREEN);
        assertThrows(IllegalStateException.class, () -> testDeck.addCard(testCard));
    }

    @Test
    void removeCardTest() {
        DeckShop testDeck = buildDeck(3, CardColour.GREEN);
        DeckShop oldDeck = buildDeck(3, CardColour.GREEN);
        testDeck.removeFirst();
        assertTrue(testDeck.getDeck().size() == 3);
        for(int i = 0; i < testDeck.getDeck().size(); i++) {
            assertEquals(testDeck.getCard(i), oldDeck.getCard(i+1));
        }
    }
}