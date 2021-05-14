package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerThread;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    @Test
    void checkFaithPathFirstVaticanReport() {
        ArrayList<PlayerDashboard> players = playerList();
        Game game = new Game(players,null,null,null);
        GameManager gameManager = new GameManager(game,new PlayerTurnManager(players.get(0)),false,new ServerThread(2));
        for(int i=0;i<8;i++)
            players.get(0).updatePathPosition();
        assertEquals(8,players.get(0).getPathPosition());
        assertThrows(NullPointerException.class, () -> gameManager.checkFaithPath(players.get(0)));
        assertEquals(3,players.get(0).getPoints());
        assertEquals(1,players.get(1).getPoints());
        for(int i=0;i<8;i++)
            players.get(1).updatePathPosition();
        assertEquals(8,players.get(1).getPathPosition());
        assertThrows(NullPointerException.class, () -> gameManager.checkFaithPath(players.get(1)));
        assertEquals(1,players.get(1).getPoints());
    }

    @Test
    void checkFaithPathSecondVaticanReport(){
        ArrayList<PlayerDashboard> players = playerList();
        Game game = new Game(players,null,null,null);
        GameManager gameManager = new GameManager(game,new PlayerTurnManager(players.get(0)),false,new ServerThread(2));
        for(int i=0;i<16;i++)
            players.get(0).updatePathPosition();
        assertEquals(16,players.get(0).getPathPosition());
        assertThrows(NullPointerException.class, () -> gameManager.checkFaithPath(players.get(0)));
        assertEquals(4,players.get(0).getPoints());
        assertEquals(1,players.get(1).getPoints());
        for(int i=0;i<16;i++)
            players.get(1).updatePathPosition();
        assertEquals(16,players.get(1).getPathPosition());
        assertThrows(NullPointerException.class, () -> gameManager.checkFaithPath(players.get(1)));
        assertEquals(1,players.get(1).getPoints());
    }

    @Test
    void checkFaithPathThirdVaticanReport(){
        ArrayList<PlayerDashboard> players = playerList();
        Game game = new Game(players,null,null,null);
        GameManager gameManager = new GameManager(game,new PlayerTurnManager(players.get(0)),false,new ServerThread(2));
        for(int i=0;i<24;i++)
            players.get(0).updatePathPosition();
        assertEquals(24,players.get(0).getPathPosition());
        assertThrows(NullPointerException.class, () -> gameManager.checkFaithPath(players.get(0)));
        assertEquals(5,players.get(0).getPoints());
        assertEquals(1,players.get(1).getPoints());
        for(int i=0;i<24;i++)
            players.get(1).updatePathPosition();
        assertEquals(24,players.get(1).getPathPosition());
        assertThrows(NullPointerException.class, () -> gameManager.checkFaithPath(players.get(1)));
        assertEquals(1,players.get(1).getPoints());
    }

    @Test
    void updateOpponentsPathPosition() {
    }

    @Test
    void calculatePoints() {
    }

    @Test
    void endGame() {
    }

    @Test
    void nextRound() {
    }

    @Test
    void wasPlaying() {
    }

    @Test
    void playerComeback() {
    }

    ArrayList<PlayerDashboard> playerList(){
        ArrayList<PlayerDashboard> players = new ArrayList<>();
        for(int i=0;i<4;i++)
            players.add(createPlayer(i+1));
        return players;
    }
    PlayerDashboard createPlayer(int position){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,2);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ServerThread playerObserver = new ServerThread(2);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        PlayerDashboard p = new PlayerDashboard(storage,chest,devCards,leaderCards,position,nickname,1, false);
        p.addObserver(playerObserver);
        p.getStorage().addObserver(p);
        return p;
    }
}