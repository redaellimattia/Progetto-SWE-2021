package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
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
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount testChest = new ResourceCount(0, 0, 0, 0, 0);
        DeckDashboard[] testDevCards = new DeckDashboard[3];
        ArrayList<LeaderCard> testLeaderCards = new ArrayList<>(0);
        PlayerDashboard player = new PlayerDashboard(testStorage, testChest, testDevCards, testLeaderCards, "Test", 0,false);
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        return player;
    }

    @Test
    public void testNoReRollToken() {
        PlayerDashboard testDashboard = buildPlayerDashboard();
        int oldPos = testDashboard.getPathPosition();
        PlayerDashboard player1 = buildPlayerDashboard();
        PlayerDashboard lorenzo = buildPlayerDashboard();
        ArrayList<PlayerDashboard> players = new ArrayList<>();
        players.add(player1);
        players.add(lorenzo);
        Game game = new Game(players, new Shop(new DeckShop[3][4]), new MarketDashboard(new MarketMarble[3][4], new MarketMarble(MarbleColour.PURPLE)), new ArrayList<>());
        AdvanceToken testToken = new AdvanceToken(false);
        testToken.useToken(testDashboard, game,new ServerLobby(1,1));
        assertEquals(oldPos + 2, testDashboard.getPathPosition());
    }

    @Test
    public void testReRollToken() {
        PlayerDashboard testDashboard = buildPlayerDashboard();
        int oldPos = testDashboard.getPathPosition();
        PlayerDashboard player1 = buildPlayerDashboard();
        PlayerDashboard lorenzo = buildPlayerDashboard();
        ArrayList<PlayerDashboard> players = new ArrayList<>();
        players.add(player1);
        players.add(lorenzo);
        Game game = new Game(players, new Shop(new DeckShop[3][4]), new MarketDashboard(new MarketMarble[3][4], new MarketMarble(MarbleColour.PURPLE)), new ArrayList<>());
        AdvanceToken testToken = new AdvanceToken(true);
        testToken.useToken(testDashboard, game,new ServerLobby(1,1));
        assertEquals(oldPos + 1, testDashboard.getPathPosition());
    }
}