package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDashboardTest {

    @Test
    void initArrayDeposit() {
        PlayerDashboard player = createPlayer();
        player.initArrayDeposit(Resource.COIN);
        assertEquals(1, player.getArrayDeposit().size());
    }
    @Test
    void initArrayDepositMax2() {
        PlayerDashboard player = createPlayer();
        player.initArrayDeposit(Resource.COIN);
        player.initArrayDeposit(Resource.ROCK);
        assertEquals(2, player.getArrayDeposit().size());
        player.initArrayDeposit(Resource.SHIELD);
        assertEquals(2, player.getArrayDeposit().size());
    }

    @Test
    void addDevCards() {
        PlayerDashboard player = createPlayer();
        Production prod = new Production(new ResourceCount(0,0,0,0,0),new ResourceCount(0,0,0,0,0));
        DevelopmentCard card1 = new DevelopmentCard(0,1,new ResourceCount(1,0,0,0,0),prod,1,CardColour.GREEN);

        player.getDevCards()[0].addCard(card1);

        assertEquals(player.getDevCards()[0].getFirst(),card1);
    }

    @Test
    void leadersInGame() {
        PlayerDashboard player = createPlayer();

        assertTrue(player.leadersInGame());
    }

    @Test
    void discardLeader() {
        PlayerDashboard player = createPlayer();
        player.discardLeader(1);
        assertTrue(player.getLeaderCards().size() == 1 && !player.getLeaderCards().get(0).isInGame());
    }

    @Test
    void updatePathPosition() {
        PlayerDashboard player = createPlayer();

        player.updatePathPosition();
        assertEquals(1, player.getPathPosition());
    }

    @Test
    void isFull() throws CounterTopOverloadException { //basic isFull test
        PlayerDashboard player = createPlayer();
        Resource res = Resource.COIN;

        player.initArrayDeposit(res);
        player.addToDeposit(res);
        player.addToDeposit(res);

        assertTrue(player.isFull(res));
    }
    @Test
    void isFull1() throws CounterTopOverloadException {
        PlayerDashboard player = createPlayer();
        Resource res = Resource.COIN;
        Resource res1 = Resource.ROCK;
        Resource res2 = Resource.SERVANT;
        player.initArrayDeposit(res);
        player.addToDeposit(res);
        player.addToDeposit(res);

        player.initArrayDeposit(res1);
        player.addToDeposit(res1);

        assertTrue(player.isFull(res));
        assertFalse(player.isFull(res1));
        assertTrue(player.isFull(res2));
    }
    @Test
    void isFull2() throws CounterTopOverloadException {
        PlayerDashboard player = createPlayer();
        Resource res = Resource.COIN;
        assertThrows(CounterTopOverloadException.class, ()-> player.addToDeposit(res));
        assertTrue(player.isFull(res));
    }

    @Test
    void addToDeposit() throws CounterTopOverloadException {
        PlayerDashboard player = createPlayer();
        Resource res = Resource.COIN;

        player.initArrayDeposit(res);
        player.addToDeposit(res);
        ResourceCount check = new ResourceCount(1,0,0,0,0);
        assertEquals(player.getAbilityDepositResources(),check);
    }


    PlayerDashboard createPlayer(){
        String nickname = "Prova";
        Resource coins =  Resource.COIN;
        CounterTop firstRow = new CounterTop(coins,0);
        CounterTop secondRow = new CounterTop(coins,0);
        CounterTop thirdRow = new CounterTop(coins,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount chest = new ResourceCount(0,0,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];

        ArrayList <LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(0,createLeaderCard(true));
        leaderCards.add(0,createLeaderCard(false));
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,nickname,2, false);
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        return player;
    }

    LeaderCard createLeaderCard(boolean inGame){
        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new DepositAbility(Resource.COIN);
        LeaderCard leader = new LeaderCard(0,0,requirement,specialAbility);
        if(inGame)
            leader.setInGame();
        return leader;
    }
}