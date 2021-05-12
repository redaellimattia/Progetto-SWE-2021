package it.polimi.ingsw.controller;

import org.junit.jupiter.api.Test;

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
}