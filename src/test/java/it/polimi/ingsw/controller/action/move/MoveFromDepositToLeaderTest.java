package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.action.leaderAction.PlayLeaderAction;
import it.polimi.ingsw.exceptions.action.WrongResourcesMovedException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveFromDepositToLeaderTest {

    @Test
    void useAction() { //BASIC MOVE OF RESOURCES
        PlayerDashboard player = createPlayerThirdFull();
        MoveFromDepositToLeader organize= new MoveFromDepositToLeader(0,3,1);
        PlayLeaderAction action = new PlayLeaderAction(player.getLeaderCards().get(1));
        action.useAction(player);
        organize.useAction(player);
        assertEquals(1, player.getArrayDeposit().get(0).getContent());
        assertEquals(2, player.getStorage().getThirdRow().getContent());
    }
    @Test
    void useAction1() { //BASIC MOVE OF RESOURCES AND STOP WHEN LEADERDEPOSIT IS FULL
        PlayerDashboard player = createPlayerThirdFull();
        MoveFromDepositToLeader organize= new MoveFromDepositToLeader(0,3,2);
        PlayLeaderAction action = new PlayLeaderAction(player.getLeaderCards().get(1));
        action.useAction(player);
        organize.useAction(player);
        assertEquals(2, player.getArrayDeposit().get(0).getContent());
        assertEquals(1, player.getStorage().getThirdRow().getContent());
        MoveFromDepositToLeader organize1= new MoveFromDepositToLeader(0,3,1);
        //assertFalse(organize1.useAction(player));
        assertThrows(WrongResourcesMovedException.class, () -> organize1.useAction(player));
        assertEquals(2, player.getArrayDeposit().get(0).getContent());
        assertEquals(1, player.getStorage().getThirdRow().getContent());
    }
    @Test
    void useAction2() { //DOESN'T MOVE RESOURCES TO A LEADER WITH A DIFFERENT RESOURCETYPE
        PlayerDashboard player = createPlayerThirdFull();
        MoveFromDepositToLeader organize= new MoveFromDepositToLeader(0,2,1);
        PlayLeaderAction action = new PlayLeaderAction(player.getLeaderCards().get(1));
        action.useAction(player);
        //assertFalse(organize.useAction(player));
        assertThrows(WrongResourcesMovedException.class, () -> organize.useAction(player));
        assertEquals(0, player.getArrayDeposit().get(0).getContent());
        assertEquals(1, player.getStorage().getSecondRow().getContent());
    }
    @Test
    void useAction3(){ //DOESN'T MOVE MORE RESOURCES THAN WHAT A SHELF HAS
        PlayerDashboard player = createPlayerThirdFull();
        MoveFromDepositToLeader organize= new MoveFromDepositToLeader(0,3,2);
        PlayLeaderAction action = new PlayLeaderAction(player.getLeaderCards().get(1));
        action.useAction(player);
        organize.useAction(player);
        assertEquals(2, player.getArrayDeposit().get(0).getContent());
        assertEquals(1, player.getStorage().getThirdRow().getContent());
        MoveFromDepositToLeader organize1= new MoveFromDepositToLeader(0,3,1);
        //assertFalse(organize1.useAction(player));
        assertThrows(WrongResourcesMovedException.class, () -> organize1.useAction(player));
        assertEquals(2, player.getArrayDeposit().get(0).getContent());
        assertEquals(1, player.getStorage().getThirdRow().getContent());
        PlayLeaderAction action1 = new PlayLeaderAction(player.getLeaderCards().get(0));
        action1.useAction(player);
        MoveFromDepositToLeader organize2= new MoveFromDepositToLeader(1,3,2);
        //assertFalse(organize2.useAction(player));
        assertThrows(WrongResourcesMovedException.class, () -> organize2.useAction(player));
        assertEquals(0, player.getArrayDeposit().get(0).getContent());
        assertEquals(2, player.getArrayDeposit().get(1).getContent());
        assertEquals(1, player.getStorage().getThirdRow().getContent());
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
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(0,createLeaderCard(false));
        leaderCards.add(0,createLeaderCard(false));
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,1,nickname,2,false);
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        devCards[0].addCard(createDevCard(1));
        devCards[1].addCard(createDevCard(1));
        return player;
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