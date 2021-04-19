package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResourceRequirementTest {

    @Test
    void isPlayable() {
        //CHEST
        PlayerDashboard player = createPlayer(new CounterTop(Resource.COIN,0),new CounterTop(Resource.COIN,0),new CounterTop(Resource.COIN,0)
                ,new ResourceCount(5,5,5,5,0));

        ResourceRequirement req = new ResourceRequirement(new ResourceCount(1,1,0,0,0));
        assertTrue(req.isPlayable(player));

        //CHEST NOT ENOUGH
         player = createPlayer(new CounterTop(Resource.COIN,0),new CounterTop(Resource.COIN,0),new CounterTop(Resource.COIN,0)
                ,new ResourceCount(1,0,50,5,0));

         req = new ResourceRequirement(new ResourceCount(1,1,0,0,0));
        assertFalse(req.isPlayable(player));

        //STORAGE
        player = createPlayer(new CounterTop(Resource.COIN,1),new CounterTop(Resource.ROCK,1),new CounterTop(Resource.COIN,0)
                ,new ResourceCount(0,0,0,0,0));
        req = new ResourceRequirement(new ResourceCount(1,1,0,0,0));
        assertTrue(req.isPlayable(player));

        //NOT ENOUGH
        player = createPlayer(new CounterTop(Resource.COIN,0),new CounterTop(Resource.ROCK,1),new CounterTop(Resource.COIN,0)
                ,new ResourceCount(0,0,0,0,0));
        req = new ResourceRequirement(new ResourceCount(1,1,0,1,0));
        assertFalse(req.isPlayable(player));

        //ABILITYDEPOSIT
        player = createPlayer(new CounterTop(Resource.COIN,0),new CounterTop(Resource.ROCK,1),new CounterTop(Resource.COIN,0)
                ,new ResourceCount(0,0,0,0,0));
        player.initArrayDeposit(Resource.COIN);
        player.addToDeposit(Resource.COIN);
        player.initArrayDeposit(Resource.ROCK);
        player.addToDeposit(Resource.ROCK);
        req = new ResourceRequirement(new ResourceCount(1,1,0,0,0));
        assertTrue(req.isPlayable(player));

        //ABILITYDEPOSITNOTENOUGH
        player = createPlayer(new CounterTop(Resource.COIN,0),new CounterTop(Resource.ROCK,1),new CounterTop(Resource.COIN,0)
                ,new ResourceCount(0,0,0,0,0));
        player.initArrayDeposit(Resource.COIN);
        player.addToDeposit(Resource.COIN);
        player.initArrayDeposit(Resource.ROCK);
        player.addToDeposit(Resource.ROCK);
        req = new ResourceRequirement(new ResourceCount(1,1,0,1,0));
        assertFalse(req.isPlayable(player));
    }
    PlayerDashboard createPlayer(CounterTop firstRow,CounterTop secondRow, CounterTop thirdRow,ResourceCount resCount){
        String nickname = "Primo";
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        return new PlayerDashboard(storage,resCount,devCards,leaderCards,1,nickname,2);
    }
}