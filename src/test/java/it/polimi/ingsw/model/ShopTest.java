package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    @Test
    void buy() { //CHECK BUY METHODS AND ALL CORNER CASES (DECK EMPTY)
        Shop shop = createShop();
        Production prod = new Production(new ResourceCount(0,0,0,0,0),new ResourceCount(0,0,0,0,0));
        DevelopmentCard cardYellow = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.YELLOW);
        DevelopmentCard bought = shop.buy(1,2);
        assertEquals(bought, cardYellow);
        assertTrue(shop.getGrid()[1][2].getDeck().size() == 3);
        bought = shop.buy(1,2);
        bought = shop.buy(1,2);
        bought = shop.buy(1,2);
        assertTrue(shop.getGrid()[1][2].getDeck().size() == 0);
        bought = shop.buy(1,2);
        assertTrue(shop.getGrid()[1][2].getDeck().size() == 0);
        assertTrue(bought == null);
    }

    @Test
    void discardFromToken() {
        Shop shop = createShop();

    }


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