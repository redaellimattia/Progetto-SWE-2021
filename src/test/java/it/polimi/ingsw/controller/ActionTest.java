package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    @Test
    void deleteRes() {
        ProductionAction action = new ProductionAction();
        PlayerDashboard player = createPlayer();
        //TOTAL COST IS 1 COIN AND 2 ROCKS
        ResourceCount chestFromView = new ResourceCount(0,2,0,0,0);
        ResourceCount storageFromView = new ResourceCount(1,0,0,0,0);
        ResourceCount resultChest = new ResourceCount(5,3,0,0,0);
        ResourceCount resultStorage = new ResourceCount(0,0,0,0,0);
        assertTrue(action.deleteRes(storageFromView,chestFromView,player));
        ResourceCount playerStorage = player.getStorage().readStorage();
        ResourceCount playerChest = player.getChest();
        assertEquals(resultChest,playerChest);
        assertEquals(resultStorage,playerStorage);

        //TEST WITH chestFromView == null, player wants to pay with chest only || TOTAL COST IS 1 COIN AND 2 ROCKS
        player = createPlayer();
        chestFromView = new ResourceCount(1,2,0,0,0);
        storageFromView = null;
        resultChest = new ResourceCount(4,3,0,0,0);
        resultStorage = new ResourceCount(1,0,0,0,0);
        assertTrue(action.deleteRes(storageFromView,chestFromView,player));
        playerStorage = player.getStorage().readStorage();
        playerChest = player.getChest();
        assertEquals(resultChest,playerChest);
        assertEquals(resultStorage,playerStorage);

    }

    @Test
    void removeFromStorage() {
    }

    @Test
    void removeFromChest() {
        //STANDARD REMOVE
        ProductionAction action = new ProductionAction();
        ResourceCount playerChest = new ResourceCount(5,5,0,0,0); //Player's chest
        ResourceCount chestFromView = new ResourceCount(1,2,0,0,0);
        ResourceCount resultChest = new ResourceCount(4,3,0,0,0);
        assertTrue(action.removeFromChest(chestFromView,playerChest));
        assertEquals(resultChest,playerChest);

        //IMPOSSIBLE REMOVE, CHESTFROMVIEW IS GREATER THAN ACTUAL CHEST
        playerChest = new ResourceCount(5,5,0,0,0); //Player's chest
        chestFromView = new ResourceCount(6,6,0,0,0);
        resultChest = new ResourceCount(4,3,0,0,0);
        assertFalse(action.removeFromChest(chestFromView,playerChest));
    }
    PlayerDashboard createPlayer(){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,0);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ResourceCount input = new ResourceCount(0,0,0,0,0);
        ResourceCount output = new ResourceCount(0,0,0,0,0);
        Production basicProduction = new Production(input,output);

        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        leaderCards.add(0,createLeaderCard(true));
        leaderCards.add(0,createLeaderCard(false));
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,nickname,2);
        return player;
    }

    LeaderCard createLeaderCard(boolean inGame){
        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new DepositAbility(Resource.COIN);
        LeaderCard leader = new LeaderCard(0,requirement,specialAbility);
        if(inGame)
            leader.setInGame();
        return leader;
    }
}