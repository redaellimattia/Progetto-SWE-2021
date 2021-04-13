package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDashboardTest {

    @Test
    void initArrayDeposit() {
        PlayerDashboard player = createPlayer();
        player.initArrayDeposit(Resource.COIN);
        assertTrue(player.getArrayDeposit().size() == 1);
    }
    @Test
    void initArrayDepositMax2() {
        PlayerDashboard player = createPlayer();
        player.initArrayDeposit(Resource.COIN);
        player.initArrayDeposit(Resource.ROCK);
        assertTrue(player.getArrayDeposit().size() == 2);
        player.initArrayDeposit(Resource.SHIELD);
        assertTrue(player.getArrayDeposit().size() == 2);
    }

    @Test
    void addDevCards() {
        PlayerDashboard player = createPlayer();
        Production prod = new Production(new ResourceCount(0,0,0,0,0),new ResourceCount(0,0,0,0,0));
        DevelopmentCard card1 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1,CardColour.GREEN);

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

        player.updatePathPosition(1);
        assertTrue(player.getPathPosition() == 1);
    }

    @Test
    void isFull() { //basic isFull test
        PlayerDashboard player = createPlayer();
        Resource res = Resource.COIN;

        player.initArrayDeposit(res);
        player.addToDeposit(res);
        player.addToDeposit(res);

        assertTrue(player.isFull(res));
    }
    @Test
    void isFull1() {
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
    void isFull2() {
        PlayerDashboard player = createPlayer();
        Resource res = Resource.COIN;
        player.addToDeposit(res);
        assertTrue(player.isFull(res));
    }

    @Test
    void addToDeposit() {
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
        ResourceCount chest = new ResourceCount(0,0,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ResourceCount input = new ResourceCount(0,0,0,0,0);
        ResourceCount output = new ResourceCount(0,0,0,0,0);
        Production basicProduction = new Production(input,output);

        ArrayList <LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        leaderCards.add(0,createLeaderCard(true));
        leaderCards.add(0,createLeaderCard(false));
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,nickname,2);
        return player;
    }

    LeaderCard createLeaderCard(boolean inGame){
        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new DepositAbility(Resource.COIN);
        LeaderCard leader = new LeaderCard(0,requirement,specialAbility);
        if(inGame)
            leader.setInGame();
        return leader;
    }
}