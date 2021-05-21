package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.LeaderCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameLobbyTest {


    @Test
    void checkNicknamePossiblePlayer() {
        GameLobby testLobby = new GameLobby(0, 4);
        testLobby.addPlayer("Piero");
        testLobby.addPlayer("Mario");
        assertTrue(testLobby.checkNickname("Luca"));
    }

    @Test
    void checkNicknameImpossiblePlayer() {
        GameLobby testLobby = new GameLobby(0, 4);
        testLobby.addPlayer("Piero");
        testLobby.addPlayer("Mario");
        assertFalse(testLobby.checkNickname("Piero"));
    }

    @Test
    void initGame() {
        GameLobby testLobby = new GameLobby(0, 4);
        testLobby.addPlayer("Pippo");
        testLobby.addPlayer("Pluto");
        testLobby.addPlayer("Paperino");
        testLobby.addPlayer("Giocatore");
        testLobby.initGame(false,null);
        GameManager gameManager  = testLobby.getGameManager();
        assertEquals(4, gameManager.getGame().getPlayers().size());
    }

    @Test
    void initSingleGame() {
        GameLobby testLobby = new GameLobby(0, 4);
        testLobby.addPlayer("Giocatore");
        testLobby.initGame(true,null);
        GameManager gameManager  = testLobby.getGameManager();
    }

    @Test
    void getFourLeadersTest() {
        GameLobby testLobby = new GameLobby(0, 4);
        testLobby.addPlayer("Pippo");
        testLobby.addPlayer("Pluto");
        testLobby.addPlayer("Paperino");
        testLobby.addPlayer("Giocatore");
        testLobby.initGame(false,null);
        GameManager gameManager  = testLobby.getGameManager();
        ArrayList<LeaderCard> leadersTest1 = testLobby.getFourLeaders("Pippo");
        ArrayList<LeaderCard> leadersTest2 = testLobby.getFourLeaders("Pluto");
        assertEquals(4, leadersTest1.size());
        assertEquals(4, leadersTest2.size());
        for (LeaderCard c1: leadersTest1) {
            for(LeaderCard c2: leadersTest2) {
                assertFalse(c1.equals(c2));
            }
        }
    }
}