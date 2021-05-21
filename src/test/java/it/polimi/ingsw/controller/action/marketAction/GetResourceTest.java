package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.exceptions.action.WrongMarbleException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GetResourceTest {

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
    void addResourceToFirstRow() throws CounterTopOverloadException {
        boolean ok;
        GetResource test = new GetResource(1);
        ok = test.useAction(new MarketMarble(MarbleColour.PURPLE), buildPlayerDashboard(0, 2, 2));
        assertTrue(ok);
    }

    @Test
    void addResourceToSecondRow() throws CounterTopOverloadException {
        boolean ok;
        GetResource test = new GetResource(2);
        ok = test.useAction(new MarketMarble(MarbleColour.GREY), buildPlayerDashboard(1, 1, 3));
        assertTrue(ok);
    }

    @Test
    void IllegalGet() {
        GetResource test1 = new GetResource(2);
        assertThrows(WrongMarbleException.class, () -> test1.useAction(new MarketMarble(MarbleColour.RED), buildPlayerDashboard(1, 0, 3)));
    }
}