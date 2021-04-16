package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductionActionTest {

    /*@Test
    void useProductionAction() { //DEVCARD PRODUCTION
        //HYBRID PAYMENT
        ProductionAction action = new ProductionAction();
        PlayerDashboard player = createPlayer(true);
        ResourceCount storageCount = new ResourceCount(1,0,0,0,0);
        ResourceCount chestCount = new ResourceCount(0,2,0,0,0);
        DevelopmentCard card = createDevCard(3);
        assertTrue(action.useProductionAction(card,storageCount,chestCount,player));
        assertEquals(card.getProductionPower().getOutput(),action.getBufferOutput()); //Buffer equals to production output

        //STORAGE ONLY PAYMENT
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = new ResourceCount(1,2,0,0,0); //Correct payment with storage
        chestCount = null;
        card = createDevCard(3);
        assertTrue(action.useProductionAction(card,storageCount,chestCount,player));
        assertEquals(card.getProductionPower().getOutput(),action.getBufferOutput()); //Buffer equals to production output

        //CHEST ONLY PAYMENT
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = null;
        chestCount = new ResourceCount(1,2,0,0,0); //Correct payment with chest
        card = createDevCard(3);
        assertTrue(action.useProductionAction(card,storageCount,chestCount,player));
        assertEquals(card.getProductionPower().getOutput(),action.getBufferOutput()); //Buffer equals to production output

        //CANT PAY
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = null;
        chestCount = new ResourceCount(0,2,0,0,0);
        card = createDevCard(3);
        assertFalse(action.useProductionAction(card,storageCount,chestCount,player));

        //OVERPAY
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = null;
        chestCount = new ResourceCount(5,5,5,5,0);
        card = createDevCard(3);
        assertFalse(action.useProductionAction(card,storageCount,chestCount,player));

        //WRONG CARD, DIFF LEVEL
        action = new ProductionAction();
        player = createPlayer(true);
        chestCount = new ResourceCount(1,2,0,0,0);
        card = createDevCard(2);
        assertFalse(action.useProductionAction(card,null,chestCount,player));

    }

    @Test
    void testUseProductionAction() { //LEADERCARD PRODUCTION
        //STORAGE ONLY PAYMENT
        ProductionAction action = new ProductionAction();
        PlayerDashboard player = createPlayer(true);
        ResourceCount storageCount = new ResourceCount(1,0,0,0,0);
        ResourceCount chestCount = null;
        ResourceCount result = new ResourceCount(0,2,0,0,0);
        ResourceCount resultBuff = new ResourceCount(1,0,0,0,1);
        LeaderCard card = createLeaderCard(true);
        assertTrue(action.useProductionAction(card,storageCount,chestCount,player,Resource.COIN));
        assertEquals(result,player.getStorage().readStorage()); //Paid correctly
        assertEquals(resultBuff,action.getBufferOutput()); //Buffer equals to production output

        //CHEST ONLY PAYMENT
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = null;
        chestCount = new ResourceCount(1,0,0,0,0);
        result = new ResourceCount(4,5,0,0,0);
        resultBuff = new ResourceCount(1,0,0,0,1);
        assertTrue(action.useProductionAction(card,storageCount,chestCount,player,Resource.COIN));
        assertEquals(result,player.getChest()); //Paid correctly
        assertEquals(resultBuff, action.getBufferOutput()); //Buffer equals to production output

        //CANT PAY
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = null;
        chestCount = new ResourceCount(0,0,0,0,0);
        assertFalse(action.useProductionAction(card,storageCount,chestCount,player,Resource.COIN));

        //OVERPAY
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = null;
        chestCount = new ResourceCount(5,5,5,5,0);
        assertFalse(action.useProductionAction(card,storageCount,chestCount,player,Resource.COIN));

        //WRONG CARD, IT ISNT IN GAME
        action = new ProductionAction();
        player = createPlayer(false);
        card = createLeaderCard(false);
        storageCount = null;
        chestCount = new ResourceCount(1,0,0,0,0);
        assertFalse(action.useProductionAction(card,storageCount,chestCount,player,Resource.COIN));

        //WRONG CARD, Passing as it is inGame but it actually isnt
        action = new ProductionAction();
        player = createPlayer(false);
        storageCount = null;
        chestCount = new ResourceCount(1,0,0,0,0);
        assertFalse(action.useProductionAction(card,storageCount,chestCount,player,Resource.COIN));

        //WRONG CARD, NOT PROD ABILITY
        action = new ProductionAction();
        player = createPlayer(false);
        card = createLeaderCardNotProd();
        storageCount = null;
        chestCount = new ResourceCount(1,0,0,0,0);
        assertFalse(action.useProductionAction(card,storageCount,chestCount,player,Resource.COIN));

    }

    @Test
    void testUseProductionAction1() { //BASICPRODUCTION
        //HYBRID PAYMENT
        ProductionAction action = new ProductionAction();
        PlayerDashboard player = createPlayer(true);
        ResourceCount storageCount = new ResourceCount(1,0,0,0,0);
        ResourceCount chestCount = new ResourceCount(0,1,0,0,0);
        ResourceCount resultStorage = new ResourceCount(0,2,0,0,0);
        ResourceCount resultChest = new ResourceCount(5,4,0,0,0);
        ResourceCount resultBuff = new ResourceCount(1,0,0,0,0);
        assertTrue(action.useProductionAction(Resource.COIN,storageCount,chestCount,player));
        assertEquals(resultStorage,player.getStorage().readStorage()); //Paid correctly
        assertEquals(resultChest,player.getChest()); //Paid correctly
        assertEquals(resultBuff,action.getBufferOutput()); //Buffer equals to production output

        //STORAGE ONLY PAYMENT
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = new ResourceCount(1,1,0,0,0);
        chestCount = null;
        resultStorage = new ResourceCount(0,1,0,0,0);
        resultBuff = new ResourceCount(1,0,0,0,0);
        assertTrue(action.useProductionAction(Resource.COIN,storageCount,chestCount,player));
        assertEquals(resultStorage,player.getStorage().readStorage()); //Paid correctly
        assertEquals(resultBuff,action.getBufferOutput()); //Buffer equals to production output

        //CHEST ONLY PAYMENT
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = null;
        chestCount = new ResourceCount(1,1,0,0,0);
        resultChest = new ResourceCount(4,4,0,0,0);
        resultBuff = new ResourceCount(1,0,0,0,0);
        assertTrue(action.useProductionAction(Resource.COIN,storageCount,chestCount,player));
        assertEquals(resultChest,player.getChest()); //Paid correctly
        assertEquals(resultBuff,action.getBufferOutput()); //Buffer equals to production output

        //WRONG INPUT !=2: >2
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = new ResourceCount(1,0,0,0,0);
        chestCount = new ResourceCount(1,1,0,0,0);
        assertFalse(action.useProductionAction(Resource.COIN,storageCount,chestCount,player));

        //WRONG INPUT !=2: <2
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = new ResourceCount(1,0,0,0,0);
        chestCount = new ResourceCount(0,0,0,0,0);
        assertFalse(action.useProductionAction(Resource.COIN,storageCount,chestCount,player));

        //WRONG CHOSEN RESOURCE (CANT CHOOSE FAITH)
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = new ResourceCount(1,0,0,0,0);
        chestCount = new ResourceCount(0,1,0,0,0);
        assertFalse(action.useProductionAction(Resource.FAITH,storageCount,chestCount,player));

    }

    @Test
    void endProductionAction() {
        //ACTION THAT ADDS 1 COIN AND PATHPOSITION++
        ProductionAction action = new ProductionAction();
        PlayerDashboard player = createPlayer(true);
        ResourceCount storageCount = new ResourceCount(1,0,0,0,0);
        ResourceCount result = new ResourceCount(0,2,0,0,0);
        ResourceCount resultBuff = new ResourceCount(1,0,0,0,1);

        LeaderCard card = createLeaderCard(true);
        action.useProductionAction(card,storageCount,null,player,Resource.COIN);
        assertEquals(result,player.getStorage().readStorage()); //Paid correctly
        assertEquals(resultBuff,action.getBufferOutput()); //Buffer equals to production output


        //BASICPRODUCTION THAT ADDS 1 COIN
        ResourceCount chestCount = new ResourceCount(1,1,0,0,0);
        ResourceCount resultChest = new ResourceCount(4,4,0,0,0);
        resultBuff.sumCounts(new ResourceCount(1,0,0,0,0));
        action.useProductionAction(Resource.COIN,null,chestCount,player);
        assertEquals(resultChest,player.getChest()); //Paid correctly
        assertEquals(resultBuff,action.getBufferOutput()); //Buffer equals to production output

        //END ACTION THEN CHECK IF PATHPOSITION INCREASED, AND CHEST INCREASED
        action.endProductionAction(player);
        ResourceCount newChest = new ResourceCount(6,4,0,0,0);
        assertEquals(1,player.getPathPosition());
        assertEquals(newChest,player.getChest());
    }
    PlayerDashboard createPlayer(boolean inGame){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,2);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ResourceCount input = new ResourceCount(0,0,0,0,0);
        ResourceCount output = new ResourceCount(0,0,0,0,0);
        Production basicProduction = new Production(input,output);

        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        leaderCards.add(0,createLeaderCard(inGame));
        leaderCards.add(0,createLeaderCard(false));
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,nickname,2);
        devCards[0].addCard(createDevCard(3));
        devCards[1].addCard(createDevCard(1));
        return player;
    }

    DevelopmentCard createDevCard(int level){
        Production prod = new Production(new ResourceCount(1,2,0,0,0),new ResourceCount(0,0,3,0,0));
        return new DevelopmentCard(5,new ResourceCount(0,0,0,0,0),prod,level, CardColour.BLUE);
    }

    LeaderCard createLeaderCard(boolean inGame){
        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new ProductionAbility(Resource.COIN);
        LeaderCard leader = new LeaderCard(0,requirement,specialAbility);
        if(inGame)
            leader.setInGame();
        return leader;
    }
    LeaderCard createLeaderCardNotProd(){
        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new DiscountAbility(Resource.COIN);
        LeaderCard leader = new LeaderCard(0,requirement,specialAbility);
        leader.setInGame();
        return leader;
    }*/
}