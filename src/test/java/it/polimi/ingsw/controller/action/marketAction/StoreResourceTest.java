package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StoreResourceTest {

    public PlayerDashboard buildPlayerDashboard(int val1, int val2, int val3) {
        CounterTop testCounterTop1 = new CounterTop(Resource.COIN, val1);
        CounterTop testCounterTop2 = new CounterTop(Resource.ROCK, val2);
        CounterTop testCounterTop3 = new CounterTop(Resource.SHIELD, val3);
        Storage testStorage = new Storage(testCounterTop1, testCounterTop2, testCounterTop3);
        ResourceCount testChest = new ResourceCount(0, 0, 0, 0, 0);
        DeckDashboard[] testDevCards = new DeckDashboard[3];
        ArrayList<LeaderCard> testLeaderCards = new ArrayList<>(0);
        return new PlayerDashboard(testStorage, testChest, testDevCards, testLeaderCards, 0, "Test", 0, false);
    }

    @Test
    void storeInFirstRow() {
        boolean ok;
        PlayerDashboard testPlayer = buildPlayerDashboard(0, 2, 3);
        ok = StoreResource.storeResource(testPlayer, Resource.SHIELD, 1);
        assertTrue(ok);
        assertTrue(testPlayer.getStorage().getFirstRow().getResourceType() == Resource.SHIELD);
        assertTrue(testPlayer.getStorage().getFirstRow().getContent() == 1);
    }

    @Test
    void storeInSecondRow() {
        boolean ok;
        PlayerDashboard testPlayer = buildPlayerDashboard(1, 1, 3);
        ok = StoreResource.storeResource(testPlayer, Resource.ROCK, 2);
        assertTrue(ok);
        assertTrue(testPlayer.getStorage().getSecondRow().getResourceType() == Resource.ROCK);
        assertTrue(testPlayer.getStorage().getSecondRow().getContent() == 2);
    }

    @Test
    void addInFullRow() {
        boolean ok;
        PlayerDashboard testPlayer = buildPlayerDashboard(1, 2, 3);
        ok = StoreResource.storeResource(testPlayer, Resource.SHIELD, 1);
        assertFalse(ok);
    }

    @Test
    void illegalStore() {
        boolean ok;
        PlayerDashboard testPlayer = buildPlayerDashboard(1, 1, 3);
        ok = StoreResource.storeResource(testPlayer, Resource.SHIELD, 2);
        assertFalse(ok);
    }

    @Test
    void depositNotPresent() {
        boolean ok;
        PlayerDashboard testPlayer = buildPlayerDashboard(1, 1, 3);
        ok = StoreResource.storeResource(testPlayer, Resource.SHIELD, 4);
        assertFalse(ok);
    }
}