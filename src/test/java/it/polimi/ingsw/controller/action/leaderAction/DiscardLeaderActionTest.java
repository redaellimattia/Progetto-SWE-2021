package it.polimi.ingsw.controller.action.leaderAction;


import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiscardLeaderActionTest {

    @Test
    void discardLeaderAction() {
        //DISCARD FIRST CARD
        PlayerDashboard player = createPlayer();
        LeaderCard card = createLeaderCard(new ColourCount(1,0,0,0));
        DiscardLeaderAction action = new DiscardLeaderAction(card);
        assertTrue(action.useAction(player));
        assertEquals(1,player.getPathPosition());

        //DISCARD SECOND CARD
        player = createPlayer();
        card = createLeaderCard(new ColourCount(0,2,1,0));
        action = new DiscardLeaderAction(card);
        assertTrue(action.useAction(player));
        assertEquals(1,player.getPathPosition());

        //DISCARD FIRST CARD AFTER SECOND
        card = createLeaderCard(new ColourCount(1,0,0,0));
        action = new DiscardLeaderAction(card);
        assertTrue(action.useAction(player));
        assertEquals(2,player.getPathPosition());

        //CAN'T DISCARD, DOESN'T EXIST IN MODEL
        player = createPlayer();
        card = createLeaderCard(new ColourCount(5,5,5,5));
        action = new DiscardLeaderAction(card);
        assertFalse(action.useAction(player)); //CAN'T DISCARD
        assertEquals(0,player.getPathPosition());

        //CAN'T DISCARD, IT'S IN GAME

        player = createPlayer();
        card = createLeaderCard(new ColourCount(1,0,0,0));
        action = new DiscardLeaderAction(card);
        PlayLeaderAction playAction = new PlayLeaderAction(card);
        playAction.useAction(player); //Playing leader
        assertFalse(action.useAction(player)); //CAN'T DISCARD
        assertEquals(0,player.getPathPosition());
    }
    PlayerDashboard createPlayer(){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,2);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();

        leaderCards.add(0,createLeaderCard(new ColourCount(1,0,0,0)));
        leaderCards.add(0,createLeaderCard(new ColourCount(0,2,1,0)));
        return new PlayerDashboard(storage,chest,devCards,leaderCards,1,nickname,2);
    }

    LeaderCard createLeaderCard(ColourCount count){
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new ProductionAbility(Resource.COIN);
        return new LeaderCard(0,requirement,specialAbility);
    }
}