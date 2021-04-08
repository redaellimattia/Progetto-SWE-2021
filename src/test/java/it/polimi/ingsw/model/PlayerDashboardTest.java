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
        String gabri = "gabri";
        Resource coins =  Resource.COIN;
        CounterTop firstRow = new CounterTop(coins,0);
        CounterTop secondRow = new CounterTop(coins,0);
        CounterTop thirdRow = new CounterTop(coins,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ResourceCount chest = new ResourceCount(0,0,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ArrayList <LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        ResourceCount input = new ResourceCount(0,0,0,0,0);
        ResourceCount output = new ResourceCount(0,0,0,0,0);
        Production basicProduction = new Production(input,output);
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,gabri,2);

        player.initArrayDeposit(coins);
        assertTrue(player.getArrayDeposit().size() == 1);
    }

    @Test
    void addDevCards() {
        String gabri = "gabri";
        Resource coins =  Resource.COIN;
        CounterTop firstRow = new CounterTop(coins,0);
        CounterTop secondRow = new CounterTop(coins,0);
        CounterTop thirdRow = new CounterTop(coins,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ResourceCount chest = new ResourceCount(0,0,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ArrayList <LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        ResourceCount input = new ResourceCount(0,0,0,0,0);
        ResourceCount output = new ResourceCount(0,0,0,0,0);
        Production basicProduction = new Production(input,output);
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,gabri,2);
        CardColour green = CardColour.GREEN;
        DevelopmentCard card1 = new DevelopmentCard(1,chest,basicProduction,1,green);

        player.getDevCards()[0] = new DeckDashboard(new ArrayList<DevelopmentCard>());
        player.getDevCards()[0].addCard(card1);

        assertEquals(player.getDevCards()[0].getFirst(),card1);
    }

    @Test
    void leadersInGame() {
        String gabri = "gabri";
        Resource coins =  Resource.COIN;
        CounterTop firstRow = new CounterTop(coins,0);
        CounterTop secondRow = new CounterTop(coins,0);
        CounterTop thirdRow = new CounterTop(coins,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ResourceCount chest = new ResourceCount(0,0,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ArrayList <LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        ResourceCount input = new ResourceCount(0,0,0,0,0);
        ResourceCount output = new ResourceCount(0,0,0,0,0);
        Production basicProduction = new Production(input,output);
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,gabri,2);

        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new DepositAbility(coins);
        LeaderCard leader1 = new LeaderCard(0,requirement,specialAbility);
        leader1.setInGame();

        leaderCards.add(leader1);
        assertTrue(player.leadersInGame());
    }

    @Test
    void discardLeader() {
        String gabri = "gabri";
        Resource coins =  Resource.COIN;
        CounterTop firstRow = new CounterTop(coins,0);
        CounterTop secondRow = new CounterTop(coins,0);
        CounterTop thirdRow = new CounterTop(coins,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ResourceCount chest = new ResourceCount(0,0,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ArrayList <LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        ResourceCount input = new ResourceCount(0,0,0,0,0);
        ResourceCount output = new ResourceCount(0,0,0,0,0);
        Production basicProduction = new Production(input,output);
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,gabri,2);

        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new DepositAbility(coins);
        LeaderCard leader1 = new LeaderCard(0,requirement,specialAbility);
        LeaderCard leader2 = new LeaderCard(1,requirement,specialAbility);

        leaderCards.add(leader1);
        leaderCards.add(leader2);
        //assertTrue(leaderCards.size() == 2);
        player.discardLeader(1);
        assertTrue(leaderCards.size() == 1 && leaderCards.get(0) == leader1);
    }

    @Test
    void updatePathPosition() {
        String gabri = "gabri";
        Resource coins =  Resource.COIN;
        CounterTop firstRow = new CounterTop(coins,0);
        CounterTop secondRow = new CounterTop(coins,0);
        CounterTop thirdRow = new CounterTop(coins,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ResourceCount chest = new ResourceCount(0,0,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ArrayList <LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        ResourceCount input = new ResourceCount(0,0,0,0,0);
        ResourceCount output = new ResourceCount(0,0,0,0,0);
        Production basicProduction = new Production(input,output);
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,gabri,2);

        player.updatePathPosition(1);
        assertTrue(player.getPathPosition() == 1);
    }

    @Test
    void isFull() {
        String gabri = "gabri";
        Resource coins =  Resource.COIN;
        CounterTop firstRow = new CounterTop(coins,0);
        CounterTop secondRow = new CounterTop(coins,0);
        CounterTop thirdRow = new CounterTop(coins,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ResourceCount chest = new ResourceCount(0,0,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ArrayList <LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        ResourceCount input = new ResourceCount(0,0,0,0,0);
        ResourceCount output = new ResourceCount(0,0,0,0,0);
        Production basicProduction = new Production(input,output);
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,gabri,2);


        player.initArrayDeposit(coins);
        player.addToDeposit(coins);
        player.addToDeposit(coins);

        assertTrue(player.isFull(coins));
    }

    @Test
    void addToDeposit() {
            String gabri = "gabri";
            Resource coins =  Resource.COIN;
            CounterTop firstRow = new CounterTop(coins,0);
            CounterTop secondRow = new CounterTop(coins,0);
            CounterTop thirdRow = new CounterTop(coins,0);
            Storage storage = new Storage(firstRow,secondRow,thirdRow);
            ResourceCount chest = new ResourceCount(0,0,0,0,0);
            DeckDashboard[] devCards = new DeckDashboard[3];
            ArrayList <LeaderCard> leaderCards = new ArrayList<LeaderCard>();
            ResourceCount input = new ResourceCount(0,0,0,0,0);
            ResourceCount output = new ResourceCount(0,0,0,0,0);
            Production basicProduction = new Production(input,output);
            PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,gabri,2);

            player.initArrayDeposit(coins);
            player.addToDeposit(coins);
            ResourceCount check = new ResourceCount(1,0,0,0,0);
            assertEquals(player.getAbilityDepositResources(),check);
    }
}