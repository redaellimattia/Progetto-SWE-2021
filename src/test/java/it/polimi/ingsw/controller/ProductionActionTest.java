package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductionActionTest {

    @Test
    void useProductionAction() { //DEVCARD PRODUCTION
        //HYBRID PAYMENT
        ProductionAction action = new ProductionAction();
        PlayerDashboard player = createPlayer(true);
        ResourceCount storageCount = new ResourceCount(1,0,0,0,0);
        ResourceCount chestCount = new ResourceCount(0,2,0,0,0);
        DevelopmentCard card = createDevCard();
        assertTrue(action.useProductionAction(card,storageCount,chestCount,player));
        assertEquals(card.getProductionPower().getOutput(),action.getBufferOutput()); //Buffer equals to production output

        //STORAGE ONLY PAYMENT
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = new ResourceCount(1,2,0,0,0); //Correct payment with storage
        chestCount = null;
        card = createDevCard();
        assertTrue(action.useProductionAction(card,storageCount,chestCount,player));
        assertEquals(card.getProductionPower().getOutput(),action.getBufferOutput()); //Buffer equals to production output

        //CHEST ONLY PAYMENT
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = null;
        chestCount = new ResourceCount(1,2,0,0,0); //Correct payment with chest
        card = createDevCard();
        assertTrue(action.useProductionAction(card,storageCount,chestCount,player));
        assertEquals(card.getProductionPower().getOutput(),action.getBufferOutput()); //Buffer equals to production output

        //CANT PAY
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = null;
        chestCount = new ResourceCount(0,2,0,0,0);
        card = createDevCard();
        assertFalse(action.useProductionAction(card,storageCount,chestCount,player));

        //OVERPAY
        action = new ProductionAction();
        player = createPlayer(true);
        storageCount = null;
        chestCount = new ResourceCount(5,5,5,5,0);
        card = createDevCard();
        assertFalse(action.useProductionAction(card,storageCount,chestCount,player));

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

    }

    @Test
    void testUseProductionAction1() {
    }

    @Test
    void endProductionAction() {
        ProductionAction action = new ProductionAction();
        ResourceCount playerChest = new ResourceCount(5,5,0,0,0);
        ResourceCount result = ResourceCount.getTotal(playerChest,action.getBufferOutput());
        //CALL TO USEPRODUCTIONACTION
        //action.endProductionAction(player);
        assertEquals(result,playerChest);
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
        return new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,nickname,2);
    }

    DevelopmentCard createDevCard(){
        Production prod = new Production(new ResourceCount(1,2,0,0,0),new ResourceCount(0,0,3,0,0));
        return new DevelopmentCard(5,new ResourceCount(0,0,0,0,0),prod,3, CardColour.BLUE);
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
}