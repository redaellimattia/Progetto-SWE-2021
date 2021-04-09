package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DepositAbilityTest {

    @Test
    void useDepositAbility() {
        PlayerDashboard player = createPlayer();
        DepositAbility ability = new DepositAbility(Resource.COIN);
        ability.useDepositAbility(player);
        assertTrue(player.getArrayDeposit().size()!=0&&player.getArrayDeposit().get(0).getResourceType().equals(ability.getResourceType()));

        ability = new DepositAbility(Resource.ROCK);
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
        ResourceCount chest = new ResourceCount(0,0,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ResourceCount input = new ResourceCount(0,0,0,0,0);
        ResourceCount output = new ResourceCount(0,0,0,0,0);
        Production basicProduction = new Production(input,output);
        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,basicProduction,1,nickname,2);
        return player;
    }
}