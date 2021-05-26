package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.exceptions.action.PaymentFailedException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardShopActionTest {
    @Test //shop remove correctly the card and its placed in the right place.
    void legitimateBuy(){
        PlayerDashboard player = createPlayer();
        Shop shop = createShop();
        ResourceCount payment = new ResourceCount(1,0,0,0,0);
        CardShopAction action = new CardShopAction(shop,2,3,0,payment,new ResourceCount(0, 0, 0, 0, 0));
        action.useAction(player);
        assertEquals(0,payment.getCoins()); //DiscountAbility works fine
        Production prod = new Production(new ResourceCount(0,0,0,0,0),new ResourceCount(0,0,0,0,0));
        DevelopmentCard cardPurple12 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.PURPLE);
        assertEquals(player.getDevCards()[0].getFirst(),cardPurple12);
        assertEquals(3,shop.getGrid()[2][3].getDeck().size());
    }
    @Test //try to put level 1 card on a level 2 deck
    void illegitimateBuy1(){
        PlayerDashboard player = createPlayerWithCards();
        Shop shop = createShop();
        ResourceCount payment = new ResourceCount(1,0,0,0,0);
        CardShopAction action = new CardShopAction(shop,2,3,0,payment,new ResourceCount(0, 0, 0, 0, 0));
        //assertFalse(action.useAction(player));
        assertThrows(PaymentFailedException.class, () -> action.useAction(player));
    }
    @Test//try to put level 2 card on a level 0 deck
    void illegitimateBuy2(){
        PlayerDashboard player = createPlayer();
        Shop shop = createShop();
        ResourceCount payment = new ResourceCount(1,0,0,0,0);
        CardShopAction action = new CardShopAction(shop,1,3,0,payment,new ResourceCount(0, 0, 0, 0, 0));
        //assertFalse(action.useAction(player));
        assertThrows(PaymentFailedException.class, () -> action.useAction(player));
    }
    @Test //shop remove correctly the card and its placed in the right place.
    void legitimateBuyCheckDiscounts(){
        PlayerDashboard player = createPlayer();
        Shop shop = createShop();
        ResourceCount payment = new ResourceCount(1,1,0,0,0);
        CardShopAction action = new CardShopAction(shop,2,2,0,payment,new ResourceCount(0, 0, 0, 0, 0));
        action.useAction(player);
        assertEquals(0,payment.getCoins()); //DiscountAbility works fine
        assertEquals(0,payment.getRocks());
        Production prod = new Production(new ResourceCount(0,0,0,0,0),new ResourceCount(0,0,0,0,0));
        DevelopmentCard cardYellow12 = new DevelopmentCard(0,1,new ResourceCount(1,1,0,0,0),prod,1, CardColour.YELLOW);
        assertEquals(player.getDevCards()[0].getFirst(),cardYellow12);
        assertEquals(3,shop.getGrid()[2][2].getDeck().size());
    }
    PlayerDashboard createPlayer(){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,2);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(0,createLeaderCardDiscount(true));
        leaderCards.add(0,createLeaderCardDiscount2(true));
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,nickname,2,false);
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        return player;
    }
    PlayerDashboard createPlayerWithCards(){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,2);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(0,createLeaderCard(false));
        leaderCards.add(0,createLeaderCard(false));
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,nickname,2,false);
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        devCards[0].addCard(createDevCard(1));
        devCards[0].addCard(createDevCard(2));
        devCards[1].addCard(createDevCard(1));
        return player;
    }
    LeaderCard createLeaderCard(boolean inGame){
        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new DepositAbility(Resource.SERVANT);
        LeaderCard leader = new LeaderCard(0,0,requirement,specialAbility);
        if(inGame)
            leader.setInGame();
        return leader;
    }

    LeaderCard createLeaderCardDiscount(boolean inGame){
        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new DiscountAbility(Resource.COIN);
        LeaderCard leader = new LeaderCard(0,0,requirement,specialAbility);
        if(inGame)
            leader.setInGame();
        return leader;
    }
    LeaderCard createLeaderCardDiscount2(boolean inGame){
        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new DiscountAbility(Resource.ROCK);
        LeaderCard leader = new LeaderCard(0,0,requirement,specialAbility);
        if(inGame)
            leader.setInGame();
        return leader;
    }
    DevelopmentCard createDevCard(int level){
        Production prod = new Production(new ResourceCount(1,2,0,0,0),new ResourceCount(0,0,3,0,0));
        return new DevelopmentCard(0, 5,new ResourceCount(0,0,0,0,0),prod,level, CardColour.BLUE);
    }
    Shop createShop(){
        DeckShop[][] testStructure = new DeckShop[3][4];
        ServerLobby shopObserver = new ServerLobby(2,1);
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