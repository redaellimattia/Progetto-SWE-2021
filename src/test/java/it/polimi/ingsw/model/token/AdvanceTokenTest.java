package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//TO-DO: Use Game class to build PlayerDashboard; test ReRoll effect in Game class
class AdvanceTokenTest {
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

    @Test
    public void testNoReRollToken() {
        PlayerDashboard testDashboard = buildPlayerDashboard();
        int oldPos = testDashboard.getPathPosition();
        AdvanceToken testToken = new AdvanceToken(false);
        testToken.useToken(testDashboard);
        assertEquals(oldPos + 2, testDashboard.getPathPosition());
    }

    @Test
    public void testReRollToken() {
        PlayerDashboard testDashboard = buildPlayerDashboard();
        int oldPos = testDashboard.getPathPosition();
        AdvanceToken testToken = new AdvanceToken(true);
        testToken.useToken(testDashboard);
        assertEquals(oldPos + 1, testDashboard.getPathPosition());
    }
}