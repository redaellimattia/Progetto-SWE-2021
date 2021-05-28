package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.action.PaymentFailedException;
import it.polimi.ingsw.exceptions.action.WrongResourceException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BasicProductionActionTest {

    @Test
    void useActionHybridPayment() { //BASICPRODUCTION HYBRID PAYMENT
        PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
        ResourceCount storageCount = new ResourceCount(1, 0, 0, 0, 0);
        ResourceCount chestCount = new ResourceCount(0, 1, 0, 0, 0);
        ResourceCount resultStorage = new ResourceCount(0, 2, 0, 0, 0);
        ResourceCount resultChest = new ResourceCount(5, 4, 0, 0, 0);
        ResourceCount resultBuff = new ResourceCount(1, 0, 0, 0, 0);
        BasicProductionAction action = new BasicProductionAction(Resource.COIN, storageCount, chestCount);
        action.useAction(player,turnManager);
        assertEquals(resultStorage, player.getStorage().readStorage()); //Paid correctly
        assertEquals(resultChest, player.getChest()); //Paid correctly
        assertEquals(resultBuff, player.getBufferProduction()); //Buffer equals to production output
    }

    @Test
    void useActionStorageOnlyPayment(){ //STORAGE ONLY PAYMENT
         PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
         ResourceCount storageCount = new ResourceCount(1,1,0,0,0);
         ResourceCount resultStorage = new ResourceCount(0,1,0,0,0);
         ResourceCount resultBuff = new ResourceCount(1,0,0,0,0);
         BasicProductionAction action = new BasicProductionAction(Resource.COIN, storageCount, new ResourceCount(0, 0, 0, 0, 0));
         action.useAction(player,turnManager);
         assertEquals(resultStorage,player.getStorage().readStorage()); //Paid correctly
         assertEquals(resultBuff,player.getBufferProduction()); //Buffer equals to production output
    }

    @Test
    void useActionChestOnlyPayment() { //CHEST ONLY PAYMENT
        PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
        ResourceCount chestCount = new ResourceCount(1,1,0,0,0);
        ResourceCount resultChest = new ResourceCount(4,4,0,0,0);
        ResourceCount resultBuff = new ResourceCount(1,0,0,0,0);
        BasicProductionAction action = new BasicProductionAction(Resource.COIN, new ResourceCount(0, 0, 0, 0, 0), chestCount);
        action.useAction(player,turnManager);
        assertEquals(resultChest,player.getChest()); //Paid correctly
        assertEquals(resultBuff,player.getBufferProduction()); //Buffer equals to production output
    }

    @Test
    void useActionWrongInputMoreThan2() { //WRONG INPUT !=2: >2
        PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
        ResourceCount storageCount = new ResourceCount(1,0,0,0,0);
        ResourceCount chestCount = new ResourceCount(1,1,0,0,0);
        BasicProductionAction action = new BasicProductionAction(Resource.COIN, storageCount, chestCount);
        assertThrows(PaymentFailedException.class, () -> action.useAction(player,turnManager));
    }

    @Test
    void useActionWrongInputLessThan2() { //WRONG INPUT !=2: <2
        PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
        ResourceCount storageCount = new ResourceCount(1,0,0,0,0);
        ResourceCount chestCount = new ResourceCount(0,0,0,0,0);
        BasicProductionAction action = new BasicProductionAction(Resource.COIN, storageCount, chestCount);
        assertThrows(PaymentFailedException.class, () -> action.useAction(player,turnManager));
    }

    @Test
    void useActionWrongChosenResource() { //WRONG CHOSEN RESOURCE (CANT CHOOSE FAITH)
        PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
        ResourceCount storageCount = new ResourceCount(1,0,0,0,0);
        ResourceCount chestCount = new ResourceCount(0,1,0,0,0);
        BasicProductionAction action = new BasicProductionAction(Resource.FAITH, storageCount, chestCount);
        assertThrows(WrongResourceException.class, () -> action.useAction(player,turnManager));
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
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,nickname,2,false);
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        return player;
    }
    PlayerTurnManager createTurnManager(PlayerDashboard player){
        return new PlayerTurnManager(player);
    }
}