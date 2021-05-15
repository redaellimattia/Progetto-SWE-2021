package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.LeaderCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameLobbyTest {

    @Test
    void addPlayer() {
    }

    @Test
    void removePlayer() {
    }

    @Test
    void checkNickname() {
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
    }
}