package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.exceptions.action.CardNotExistsException;
import it.polimi.ingsw.exceptions.action.PaymentFailedException;
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
        action.useAction(player);
        assertEquals(card.getProductionPower().getOutput(), player.getBufferProduction()); //Buffer equals to production output
    }

    @Test
    void useActionStorageOnlyPayment(){ //STORAGE ONLY PAYMENT
        PlayerDashboard player = createPlayer();
        ResourceCount storageCount = new ResourceCount(1,2,0,0,0); //Correct payment with storage
        DevelopmentCard card = createDevCard(3);
        DevCardProductionAction action = new DevCardProductionAction(card,storageCount,null);
        action.useAction(player);
        assertEquals(card.getProductionPower().getOutput(),player.getBufferProduction()); //Buffer equals to production output
    }

    @Test
    void useActionChestOnlyPayment() { //CHEST ONLY PAYMENT
        PlayerDashboard player = createPlayer();
        ResourceCount chestCount = new ResourceCount(1,2,0,0,0); //Correct payment with chest
        DevelopmentCard card = createDevCard(3);
        DevCardProductionAction action = new DevCardProductionAction(card,null,chestCount);
        action.useAction(player);
        assertEquals(card.getProductionPower().getOutput(),player.getBufferProduction()); //Buffer equals to production output
    }

    @Test
    void useActionNotEnoughResourcesToPay() {//CANT PAY
        PlayerDashboard player = createPlayer();
        ResourceCount chestCount = new ResourceCount(0, 2, 0, 0, 0);
        DevelopmentCard card = createDevCard(3);
        DevCardProductionAction action = new DevCardProductionAction(card,null,chestCount);
        assertThrows(PaymentFailedException.class, () -> action.useAction(player));
    }

    @Test
    void useActionMoreResourcesThanNecessaryToPay() { //OVERPAY
        PlayerDashboard player = createPlayer();
        ResourceCount chestCount = new ResourceCount(5,5,5,5,0);
        DevelopmentCard card = createDevCard(3);
        DevCardProductionAction action = new DevCardProductionAction(card,null,chestCount);
        assertThrows(PaymentFailedException.class, () -> action.useAction(player));
    }

    @Test
    void useActionWrongCard() { //WRONG CARD, DIFF LEVEL
        PlayerDashboard player = createPlayer();
        ResourceCount chestCount = new ResourceCount(1,2,0,0,0);
        DevelopmentCard card = createDevCard(2);
        DevCardProductionAction action = new DevCardProductionAction(card,null,chestCount);
        assertThrows(CardNotExistsException.class, () -> action.useAction(player));
    }

    @Test
    void endActionNoFaith() { //DEVCARDPRODUCTION THAT ADDS 3 SERVANTS
        PlayerDashboard player = createPlayer();
        ResourceCount chestCount = new ResourceCount(1,2,0,0,0);
        ResourceCount resultChest = new ResourceCount(4,3,0,0,0);
        ResourceCount resultBuff = new ResourceCount(0,0,3,0,0);
        DevelopmentCard card = createDevCard(3);
        DevCardProductionAction action = new DevCardProductionAction(card, null, chestCount);
        action.useAction(player);
        assertEquals(resultChest,player.getChest()); //Paid correctly
        assertEquals(resultBuff,player.getBufferProduction()); //Buffer equals to production output

        //END ACTION THEN CHECK IF PATHPOSITION INCREASED, AND CHEST INCREASED
        action.endAction(player);
        assertEquals(new ResourceCount(0,0,0,0,0),player.getBufferProduction()); //Buffer is empty
        ResourceCount newChest = new ResourceCount(4,3,3,0,0);
        assertEquals(0,player.getPathPosition());
        assertEquals(newChest,player.getChest());
    }

    @Test
    void endActionFaith() { //DEVCARDPRODUCTION THAT ADDS 2 SERVANTS AND 1 FAITH
        PlayerDashboard player = createPlayerDevCardFaith();
        ResourceCount chestCount = new ResourceCount(1,2,0,0,0);
        ResourceCount resultChest = new ResourceCount(4,3,0,0,0);
        ResourceCount resultBuff = new ResourceCount(0,0,2,0,1);
        DevelopmentCard card = createDevCardWithFaith();
        DevCardProductionAction action = new DevCardProductionAction(card, null, chestCount);
        action.useAction(player);
        assertEquals(resultChest,player.getChest()); //Paid correctly
        assertEquals(resultBuff,player.getBufferProduction()); //Buffer equals to production output

        //END ACTION THEN CHECK IF PATHPOSITION INCREASED, AND CHEST INCREASED
        action.endAction(player);
        assertEquals(new ResourceCount(0,0,0,0,0),player.getBufferProduction()); //Buffer is empty
        ResourceCount newChest = new ResourceCount(4,3,2,0,0);
        assertEquals(1,player.getPathPosition());
        assertEquals(newChest,player.getChest());
    }

    PlayerDashboard createPlayer(){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,2);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,1,nickname,2);
        devCards[0].addCard(createDevCard(1));
        devCards[0].addCard(createDevCard(2));
        devCards[0].addCard(createDevCard(3));
        devCards[1].addCard(createDevCard(1));
        return player;
    }

    PlayerDashboard createPlayerDevCardFaith(){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,2);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,1,nickname,2);
        devCards[0].addCard(createDevCard(1));
        devCards[0].addCard(createDevCard(2));
        devCards[0].addCard(createDevCardWithFaith());
        devCards[1].addCard(createDevCard(1));
        return player;
    }

    DevelopmentCard createDevCard(int level){
        Production prod = new Production(new ResourceCount(1,2,0,0,0),new ResourceCount(0,0,3,0,0));
        return new DevelopmentCard(5,new ResourceCount(0,0,0,0,0),prod,level, CardColour.BLUE);
    }

    DevelopmentCard createDevCardWithFaith(){
        Production prod = new Production(new ResourceCount(1,2,0,0,0),new ResourceCount(0,0,2,0,1));
        return new DevelopmentCard(5,new ResourceCount(0,0,0,0,0),prod,3, CardColour.BLUE);
    }
}