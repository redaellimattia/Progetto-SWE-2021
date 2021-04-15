package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiscardTokenTest {

    // In this test, all two cards will be removed from the level 1 row
    @Test
    void testDiscardTokenSimple() {
        Shop testShop = createShop();
        Shop testShopOld = createShop();
        CardColour testColour = CardColour.PURPLE;
        testShop.discardFromToken(testColour);
        for(int i=0; i<4; i++) {
            if(i == testColour.getColumn()) {
                assertTrue(testShop.getGrid()[2][i].getDeck().size() == 2);
                for(int j = 0; j<2; j++) {
                    assertEquals(testShopOld.getGrid()[2][i].getDeck().get(j+2), testShop.getGrid()[2][i].getDeck().get(j));
                }
            }
            else {
                for(int j = 0; j<4; j++) {
                    assertEquals(testShopOld.getGrid()[2][i].getDeck().get(j), testShop.getGrid()[2][i].getDeck().get(j));
                    assertEquals(testShopOld.getGrid()[1][i].getDeck().get(j), testShop.getGrid()[1][i].getDeck().get(j));
                    assertEquals(testShopOld.getGrid()[0][i].getDeck().get(j), testShop.getGrid()[0][i].getDeck().get(j));
                }
            }

        }
        /*int toDiscard = 2;
        // Checking how many cards will be discarded in each row
        int[] toDiscardInRow = new int[3];
        for(int i = 0; i<toDiscardInRow.length; i++) {
            toDiscardInRow[i] = 0;
        }
        int curRow = 2;
        while(toDiscard > 0 && curRow > 0) {
            if(!testShop.getGrid()[curRow][testColour.getColumn()].getDeck().isEmpty()) {
                // If the deck is not empty, then a card will be removed in this row
                toDiscardInRow[curRow]++;
                toDiscard--;
            }
            else {
                // Move to the next row
                curRow--;
            }

        }

        assertTrue(testShop.) */
    }

    // TO-DO: Create a more "real" shop (e.g. using cards of different levels)
    Shop createShop(){
        Deck[][] testStructure = new DeckShop[3][4];

        Production prod = new Production(new ResourceCount(0,0,0,0,0),new ResourceCount(0,0,0,0,0));
        DevelopmentCard cardGreen = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.GREEN);
        DevelopmentCard cardBlue = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.BLUE);
        DevelopmentCard cardYellow = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.YELLOW);
        DevelopmentCard cardPurple = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);

        ArrayList<DevelopmentCard> test1 = new ArrayList<DevelopmentCard>();
        test1.add(cardGreen);
        test1.add(cardGreen);
        test1.add(cardGreen);
        test1.add(cardGreen);

        DeckShop deckGreen = new DeckShop(test1);
        ArrayList<DevelopmentCard> test2 = new ArrayList<DevelopmentCard>();
        test2.add(cardBlue);
        test2.add(cardBlue);
        test2.add(cardBlue);
        test2.add(cardBlue);
        DeckShop deckBlue = new DeckShop(test2);
        ArrayList<DevelopmentCard> test3 = new ArrayList<DevelopmentCard>();
        test3.add(cardYellow);
        test3.add(cardYellow);
        test3.add(cardYellow);
        test3.add(cardYellow);
        DeckShop deckYellow = new DeckShop(test3);
        ArrayList<DevelopmentCard> test4 = new ArrayList<DevelopmentCard>();
        test4.add(cardPurple);
        test4.add(cardPurple);
        test4.add(cardPurple);
        test4.add(cardPurple);
        DeckShop deckPurple = new DeckShop(test4);

        testStructure[0][0] = deckGreen;
        testStructure[0][1] = deckBlue;
        testStructure[0][2] = deckYellow;
        testStructure[0][3] = deckPurple;
        testStructure[1][0] = deckGreen;
        testStructure[1][1] = deckBlue;
        testStructure[1][2] = deckYellow;
        testStructure[1][3] = deckPurple;
        testStructure[2][0] = deckGreen;
        testStructure[2][1] = deckBlue;
        testStructure[2][2] = deckYellow;
        testStructure[2][3] = deckPurple;
        Shop shop = new Shop(testStructure);
        return shop;
    }
}