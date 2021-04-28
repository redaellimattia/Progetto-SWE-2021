package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.token.AdvanceToken;
import it.polimi.ingsw.model.token.DiscardToken;
import it.polimi.ingsw.model.token.SoloToken;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SoloTokenActionTest {

    public PlayerDashboard buildPlayerDashboard() {
        CounterTop testCounterTop1 = new CounterTop(Resource.COIN, 1);
        CounterTop testCounterTop2 = new CounterTop(Resource.ROCK, 2);
        CounterTop testCounterTop3 = new CounterTop(Resource.SHIELD, 3);
        Storage testStorage = new Storage(testCounterTop1, testCounterTop2, testCounterTop3);
        ResourceCount testChest = new ResourceCount(0, 0, 0, 0, 0);
        DeckDashboard[] testDevCards = new DeckDashboard[3];
        ArrayList<LeaderCard> testLeaderCards = new ArrayList<>(0);
        return new PlayerDashboard(testStorage, testChest, testDevCards, testLeaderCards, 0, "Test", 0);
    }

    public GameManager buildGameManager() {
        PlayerDashboard player1 = buildPlayerDashboard();
        PlayerDashboard lorenzo = buildPlayerDashboard();
        ArrayList<PlayerDashboard> players = new ArrayList<PlayerDashboard>();
        players.add(player1);
        players.add(lorenzo);
        Game game = new Game(players, createShop(), new MarketDashboard(new MarketMarble[3][4], new MarketMarble(MarbleColour.PURPLE)), new ArrayList<Card>(), new ArrayList<SoloToken>());
        GameManager gameManager = new GameManager(game, new PlayerTurnManager(player1),true);
        return gameManager;
    }

    Shop createShop() {
        Deck[][] testStructure = new DeckShop[3][4];

        Production prod = new Production(new ResourceCount(0,0,0,0,0),new ResourceCount(0,0,0,0,0));
        DevelopmentCard cardGreen = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.GREEN);
        DevelopmentCard cardBlue = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.BLUE);
        DevelopmentCard cardYellow = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.YELLOW);
        DevelopmentCard cardPurple = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);

        DevelopmentCard cardGreen2 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.GREEN);
        DevelopmentCard cardBlue2 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.BLUE);
        DevelopmentCard cardYellow2 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.YELLOW);
        DevelopmentCard cardPurple2 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,2, CardColour.PURPLE);

        DevelopmentCard cardGreen3 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.GREEN);
        DevelopmentCard cardBlue3 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.BLUE);
        DevelopmentCard cardYellow3 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.YELLOW);
        DevelopmentCard cardPurple3 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,3, CardColour.PURPLE);

        ArrayList<DevelopmentCard> test1 = new ArrayList<DevelopmentCard>();
        test1.add(cardGreen);
        test1.add(cardGreen);
        test1.add(cardGreen);
        test1.add(cardGreen);
        DeckShop deckGreen1 = new DeckShop(test1);

        ArrayList<DevelopmentCard> test2 = new ArrayList<DevelopmentCard>();
        test2.add(cardBlue);
        test2.add(cardBlue);
        test2.add(cardBlue);
        test2.add(cardBlue);
        DeckShop deckBlue1 = new DeckShop(test2);

        ArrayList<DevelopmentCard> test3 = new ArrayList<DevelopmentCard>();
        test3.add(cardYellow);
        test3.add(cardYellow);
        test3.add(cardYellow);
        test3.add(cardYellow);
        DeckShop deckYellow1 = new DeckShop(test3);

        ArrayList<DevelopmentCard> test4 = new ArrayList<DevelopmentCard>();
        test4.add(cardPurple);
        test4.add(cardPurple);
        test4.add(cardPurple);
        test4.add(cardPurple);
        DeckShop deckPurple1 = new DeckShop(test4);

        test1 = new ArrayList<DevelopmentCard>();
        test1.add(cardGreen2);
        test1.add(cardGreen2);
        test1.add(cardGreen2);
        test1.add(cardGreen2);
        DeckShop deckGreen2 = new DeckShop(test1);

        test2 = new ArrayList<DevelopmentCard>();
        test2.add(cardBlue2);
        test2.add(cardBlue2);
        test2.add(cardBlue2);
        test2.add(cardBlue2);
        DeckShop deckBlue2 = new DeckShop(test2);

        test3 = new ArrayList<DevelopmentCard>();
        test3.add(cardYellow2);
        test3.add(cardYellow2);
        test3.add(cardYellow2);
        test3.add(cardYellow2);
        DeckShop deckYellow2 = new DeckShop(test3);

        test4 = new ArrayList<DevelopmentCard>();
        test4.add(cardPurple2);
        test4.add(cardPurple2);
        test4.add(cardPurple2);
        test4.add(cardPurple2);
        DeckShop deckPurple2 = new DeckShop(test4);

        test1 = new ArrayList<DevelopmentCard>();
        test1.add(cardGreen3);
        test1.add(cardGreen3);
        test1.add(cardGreen3);
        test1.add(cardGreen3);
        DeckShop deckGreen3 = new DeckShop(test1);

        test2 = new ArrayList<DevelopmentCard>();
        test2.add(cardBlue3);
        test2.add(cardBlue3);
        test2.add(cardBlue3);
        test2.add(cardBlue3);
        DeckShop deckBlue3 = new DeckShop(test2);

        test3 = new ArrayList<DevelopmentCard>();
        test3.add(cardYellow3);
        test3.add(cardYellow3);
        test3.add(cardYellow3);
        test3.add(cardYellow3);
        DeckShop deckYellow3 = new DeckShop(test3);

        test4 = new ArrayList<DevelopmentCard>();
        test4.add(cardPurple3);
        test4.add(cardPurple3);
        test4.add(cardPurple3);
        test4.add(cardPurple3);
        DeckShop deckPurple3 = new DeckShop(test4);


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
        return shop;
    }

    SoloToken buildToken1() {
        return new AdvanceToken(true, buildGameManager());
    }

    SoloToken buildToken2() {
        return new DiscardToken(CardColour.PURPLE);
    }

    /*@Test
    void useTokens() {
        SoloToken testToken1 = buildToken1();
        SoloToken testToken2 = buildToken2();
        testToken1.useToken();
        testToken2.useToken();
    } */
}