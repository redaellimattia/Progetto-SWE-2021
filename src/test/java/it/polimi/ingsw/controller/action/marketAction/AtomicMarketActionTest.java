package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.exceptions.action.NoAdditionalDepositException;
import it.polimi.ingsw.exceptions.action.WrongCounterTopException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AtomicMarketActionTest {

    public PlayerDashboard buildPlayerDashboard(int val1, int val2, int val3) {
        CounterTop testCounterTop1 = new CounterTop(Resource.COIN, val1);
        CounterTop testCounterTop2 = new CounterTop(Resource.ROCK, val2);
        CounterTop testCounterTop3 = new CounterTop(Resource.SHIELD, val3);
        Storage testStorage = new Storage(testCounterTop1, testCounterTop2, testCounterTop3);
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount testChest = new ResourceCount(0, 0, 0, 0, 0);
        DeckDashboard[] testDevCards = new DeckDashboard[3];
        ArrayList<LeaderCard> testLeaderCards = new ArrayList<>(0);
        PlayerDashboard player = new PlayerDashboard(testStorage, testChest, testDevCards, testLeaderCards, "Test", 0, false);
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        return player;
    }

    @Test
    void storeInFirstRow() throws CounterTopOverloadException {
        boolean ok;
        PlayerDashboard testPlayer = buildPlayerDashboard(0, 2, 0);
        AtomicMarketAction testAction = new AtomicMarketAction() {};
        ok = testAction.storeResource(testPlayer, Resource.SHIELD, 1);
        assertTrue(ok);
        assertTrue(testPlayer.getStorage().getFirstRow().getResourceType() == Resource.SHIELD);
        assertTrue(testPlayer.getStorage().getFirstRow().getContent() == 1);
    }

    @Test
    void storeInSecondRow() throws CounterTopOverloadException {
        boolean ok;
        PlayerDashboard testPlayer = buildPlayerDashboard(1, 1, 3);
        AtomicMarketAction testAction = new AtomicMarketAction() {};
        ok = testAction.storeResource(testPlayer, Resource.ROCK, 2);
        assertTrue(ok);
        assertTrue(testPlayer.getStorage().getSecondRow().getResourceType() == Resource.ROCK);
        assertTrue(testPlayer.getStorage().getSecondRow().getContent() == 2);
    }

    @Test
    void addInFullRow() {
        PlayerDashboard testPlayer = buildPlayerDashboard(1, 2, 3);
        AtomicMarketAction testAction = new AtomicMarketAction() {};
        assertThrows(CounterTopOverloadException.class, () -> testAction.storeResource(testPlayer, Resource.COIN, 1));
    }

    @Test
    void illegalStore() {
        PlayerDashboard testPlayer = buildPlayerDashboard(1, 1, 3);
        AtomicMarketAction testAction = new AtomicMarketAction() {};
        assertThrows(WrongCounterTopException.class, () -> testAction.storeResource(testPlayer, Resource.SHIELD, 2));
    }

    @Test
    void depositNotPresent() {
        PlayerDashboard testPlayer = buildPlayerDashboard(1, 1, 3);
        AtomicMarketAction testAction = new AtomicMarketAction() {};
        assertThrows(NoAdditionalDepositException.class, () -> testAction.storeResource(testPlayer, Resource.SHIELD, 4));
    }

    @Test
    void duplicateDeposit() {
        PlayerDashboard testPlayer = buildPlayerDashboard(0, 2, 2);
        AtomicMarketAction testAction = new AtomicMarketAction() {};
        assertThrows(WrongCounterTopException.class, () -> testAction.storeResource(testPlayer, Resource.SHIELD, 1));
    }

}