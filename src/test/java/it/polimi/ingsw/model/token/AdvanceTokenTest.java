package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdvanceTokenTest {
    public void buildToken() {
        CounterTop testCounterTop1 = new CounterTop(Resource.COIN, 1);
        CounterTop testCounterTop2 = new CounterTop(Resource.ROCK, 2);
        CounterTop testCounterTop3 = new CounterTop(Resource.SHIELD, 3);
        Storage testStorage = new Storage(testCounterTop1, testCounterTop2, testCounterTop3);
        ResourceCount testChest = new ResourceCount(0, 0, 0, 0, 0);
        DeckDashboard[] testDevCards = new DeckDashboard[3];
        ArrayList<LeaderCard> testLeaderCards = new ArrayList<LeaderCard>(0);
        Production testProduction = new Production(testChest, testChest);
        PlayerDashboard testDashboard = new PlayerDashboard(testStorage, testChest, testDevCards, testLeaderCards, testProduction, 0, "Test", 0);
        AdvanceToken testToken = new AdvanceToken(false);
    }

    public void testNoRerollToken() {

    }

}