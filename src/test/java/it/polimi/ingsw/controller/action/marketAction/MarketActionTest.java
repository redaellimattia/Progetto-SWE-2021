package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.CardLevelRequirement;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.WhiteChangeAbility;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MarketActionTest {

    GameManager buildGameManager() {
        DeckShop[][] emptyGrid = new DeckShop[3][4];
        PlayerDashboard player1 = buildPlayerDashboard("Test1");
        PlayerDashboard player2 = buildPlayerDashboard("Test2");
        ArrayList<PlayerDashboard> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        MarketMarble[][] testMarket = new MarketMarble[3][4];
        testMarket[0][0] = new MarketMarble(MarbleColour.RED);
        testMarket[0][1] = new MarketMarble(MarbleColour.BLUE);
        testMarket[0][2] = new MarketMarble(MarbleColour.WHITE);
        testMarket[0][3] = new MarketMarble(MarbleColour.RED);
        MarketDashboard market = new MarketDashboard(testMarket, new MarketMarble(MarbleColour.PURPLE));
        ServerLobby observer = new ServerLobby(2,1);
        market.addObserver(observer);
        Game game = new Game(players, new Shop(emptyGrid), market, new ArrayList<>());
        return new GameManager(game, new PlayerTurnManager(player1),true);
    }

    public PlayerDashboard buildPlayerDashboard(String name) {
        CounterTop testCounterTop1 = new CounterTop(Resource.COIN, 0);
        CounterTop testCounterTop2 = new CounterTop(Resource.ROCK, 2);
        CounterTop testCounterTop3 = new CounterTop(Resource.SHIELD, 3);
        Storage testStorage = new Storage(testCounterTop1, testCounterTop2, testCounterTop3);
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount testChest = new ResourceCount(0, 0, 0, 0, 0);
        DeckDashboard[] testDevCards = new DeckDashboard[3];
        ArrayList<LeaderCard> testLeaderCards = new ArrayList<>(0);
        testLeaderCards.add(new LeaderCard(0,2, new CardLevelRequirement(CardColour.YELLOW, 2), new WhiteChangeAbility(Resource.COIN)));
        for(LeaderCard c: testLeaderCards) {
            c.setInGame();
        }
        PlayerDashboard player = new PlayerDashboard(testStorage, testChest, testDevCards, testLeaderCards, name, 0, false);
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        return player;
    }

    @Test
    void buildChoices() {
        GameManager testGameManager = buildGameManager();
        PlayerDashboard player = buildPlayerDashboard("gianni");
        PlayerTurnManager turnManager = createTurnManager(player);
        ArrayList<AtomicMarketAction> testChoices = new ArrayList<>();
        testChoices.add(new DiscardResource());
        testChoices.add(new ConvertWhiteMarble(testGameManager.getGame().getPlayers().get(0).getLeaderCards().get(0), 1));
        MarketAction test = new MarketAction(0, 1, testChoices, testGameManager);
        test.useAction(testGameManager.getGame().getPlayers().get(0),turnManager);
        assertEquals(1, testGameManager.getGame().getPlayers().get(0).getStorage().getFirstRow().getContent()); // Coin converted with White Marble stored
        assertEquals(1, testGameManager.getGame().getPlayers().get(1).getPathPosition()); // Opponent path position updated
    }
    PlayerTurnManager createTurnManager(PlayerDashboard player){
        return new PlayerTurnManager(player);
    }
}