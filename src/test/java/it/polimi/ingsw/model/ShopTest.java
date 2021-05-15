package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyDeckException;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.network.server.ServerThread;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    @Test
    void buy() throws EmptyDeckException { //CHECK BUY METHODS AND ALL CORNER CASES (DECK EMPTY)
        Shop shop = createShop();
        Production prod = new Production(new ResourceCount(0,0,0,0,0),new ResourceCount(0,0,0,0,0));
        DevelopmentCard cardPurple = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.PURPLE);
        DevelopmentCard bought = shop.buy(0,3);
        assertEquals(bought, cardPurple);
        assertEquals(3, shop.getGrid()[0][3].getDeck().size());
        assertEquals(4, shop.getGrid()[1][3].getDeck().size()); //same problem with removeFirst()
        bought = shop.buy(0,3);
        bought = shop.buy(0,3);
        bought = shop.buy(0,3);
        assertEquals(0, shop.getGrid()[0][3].getDeck().size());
        assertThrows(EmptyDeckException.class,() -> shop.buy(0, 3));
        assertEquals(0, shop.getGrid()[0][3].getDeck().size());

    }

    @Test
    void discardFromTokenSubsequentialDiscard() throws EmptyDeckException{
        Shop shop = createShop();
        CardColour purple = CardColour.PURPLE;
        assertEquals(4, shop.getGrid()[0][3].getDeck().size());
        assertEquals(4, shop.getGrid()[1][3].getDeck().size());
        assertEquals(4, shop.getGrid()[2][3].getDeck().size());
        shop.discardFromToken(purple);
        assertEquals(2, shop.getGrid()[2][3].getDeck().size());
        assertEquals(4, shop.getGrid()[1][3].getDeck().size());
        assertEquals(4, shop.getGrid()[0][3].getDeck().size());
        shop.discardFromToken(purple);
        assertEquals(0, shop.getGrid()[2][3].getDeck().size());
        assertEquals(4, shop.getGrid()[1][3].getDeck().size());
        shop.discardFromToken(purple);
        assertTrue(shop.getGrid()[2][3].getDeck().size() == 0 && shop.getGrid()[1][3].getDeck().size() == 2);
        DevelopmentCard bought = shop.buy(1,3);
        shop.discardFromToken(purple);
        assertEquals(0,shop.getGrid()[1][3].getDeck().size());
        assertEquals(3,shop.getGrid()[0][3].getDeck().size());
    }
    @Test //Even if column is empty the discard doesn't trigger anything, a method that check will be called by the controller to end the game in solo.
    void emptyWholeColumn(){
        Shop shop = createShop();
        CardColour purple = CardColour.PURPLE;
        shop.discardFromToken(purple);
        shop.discardFromToken(purple);
        shop.discardFromToken(purple);
        shop.discardFromToken(purple);
        shop.discardFromToken(purple);
        shop.discardFromToken(purple);
        assertEquals(0, shop.getGrid()[0][3].getDeck().size());
        assertEquals(0, shop.getGrid()[1][3].getDeck().size());
        assertEquals(0, shop.getGrid()[2][3].getDeck().size());
        shop.discardFromToken(purple);
        assertEquals(0, shop.getGrid()[0][3].getDeck().size());
        assertEquals(0, shop.getGrid()[1][3].getDeck().size());
        assertEquals(0, shop.getGrid()[2][3].getDeck().size());
    }

    Shop createShop(){
        DeckShop[][] testStructure = new DeckShop[3][4];
        ServerThread shopObserver = new ServerThread(2);
        Production prod = new Production(new ResourceCount(0,0,0,0,0),new ResourceCount(0,0,0,0,0));
        //GREEN CARDS
        DevelopmentCard cardGreen1 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.GREEN);
        DevelopmentCard cardGreen2 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.GREEN);
        DevelopmentCard cardGreen3 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.GREEN);
        DevelopmentCard cardGreen4 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.GREEN);
        DevelopmentCard cardGreen5 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.GREEN);
        DevelopmentCard cardGreen6 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.GREEN);
        DevelopmentCard cardGreen7 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.GREEN);
        DevelopmentCard cardGreen8 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.GREEN);
        DevelopmentCard cardGreen9 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.GREEN);
        DevelopmentCard cardGreen10 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.GREEN);
        DevelopmentCard cardGreen11 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.GREEN);
        DevelopmentCard cardGreen12 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.GREEN);
        //BLUE CARDS
        DevelopmentCard cardBlue1 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.BLUE);
        DevelopmentCard cardBlue2 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.BLUE);
        DevelopmentCard cardBlue3 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.BLUE);
        DevelopmentCard cardBlue4 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.BLUE);
        DevelopmentCard cardBlue5 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.BLUE);
        DevelopmentCard cardBlue6 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.BLUE);
        DevelopmentCard cardBlue7 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.BLUE);
        DevelopmentCard cardBlue8 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.BLUE);
        DevelopmentCard cardBlue9 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.BLUE);
        DevelopmentCard cardBlue10 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.BLUE);
        DevelopmentCard cardBlue11 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.BLUE);
        DevelopmentCard cardBlue12 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.BLUE);
        //YELLOW CARDS
        DevelopmentCard cardYellow1 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.YELLOW);
        DevelopmentCard cardYellow2 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.YELLOW);
        DevelopmentCard cardYellow3 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.YELLOW);
        DevelopmentCard cardYellow4 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.YELLOW);
        DevelopmentCard cardYellow5 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.YELLOW);
        DevelopmentCard cardYellow6 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.YELLOW);
        DevelopmentCard cardYellow7 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.YELLOW);
        DevelopmentCard cardYellow8 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.YELLOW);
        DevelopmentCard cardYellow9 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.YELLOW);
        DevelopmentCard cardYellow10 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.YELLOW);
        DevelopmentCard cardYellow11 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.YELLOW);
        DevelopmentCard cardYellow12 = new DevelopmentCard(0,1,new ResourceCount(1,1,0,0,0),prod,1, CardColour.YELLOW);
        //PURPLE CARDS
        DevelopmentCard cardPurple1 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.PURPLE);
        DevelopmentCard cardPurple2 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.PURPLE);
        DevelopmentCard cardPurple3 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.PURPLE);
        DevelopmentCard cardPurple4 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.PURPLE);
        DevelopmentCard cardPurple5 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.PURPLE);
        DevelopmentCard cardPurple6 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.PURPLE);
        DevelopmentCard cardPurple7 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.PURPLE);
        DevelopmentCard cardPurple8 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.PURPLE);
        DevelopmentCard cardPurple9 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple10 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple11 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        DevelopmentCard cardPurple12 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);

        //GREEN DECKS
        ArrayList<DevelopmentCard> testG3 = new ArrayList<>();
        testG3.add(cardGreen1);
        testG3.add(cardGreen2);
        testG3.add(cardGreen3);
        testG3.add(cardGreen4);
        DeckShop deckGreen3 = new DeckShop(testG3);
        ArrayList<DevelopmentCard> testG2 = new ArrayList<>();
        testG2.add(cardGreen5);
        testG2.add(cardGreen6);
        testG2.add(cardGreen7);
        testG2.add(cardGreen8);
        DeckShop deckGreen2 = new DeckShop(testG2);
        ArrayList<DevelopmentCard> testG1 = new ArrayList<>();
        testG1.add(cardGreen9);
        testG1.add(cardGreen10);
        testG1.add(cardGreen11);
        testG1.add(cardGreen12);
        DeckShop deckGreen1 = new DeckShop(testG1);

        //BLUE DECKS
        ArrayList<DevelopmentCard> testB3 = new ArrayList<>();
        testB3.add(cardBlue1);
        testB3.add(cardBlue2);
        testB3.add(cardBlue3);
        testB3.add(cardBlue4);
        DeckShop deckBlue3 = new DeckShop(testB3);
        ArrayList<DevelopmentCard> testB2 = new ArrayList<>();
        testB2.add(cardBlue5);
        testB2.add(cardBlue6);
        testB2.add(cardBlue7);
        testB2.add(cardBlue8);
        DeckShop deckBlue2 = new DeckShop(testB2);
        ArrayList<DevelopmentCard> testB1 = new ArrayList<>();
        testB1.add(cardBlue9);
        testB1.add(cardBlue10);
        testB1.add(cardBlue11);
        testB1.add(cardBlue12);
        DeckShop deckBlue1 = new DeckShop(testB1);

        //YELLOW DECKS
        ArrayList<DevelopmentCard> testY3 = new ArrayList<>();
        testY3.add(cardYellow1);
        testY3.add(cardYellow2);
        testY3.add(cardYellow3);
        testY3.add(cardYellow4);
        DeckShop deckYellow3 = new DeckShop(testY3);
        ArrayList<DevelopmentCard> testY2 = new ArrayList<>();
        testY2.add(cardYellow5);
        testY2.add(cardYellow6);
        testY2.add(cardYellow7);
        testY2.add(cardYellow8);
        DeckShop deckYellow2 = new DeckShop(testY2);
        ArrayList<DevelopmentCard> testY1 = new ArrayList<>();
        testY1.add(cardYellow9);
        testY1.add(cardYellow10);
        testY1.add(cardYellow11);
        testY1.add(0,cardYellow12);
        DeckShop deckYellow1 = new DeckShop(testY1);

        //PURPLE DECKS
        ArrayList<DevelopmentCard> testP3 = new ArrayList<>();
        testP3.add(cardPurple1);
        testP3.add(cardPurple2);
        testP3.add(cardPurple3);
        testP3.add(cardPurple4);
        DeckShop deckPurple3 = new DeckShop(testP3);
        ArrayList<DevelopmentCard> testP2 = new ArrayList<>();
        testP2.add(cardPurple5);
        testP2.add(cardPurple6);
        testP2.add(cardPurple7);
        testP2.add(cardPurple8);
        DeckShop deckPurple2 = new DeckShop(testP2);
        ArrayList<DevelopmentCard> testP1 = new ArrayList<>();
        testP1.add(cardPurple9);
        testP1.add(cardPurple10);
        testP1.add(cardPurple11);
        testP1.add(cardPurple12);
        DeckShop deckPurple1 = new DeckShop(testP1);


        testStructure[0][0] = deckGreen3;
        testStructure[0][1] = deckBlue3;
        testStructure[0][2] = deckYellow3;
        testStructure[0][3] = deckPurple3;
        testStructure[1][0] = deckGreen2;
        testStructure[1][1] = deckBlue2;
        testStructure[1][2] = deckYellow2;
        testStructure[1][3] = deckPurple2;
        testStructure[2][0] = deckGreen1;
        testStructure[2][1] = deckBlue1;
        testStructure[2][2] = deckYellow1;
        testStructure[2][3] = deckPurple1;
        Shop shop = new Shop(testStructure);
        shop.addObserver(shopObserver);
        return shop;
    }

}