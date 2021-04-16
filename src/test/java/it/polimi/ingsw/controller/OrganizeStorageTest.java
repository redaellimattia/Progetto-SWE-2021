package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrganizeStorageTest {

    /*@Test
    void swapShelves() {
        PlayerDashboard player = createPlayer();
        OrganizeStorage organize = new OrganizeStorage();
        organize.swapShelves(player,2,3);
        CounterTop check1 = new CounterTop(Resource.SERVANT,0);
        CounterTop check2 = new CounterTop(Resource.ROCK,2);
        assertTrue(player.getStorage().getSecondRow().equals(check1));
        assertTrue(player.getStorage().getThirdRow().equals(check2));
    }
    @Test //DOESNT DO SWAP IF IT'S NOT POSSIBLE;
    void swapShelves1() {
        PlayerDashboard player = createPlayer();
        OrganizeStorage organize = new OrganizeStorage();
        assertFalse(organize.swapShelves(player,2,1));
    }
    @Test
    void moveFromLeaderDeposit(){
        PlayerDashboard player = createPlayer();
        OrganizeStorage organize = new OrganizeStorage();
        LeaderAction action = new LeaderAction();
        action.playLeader(player.getLeaderCards().get(1),player );

        player.getArrayDeposit().get(0).addContent(1);
        assertTrue(organize.moveFromLeaderDeposit(player, player.getArrayDeposit().get(0),3,1 ));
        assertTrue(player.getStorage().getThirdRow().getContent() == 1);
    }
    //COMMENTED BECAUSE IT WAS A PARTICULAR SITUATION WHERE THIRDROW HAS 3 ELEM AND I TRY TO PUT ONE MORE.
    /*@Test
    void moveFromLeaderDeposit1(){ COMMENTED
        PlayerDashboard player = createPlayer();
        OrganizeStorage organize = new OrganizeStorage();
        LeaderAction action = new LeaderAction();
        action.playLeader(player.getLeaderCards().get(1),player );

        player.getArrayDeposit().get(0).addContent(1);
        assertFalse(organize.moveFromLeaderDeposit(player, player.getArrayDeposit().get(0),3,1 ));
        assertTrue(player.getStorage().getThirdRow().getContent() == 3);
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
        leaderCards.add(0,createLeaderCard(false));
        leaderCards.add(0,createLeaderCard(false));
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,nickname,2);
        devCards[0].addCard(createDevCard(3));
        devCards[1].addCard(createDevCard(1));
        return player;
    }
    LeaderCard createLeaderCard(boolean inGame){
        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new DepositAbility(Resource.SERVANT);
        LeaderCard leader = new LeaderCard(0,requirement,specialAbility);
        if(inGame)
            leader.setInGame();
        return leader;
    }
    DevelopmentCard createDevCard(int level){
        Production prod = new Production(new ResourceCount(1,2,0,0,0),new ResourceCount(0,0,3,0,0));
        return new DevelopmentCard(5,new ResourceCount(0,0,0,0,0),prod,level, CardColour.BLUE);
    }*/
}