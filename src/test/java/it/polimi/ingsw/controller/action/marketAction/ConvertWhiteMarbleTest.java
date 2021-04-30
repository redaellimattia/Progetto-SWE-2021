package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ConvertWhiteMarbleTest {

    LeaderCard buildLeaderCard(Resource type) {
        return new LeaderCard(2, new CardLevelRequirement(CardColour.YELLOW, 2), new WhiteChangeAbility(type));
    }

    public PlayerDashboard buildPlayerDashboard(int val1, int val2, int val3) {
        CounterTop testCounterTop1 = new CounterTop(Resource.COIN, val1);
        CounterTop testCounterTop2 = new CounterTop(Resource.ROCK, val2);
        CounterTop testCounterTop3 = new CounterTop(Resource.SHIELD, val3);
        Storage testStorage = new Storage(testCounterTop1, testCounterTop2, testCounterTop3);
        ResourceCount testChest = new ResourceCount(0, 0, 0, 0, 0);
        DeckDashboard[] testDevCards = new DeckDashboard[3];
        ArrayList<LeaderCard> testLeaderCards = new ArrayList<>(0);
        testLeaderCards.add(buildLeaderCard(Resource.COIN));
        testLeaderCards.add(new LeaderCard(3, new CardLevelRequirement(CardColour.YELLOW, 2), new DepositAbility(Resource.ROCK)));
        return new PlayerDashboard(testStorage, testChest, testDevCards, testLeaderCards, 0, "Test", 0, false);
    }

    @Test
    void convertAndInsert() throws CounterTopOverloadException {
        boolean ok;
        PlayerDashboard testPlayer = buildPlayerDashboard(0, 2, 3);
        LeaderCard leaderTest = testPlayer.getLeaderCards().get(0);
        ConvertWhiteMarble test = new ConvertWhiteMarble(leaderTest, 1);
        ok = test.useAction(new MarketMarble(MarbleColour.WHITE), testPlayer);
        assertTrue(ok);
        assertEquals(Resource.COIN, testPlayer.getStorage().getFirstRow().getResourceType());
        assertEquals(1, testPlayer.getStorage().getFirstRow().getContent());
    }

    @Test
    void leaderCardNotOwned() throws CounterTopOverloadException {
        boolean ok;
        PlayerDashboard testPlayer = buildPlayerDashboard(0, 2, 3);
        LeaderCard leaderTest = new LeaderCard(3, new CardLevelRequirement(CardColour.YELLOW, 2), new WhiteChangeAbility(Resource.COIN));
        ConvertWhiteMarble test = new ConvertWhiteMarble(leaderTest, 1);
        ok = test.useAction(new MarketMarble(MarbleColour.WHITE), testPlayer);
        assertFalse(ok);
        assertEquals(Resource.COIN, testPlayer.getStorage().getFirstRow().getResourceType());
        assertEquals(0, testPlayer.getStorage().getFirstRow().getContent());
    }

    @Test
    void marbleNotWhite() throws CounterTopOverloadException {
        boolean ok;
        PlayerDashboard testPlayer = buildPlayerDashboard(0, 2, 3);
        LeaderCard leaderTest = testPlayer.getLeaderCards().get(0);
        ConvertWhiteMarble test = new ConvertWhiteMarble(leaderTest, 1);
        ok = test.useAction(new MarketMarble(MarbleColour.PURPLE), testPlayer);
        assertFalse(ok);
    }

    @Test
    void leaderCardOfAnotherType() throws CounterTopOverloadException {
        boolean ok;
        PlayerDashboard testPlayer = buildPlayerDashboard(0, 2, 3);
        LeaderCard leaderTest = testPlayer.getLeaderCards().get(1);
        ConvertWhiteMarble test = new ConvertWhiteMarble(leaderTest, 1);
        ok = test.useAction(new MarketMarble(MarbleColour.WHITE), testPlayer);
        assertFalse(ok);
        assertEquals(Resource.COIN, testPlayer.getStorage().getFirstRow().getResourceType());
        assertEquals(0, testPlayer.getStorage().getFirstRow().getContent());
    }
}