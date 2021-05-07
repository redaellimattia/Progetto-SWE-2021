package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.action.leaderAction.PlayLeaderAction;
import it.polimi.ingsw.exceptions.action.WrongResourcesMovedException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveFromLeaderToDepositTest {
    @Test
    void moveFromLeaderDeposit(){
        PlayerDashboard player = createPlayer();
        MoveFromLeaderToDeposit organize = new MoveFromLeaderToDeposit(0,3,1);
        PlayLeaderAction action = new PlayLeaderAction(player.getLeaderCards().get(1));
        action.useAction(player);

        player.getArrayDeposit().get(0).addContent(1);
        organize.useAction(player);
        assertEquals(1, player.getStorage().getThirdRow().getContent());
    }

    @Test //DOESN'T ADD RESOURCES TO ALREADY FULL COUNTERTOPS
    void moveFromLeaderDeposit1(){
        PlayerDashboard player = createPlayerThirdFull();
        MoveFromLeaderToDeposit organize = new MoveFromLeaderToDeposit(0,3,1);
        PlayLeaderAction action = new PlayLeaderAction(player.getLeaderCards().get(1));
        action.useAction(player);

        player.getArrayDeposit().get(0).addContent(1);
        //assertFalse(organize.useAction(player));
        assertThrows(WrongResourcesMovedException.class, () -> organize.useAction(player));
        assertEquals(3, player.getStorage().getThirdRow().getContent());
    }
    @Test //DOESN'T ADD RESOURCES TO A COUNTERTOP WITH A DIFFERENT RESOURCETYPE
    void moveFromLeaderDeposit2(){
        PlayerDashboard player = createPlayerThirdFull();
        MoveFromLeaderToDeposit organize = new MoveFromLeaderToDeposit(0,2,1);
        PlayLeaderAction action = new PlayLeaderAction(player.getLeaderCards().get(1));
        action.useAction(player);

        player.getArrayDeposit().get(0).addContent(1);
        //assertFalse(organize.useAction(player));
        assertThrows(WrongResourcesMovedException.class, () -> organize.useAction(player));
        assertEquals(1, player.getStorage().getSecondRow().getContent());
        assertSame(player.getStorage().getSecondRow().getResourceType(), Resource.ROCK);
        assertTrue(player.getArrayDeposit().get(0).getResourceType() == Resource.SERVANT && player.getArrayDeposit().get(0).getContent() == 1);
    }
    @Test // DOESN'T MOVE MORE RESOURCES THAN WHAT A LEADER ACTUALLY HAS
    void moveFromLeaderDeposit3(){
        PlayerDashboard player = createPlayer();
        MoveFromLeaderToDeposit organize = new MoveFromLeaderToDeposit(0,3,2);
        PlayLeaderAction action = new PlayLeaderAction(player.getLeaderCards().get(1));
        action.useAction(player);

        player.getArrayDeposit().get(0).addContent(1);
        //assertFalse(organize.useAction(player));
        assertThrows(WrongResourcesMovedException.class, () -> organize.useAction(player));
        assertEquals(0, player.getStorage().getThirdRow().getContent());
        assertEquals(1, player.getArrayDeposit().get(0).getContent());
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
        leaderCards.add(0,createLeaderCard(false));
        leaderCards.add(0,createLeaderCard(false));
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,1,nickname,2,false);
        devCards[0].addCard(createDevCard(1));
        devCards[1].addCard(createDevCard(1));
        return player;
    }
    PlayerDashboard createPlayerThirdFull(){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,1);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,3);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(0,createLeaderCard(false));
        leaderCards.add(0,createLeaderCard(false));
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,1,nickname,2,false);
        devCards[0].addCard(createDevCard(1));
        devCards[1].addCard(createDevCard(1));
        return new PlayerDashboard(storage,chest,devCards,leaderCards,1,nickname,2,false);
    }
    LeaderCard createLeaderCard(boolean inGame){
        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new DepositAbility(Resource.SERVANT);
        LeaderCard leader = new LeaderCard(0,0,requirement,specialAbility);
        if(inGame)
            leader.setInGame();
        return leader;
    }
    DevelopmentCard createDevCard(int level){
        Production prod = new Production(new ResourceCount(1,2,0,0,0),new ResourceCount(0,0,3,0,0));
        return new DevelopmentCard(0,5,new ResourceCount(0,0,0,0,0),prod,level, CardColour.BLUE);
    }
}