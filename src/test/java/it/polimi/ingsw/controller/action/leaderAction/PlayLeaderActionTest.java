package it.polimi.ingsw.controller.action.leaderAction;

import it.polimi.ingsw.controller.Parameter;
import it.polimi.ingsw.controller.action.leaderAction.PlayLeaderAction;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayLeaderActionTest {

    /*@Test
    void useAction() {
        //PLAY FIRST CARD
        PlayLeaderAction action = new PlayLeaderAction();
        PlayerDashboard player = createPlayer();
        LeaderCard card = createLeaderCard(false,new ColourCount(1,0,0,0));
        Parameter param = new Parameter(null,null,card,null,null,0,0,0,null,null);
        assertFalse(player.leadersInGame()); //Not in game
        assertTrue(action.useAction(player,param)); //Play the card
        assertTrue(player.leadersInGame()); //Now it's in game
        assertTrue(player.getLeaderCards().get(1).isInGame()); //CARD POS=1 IN GAME
        assertFalse(player.getLeaderCards().get(0).isInGame()); //CARD POS=0 NOT IN GAME

        //PLAY SECOND CARD
        action = new PlayLeaderAction();
        player = createPlayer();
        card = createLeaderCard(false,new ColourCount(0,2,1,0));
        param = new Parameter(null,null,card,null,null,0,0,0,null,null);
        assertFalse(player.leadersInGame()); //Not in game
        assertTrue(action.useAction(player,param)); //Play the card
        assertTrue(player.leadersInGame()); //Now it's in game
        assertFalse(player.getLeaderCards().get(1).isInGame()); //CARD POS=1 NOT IN GAME
        assertTrue(player.getLeaderCards().get(0).isInGame()); //CARD POS=0 IN GAME

        //PLAY FIRST CARD AFTER SECOND ALREADY IN GAME
        card = createLeaderCard(false,new ColourCount(1,0,0,0));
        param = new Parameter(null,null,card,null,null,0,0,0,null,null);
        assertTrue(action.useAction(player,param)); //Play the card
        assertTrue(player.getLeaderCards().get(1).isInGame()); //CARD POS=0 IN GAME

        //CARD THAT DOESN'T EXISTS
        action = new PlayLeaderAction();
        player = createPlayer();
        card = createLeaderCard(false,new ColourCount(5,5,5,5));
        param = new Parameter(null,null,card,null,null,0,0,0,null,null);
        assertFalse(player.leadersInGame()); //Not in game
        assertFalse(action.useAction(player,param)); //Play the card
        assertFalse(player.leadersInGame()); //Not in game
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
    }*/
}