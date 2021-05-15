package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.token.SoloToken;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiscardResourceTest {

    public PlayerDashboard buildPlayerDashboard(String name) {
        CounterTop testCounterTop1 = new CounterTop(Resource.COIN, 1);
        CounterTop testCounterTop2 = new CounterTop(Resource.ROCK, 2);
        CounterTop testCounterTop3 = new CounterTop(Resource.SHIELD, 3);
        Storage testStorage = new Storage(testCounterTop1, testCounterTop2, testCounterTop3);
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount testChest = new ResourceCount(0, 0, 0, 0, 0);
        DeckDashboard[] testDevCards = new DeckDashboard[3];
        ArrayList<LeaderCard> testLeaderCards = new ArrayList<>(0);
        PlayerDashboard player =  new PlayerDashboard(testStorage, testChest, testDevCards, testLeaderCards, 0, name, 0, false);
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        return player;
    }

    public GameManager buildGameManager() {
        DeckShop[][] emptyGrid = new DeckShop[3][4];
        PlayerDashboard player1 = buildPlayerDashboard("Test1");
        PlayerDashboard player2 = buildPlayerDashboard("Test2");
        ArrayList<PlayerDashboard> players = new ArrayList<PlayerDashboard>();
        players.add(player1);
        players.add(player2);
        ServerLobby observer = new ServerLobby(2,1);
        Game game = new Game(players, new Shop(emptyGrid), new MarketDashboard(new MarketMarble[3][4], new MarketMarble(MarbleColour.PURPLE)), new ArrayList<SoloToken>());
        GameManager gameManager = new GameManager(game, new PlayerTurnManager(player1),true,observer);
        return gameManager;
    }

    @Test
    void discardByPlayer1() {
        boolean ok;
        GameManager testGameManager = buildGameManager();
        PlayerDashboard testPlayer = testGameManager.getGame().getPlayers().get(0);
        DiscardResource test = new DiscardResource(testGameManager);
        test.useAction(new MarketMarble(MarbleColour.YELLOW), testPlayer);
        assertEquals(1, testGameManager.getGame().getPlayers().get(1).getPathPosition());
        assertEquals(0, testGameManager.getGame().getPlayers().get(0).getPathPosition());
    }
}