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
        DevelopmentCard cardPurple = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard bought = shop.buy(0,3);
        assertEquals(bought, cardPurple);
        assertEquals(3, shop.getGrid()[0][3].getDeck().size());
        assertEquals(4, shop.getGrid()[1][3].getDeck().size()); //same problem with removeFirst()
        bought = shop.buy(0,3);
        bought = shop.buy(0,3);
        bought = shop.buy(0,3);
        assertEquals(0, shop.getGrid()[0][3].getDeck().size());
        bought = shop.buy(0,3);
        assertEquals(0, shop.getGrid()[0][3].getDeck().size());
        assertNull(bought);
    }

    @Test
    void discardFromToken() {
        Shop shop = createShop();
        CardColour purple = CardColour.PURPLE;
        assertEquals(4, shop.getGrid()[0][3].getDeck().size());
        assertEquals(4, shop.getGrid()[1][3].getDeck().size());
        assertEquals(4, shop.getGrid()[2][3].getDeck().size());
        shop.discardFromToken(purple);
        assertEquals(2, shop.getGrid()[2][3].getDeck().size());
        assertEquals(4, shop.getGrid()[1][3].getDeck().size());
        //assertTrue(shop.getGrid()[2][3].getDeck().size() == 2);
        //shop.discardFromToken(purple);
        //assertTrue(shop.getGrid()[0][3].getDeck().size() == 0);
        //assertTrue(shop.getGrid()[1][3].getDeck().size() == 4);
        //shop.discardFromToken(purple);
        //assertTrue(shop.getGrid()[0][3].getDeck().size() == 0 && shop.getGrid()[1][3].getDeck().size() == 2);

    }


    Shop createShop(){
        Deck[][] testStructure = new DeckShop[3][4];

        Production prod = new Production(new ResourceCount(0,0,0,0,0),new ResourceCount(0,0,0,0,0));
        DevelopmentCard cardGreen = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.GREEN);
        DevelopmentCard cardBlue = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.BLUE);
        DevelopmentCard cardYellow = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.YELLOW);
        DevelopmentCard cardPurple1 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple2 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple3 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple4 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple5 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple6 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple7 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple8 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple9 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple10 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple11 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple12 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);


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
        test4.add(cardPurple1);
        test4.add(cardPurple2);
        test4.add(cardPurple3);
        test4.add(cardPurple4);
        DeckShop deckPurple = new DeckShop(test4);
        ArrayList<DevelopmentCard> test5 = new ArrayList<DevelopmentCard>();
        test5.add(cardPurple5);
        test5.add(cardPurple6);
        test5.add(cardPurple7);
        test5.add(cardPurple8);
        DeckShop deckPurple1 = new DeckShop(test5);
        ArrayList<DevelopmentCard> test6 = new ArrayList<DevelopmentCard>();
        test6.add(cardPurple9);
        test6.add(cardPurple10);
        test6.add(cardPurple11);
        test6.add(cardPurple12);
        DeckShop deckPurple2 = new DeckShop(test6);


        testStructure[0][0] = deckGreen;
        testStructure[0][1] = deckBlue;
        testStructure[0][2] = deckYellow;
        testStructure[0][3] = deckPurple;
        testStructure[1][0] = deckGreen;
        testStructure[1][1] = deckBlue;
        testStructure[1][2] = deckYellow;
        testStructure[1][3] = deckPurple1;
        testStructure[2][0] = deckGreen;
        testStructure[2][1] = deckBlue;
        testStructure[2][2] = deckYellow;
        testStructure[2][3] = deckPurple2;
        Shop shop = new Shop(testStructure);
        return shop;
    }

}