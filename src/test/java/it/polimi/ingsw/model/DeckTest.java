package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    ArrayList<DevelopmentCard> buildDeck() {
        ArrayList<DevelopmentCard> testDeck = new ArrayList<DevelopmentCard>();
        ResourceCount testResourceCount = new ResourceCount(0, 0, 0, 7, 0);
        ResourceCount testInput = new ResourceCount(0, 0, 1, 0, 0);
        ResourceCount testOutput = new ResourceCount(1, 0, 0, 0, 3);
        Production testProductionPower = new Production(testInput, testOutput);
        DevelopmentCard testCard = new DevelopmentCard(2, testResourceCount, testProductionPower, 3, CardColour.GREEN);
        testDeck.add(testCard);
        testResourceCount = new ResourceCount(4,0,0,0,0);
        testInput = new ResourceCount(0, 0, 1, 0, 0);
        testOutput = new ResourceCount(0,0,0,0,2);
        testProductionPower = new Production(testInput, testOutput);
        testCard = new DevelopmentCard(5, testResourceCount, testProductionPower, 2, CardColour.BLUE);
        testDeck.add(testCard);
        return testDeck;
    }

}