package it.polimi.ingsw.model.token;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerThread;
import it.polimi.ingsw.network.server.SocketConnection;
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
        ServerThread playerObserver = new ServerThread(2);
        ResourceCount testChest = new ResourceCount(0, 0, 0, 0, 0);
        DeckDashboard[] testDevCards = new DeckDashboard[3];
        ArrayList<LeaderCard> testLeaderCards = new ArrayList<>(0);
        PlayerDashboard player = new PlayerDashboard(testStorage, testChest, testDevCards, testLeaderCards, 0, "Test", 0,false);
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        return player;
    }

    public GameManager buildGameManager() {
        PlayerDashboard player1 = buildPlayerDashboard();
        PlayerDashboard lorenzo = buildPlayerDashboard();
        ArrayList<PlayerDashboard> players = new ArrayList<PlayerDashboard>();
        players.add(player1);
        players.add(lorenzo);
        ServerThread observer = new ServerThread(2);
        Game game = new Game(players, new Shop(new Deck[3][4]), new MarketDashboard(new MarketMarble[3][4], new MarketMarble(MarbleColour.PURPLE)), new ArrayList<SoloToken>());
        GameManager gameManager = new GameManager(game, new PlayerTurnManager(player1),true,observer);
        return gameManager;
    }

    @Test
    public void testNoReRollToken() {
        PlayerDashboard testDashboard = buildPlayerDashboard();
        int oldPos = testDashboard.getPathPosition();
        PlayerDashboard player1 = buildPlayerDashboard();
        PlayerDashboard lorenzo = buildPlayerDashboard();
        ArrayList<PlayerDashboard> players = new ArrayList<PlayerDashboard>();
        players.add(player1);
        players.add(lorenzo);
        Game game = new Game(players, new Shop(new Deck[3][4]), new MarketDashboard(new MarketMarble[3][4], new MarketMarble(MarbleColour.PURPLE)), new ArrayList<SoloToken>());
        AdvanceToken testToken = new AdvanceToken(false, buildGameManager());
        testToken.useToken(testDashboard, game);
        assertEquals(oldPos + 2, testDashboard.getPathPosition());
    }

    @Test
    public void testReRollToken() {
        PlayerDashboard testDashboard = buildPlayerDashboard();
        int oldPos = testDashboard.getPathPosition();
        PlayerDashboard player1 = buildPlayerDashboard();
        PlayerDashboard lorenzo = buildPlayerDashboard();
        ArrayList<PlayerDashboard> players = new ArrayList<PlayerDashboard>();
        players.add(player1);
        players.add(lorenzo);
        Game game = new Game(players, new Shop(new Deck[3][4]), new MarketDashboard(new MarketMarble[3][4], new MarketMarble(MarbleColour.PURPLE)), new ArrayList<SoloToken>());
        AdvanceToken testToken = new AdvanceToken(true, buildGameManager());
        testToken.useToken(testDashboard, game);
        assertEquals(oldPos + 1, testDashboard.getPathPosition());
    }
}