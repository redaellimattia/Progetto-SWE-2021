package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.Parameter;
import it.polimi.ingsw.controller.action.DiscardLeaderAction;
import it.polimi.ingsw.controller.action.PlayLeaderAction;
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
        DiscardLeaderAction action = new DiscardLeaderAction();
        PlayerDashboard player = createPlayer();
        LeaderCard card = createLeaderCard(false,new ColourCount(1,0,0,0));
        Parameter param = new Parameter(null,null,card,null,null,0,0,0,null,null);
        assertTrue(action.useAction(player,param));
        assertEquals(1,player.getPathPosition());

        //DISCARD SECOND CARD
        action = new DiscardLeaderAction();
        player = createPlayer();
        card = createLeaderCard(false,new ColourCount(0,2,1,0));
        param = new Parameter(null,null,card,null,null,0,0,0,null,null);
        assertTrue(action.useAction(player,param));
        assertEquals(1,player.getPathPosition());

        //DISCARD FIRST CARD AFTER SECOND
        card = createLeaderCard(false,new ColourCount(1,0,0,0));
        param = new Parameter(null,null,card,null,null,0,0,0,null,null);
        assertTrue(action.useAction(player,param));
        assertEquals(2,player.getPathPosition());

        //CAN'T DISCARD, DOESN'T EXIST IN MODEL
        action = new DiscardLeaderAction();
        player = createPlayer();
        card = createLeaderCard(false,new ColourCount(5,5,5,5));
        param = new Parameter(null,null,card,null,null,0,0,0,null,null);
        assertFalse(action.useAction(player,param)); //CAN'T DISCARD
        assertEquals(0,player.getPathPosition());

        //CAN'T DISCARD, IT'S IN GAME
        action = new DiscardLeaderAction();
        PlayLeaderAction playAction = new PlayLeaderAction();
        player = createPlayer();
        card = createLeaderCard(false,new ColourCount(1,0,0,0));
        param = new Parameter(null,null,card,null,null,0,0,0,null,null);
        playAction.useAction(player,param); //Playing leader
        assertFalse(action.useAction(player,param)); //CAN'T DISCARD
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
        ResourceCount input = new ResourceCount(0,0,0,0,0);
        ResourceCount output = new ResourceCount(0,0,0,0,0);
        Production basicProduction = new Production(input,output);

        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        leaderCards.add(0,createLeaderCard(false,new ColourCount(1,0,0,0)));
        leaderCards.add(0,createLeaderCard(false,new ColourCount(0,2,1,0)));
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,nickname,2);
        return player;
    }

    LeaderCard createLeaderCard(boolean inGame,ColourCount count){
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new ProductionAbility(Resource.COIN);
        LeaderCard leader = new LeaderCard(0,requirement,specialAbility);
        if(inGame)
            leader.setInGame();
        return leader;
    }
}