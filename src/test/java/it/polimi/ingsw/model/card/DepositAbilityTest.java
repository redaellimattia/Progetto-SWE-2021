package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DepositAbilityTest {

    @Test
    void useDepositAbilityCoin() {
        PlayerDashboard player = createPlayer();
        DepositAbility ability = new DepositAbility(Resource.COIN);
        ability.useDepositAbility(player);
        assertTrue(player.getArrayDeposit().size() != 0 && player.getArrayDeposit().get(0).getResourceType().equals(ability.getResourceType()));
    }
    @Test
    void useDepositAbilityRock() {
        PlayerDashboard player = createPlayer();
        DepositAbility ability = new DepositAbility(Resource.ROCK);
        ability.useDepositAbility(player);
        assertTrue(player.getArrayDeposit().size()!=0&&player.getArrayDeposit().get(0).getResourceType().equals(ability.getResourceType()));
    }
    PlayerDashboard createPlayer(){
        String nickname = "Primo";
        Resource coins =  Resource.COIN;
        CounterTop firstRow = new CounterTop(coins,0);
        CounterTop secondRow = new CounterTop(coins,0);
        CounterTop thirdRow = new CounterTop(coins,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount chest = new ResourceCount(0,0,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,1,nickname,2,false );
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        return player;
    }
}