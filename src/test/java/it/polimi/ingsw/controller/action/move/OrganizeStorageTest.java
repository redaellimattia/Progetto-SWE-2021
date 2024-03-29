package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.exceptions.action.WrongResourcesMovedException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrganizeStorageTest {

    @Test
    void swapShelves() {
        PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
        OrganizeStorage organize = new OrganizeStorage(2,3);
        organize.useAction(player,turnManager);
        CounterTop check1 = new CounterTop(Resource.SERVANT,0);
        CounterTop check2 = new CounterTop(Resource.ROCK,2);
        assertEquals(player.getStorage().getSecondRow(), check1);
        assertEquals(player.getStorage().getThirdRow(), check2);
        OrganizeStorage organize1 = new OrganizeStorage(3,1);
        //assertFalse(organize1.useAction(player));
        assertThrows(WrongResourcesMovedException.class, () -> organize1.useAction(player,turnManager));
    }
    @Test //DOESNT DO SWAP IF IT'S NOT POSSIBLE AND SWAP EVEN 2 EMPTY SHELVES
    void swapShelves1() {
        PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
        OrganizeStorage organize = new OrganizeStorage(2,1);
        //assertFalse(organize.useAction(player));
        assertThrows(WrongResourcesMovedException.class, () -> organize.useAction(player,turnManager));
        CounterTop check = new CounterTop(Resource.COIN,0);
        try{player.getStorage().setFirstRow(check);}catch(CounterTopOverloadException e){}
        OrganizeStorage organize1 = new OrganizeStorage(1,3);
        organize1.useAction(player,turnManager);
        assertEquals(player.getStorage().getThirdRow().getResourceType(),Resource.COIN);
        assertEquals(player.getStorage().getFirstRow().getResourceType(),Resource.SERVANT);

    }


    PlayerDashboard createPlayer(){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,2);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(0,createLeaderCard(false));
        leaderCards.add(0,createLeaderCard(false));
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,nickname,2,false);
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
    PlayerTurnManager createTurnManager(PlayerDashboard player){
        return new PlayerTurnManager(player);
    }
}