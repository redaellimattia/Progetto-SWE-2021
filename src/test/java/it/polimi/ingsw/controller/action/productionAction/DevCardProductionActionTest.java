package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DevCardProductionActionTest {

    @Test
    void useActionHybridPayment() { //DEVCARD PRODUCTION HYBRID PAYMENT
        PlayerDashboard player = createPlayer();
        ResourceCount storageCount = new ResourceCount(1, 0, 0, 0, 0);
        ResourceCount chestCount = new ResourceCount(0, 2, 0, 0, 0);
        DevelopmentCard card = createDevCard(3);
        DevCardProductionAction action = new DevCardProductionAction(card,storageCount,chestCount);
        assertTrue(action.useAction(player));
        assertEquals(card.getProductionPower().getOutput(), player.getBufferProduction()); //Buffer equals to production output
    }
    @Test
    void useActionStorageOnlyPayment(){ //STORAGE ONLY PAYMENT
        PlayerDashboard player = createPlayer();
        ResourceCount storageCount = new ResourceCount(1,2,0,0,0); //Correct payment with storage
        DevelopmentCard card = createDevCard(3);
        DevCardProductionAction action = new DevCardProductionAction(card,storageCount,null);
        assertTrue(action.useAction(player));
        assertEquals(card.getProductionPower().getOutput(),player.getBufferProduction()); //Buffer equals to production output
    }
    @Test
    void useActionChestOnlyPayment() { //CHEST ONLY PAYMENT
        PlayerDashboard player = createPlayer();
        ResourceCount chestCount = new ResourceCount(1,2,0,0,0); //Correct payment with chest
        DevelopmentCard card = createDevCard(3);
        DevCardProductionAction action = new DevCardProductionAction(card,null,chestCount);
        assertTrue(action.useAction(player));
        assertEquals(card.getProductionPower().getOutput(),player.getBufferProduction()); //Buffer equals to production output
    }
    @Test
    void useActionNotEnoughResourcesToPay() {//CANT PAY
        PlayerDashboard player = createPlayer();
        ResourceCount chestCount = new ResourceCount(0, 2, 0, 0, 0);
        DevelopmentCard card = createDevCard(3);
        DevCardProductionAction action = new DevCardProductionAction(card,null,chestCount);
        assertFalse(action.useAction(player));
    }
    @Test
    void useActionMoreResourcesThanNecessaryToPay() { //OVERPAY
        PlayerDashboard player = createPlayer();
        ResourceCount chestCount = new ResourceCount(5,5,5,5,0);
        DevelopmentCard card = createDevCard(3);
        DevCardProductionAction action = new DevCardProductionAction(card,null,chestCount);
        assertFalse(action.useAction(player));
    }
    @Test
    void useActionWrongCard() { //WRONG CARD, DIFF LEVEL
        PlayerDashboard player = createPlayer();
        ResourceCount chestCount = new ResourceCount(1,2,0,0,0);
        DevelopmentCard card = createDevCard(2);
        DevCardProductionAction action = new DevCardProductionAction(card,null,chestCount);
        assertFalse(action.useAction(player));
    }

    PlayerDashboard createPlayer(){
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

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,nickname,2);
        devCards[0].addCard(createDevCard(1));
        devCards[0].addCard(createDevCard(2));
        devCards[0].addCard(createDevCard(3));
        devCards[1].addCard(createDevCard(1));
        return player;
    }

    DevelopmentCard createDevCard(int level){
        Production prod = new Production(new ResourceCount(1,2,0,0,0),new ResourceCount(0,0,3,0,0));
        return new DevelopmentCard(5,new ResourceCount(0,0,0,0,0),prod,level, CardColour.BLUE);
    }
}