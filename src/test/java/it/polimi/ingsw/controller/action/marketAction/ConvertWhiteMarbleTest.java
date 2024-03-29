package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ConvertWhiteMarbleTest {

    LeaderCard buildLeaderCard() {
        return new LeaderCard(0,2, new CardLevelRequirement(CardColour.YELLOW, 2), new WhiteChangeAbility(Resource.COIN));
    }

    public PlayerDashboard buildPlayerDashboard(int val1, int val2, int val3) {
        CounterTop testCounterTop1 = new CounterTop(Resource.COIN, val1);
        CounterTop testCounterTop2 = new CounterTop(Resource.ROCK, val2);
        CounterTop testCounterTop3 = new CounterTop(Resource.SHIELD, val3);
        Storage testStorage = new Storage(testCounterTop1, testCounterTop2, testCounterTop3);
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount testChest = new ResourceCount(0, 0, 0, 0, 0);
        DeckDashboard[] testDevCards = new DeckDashboard[3];
        ArrayList<LeaderCard> testLeaderCards = new ArrayList<>(0);
        testLeaderCards.add(buildLeaderCard());
        testLeaderCards.add(new LeaderCard(0,3, new CardLevelRequirement(CardColour.YELLOW, 2), new DepositAbility(Resource.ROCK)));
        for(LeaderCard c: testLeaderCards) {
            c.setInGame();
        }
        PlayerDashboard player = new PlayerDashboard(testStorage, testChest, testDevCards, testLeaderCards, "Test", 0, false);
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        return player;
    }

    @Test
    void convertAndInsert() throws CounterTopOverloadException {
        boolean ok;
        PlayerDashboard testPlayer = buildPlayerDashboard(0, 2, 3);
        LeaderCard leaderTest = testPlayer.getLeaderCards().get(0);
        ConvertWhiteMarble test = new ConvertWhiteMarble(leaderTest, 1);
        ok = test.useAction(new MarketMarble(MarbleColour.WHITE), testPlayer, null);
        assertTrue(ok);
        assertEquals(Resource.COIN, testPlayer.getStorage().getFirstRow().getResourceType());
        assertEquals(1, testPlayer.getStorage().getFirstRow().getContent());
    }

    @Test
    void leaderCardNotOwned() throws CounterTopOverloadException {
        PlayerDashboard testPlayer = buildPlayerDashboard(0, 2, 3);
        LeaderCard leaderTest = new LeaderCard(0,3, new CardLevelRequirement(CardColour.YELLOW, 2), new WhiteChangeAbility(Resource.COIN));
        ConvertWhiteMarble test = new ConvertWhiteMarble(leaderTest, 1);
        assertFalse(test.checkAction(new MarketMarble(MarbleColour.WHITE), testPlayer, testPlayer.getStorage(), testPlayer.getArrayDeposit()));
        assertEquals(Resource.COIN, testPlayer.getStorage().getFirstRow().getResourceType());
        assertEquals(0, testPlayer.getStorage().getFirstRow().getContent());
    }

    @Test
    void marbleNotWhite() throws CounterTopOverloadException {
        PlayerDashboard testPlayer = buildPlayerDashboard(0, 2, 3);
        LeaderCard leaderTest = testPlayer.getLeaderCards().get(0);
        ConvertWhiteMarble test = new ConvertWhiteMarble(leaderTest, 1);
        assertFalse(test.checkAction(new MarketMarble(MarbleColour.PURPLE), testPlayer, testPlayer.getStorage(), testPlayer.getArrayDeposit()));
    }

    @Test
    void leaderCardOfAnotherType() throws CounterTopOverloadException {
        PlayerDashboard testPlayer = buildPlayerDashboard(0, 2, 3);
        LeaderCard leaderTest = testPlayer.getLeaderCards().get(1);
        ConvertWhiteMarble test = new ConvertWhiteMarble(leaderTest, 1);
        assertFalse(test.checkAction(new MarketMarble(MarbleColour.WHITE), testPlayer, testPlayer.getStorage(), testPlayer.getArrayDeposit()));
        assertEquals(Resource.COIN, testPlayer.getStorage().getFirstRow().getResourceType());
        assertEquals(0, testPlayer.getStorage().getFirstRow().getContent());
    }

    @Test
    void nullLeaderCard() {
        PlayerDashboard testPlayer = buildPlayerDashboard(0, 2, 3);
        ConvertWhiteMarble test = new ConvertWhiteMarble(null, 1);
        assertFalse(test.checkAction(new MarketMarble(MarbleColour.WHITE), testPlayer, testPlayer.getStorage(), testPlayer.getArrayDeposit()));
    }
}