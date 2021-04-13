package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TypeOfCardRequirementTest {

    @Test
    void isPlayable() {
        PlayerDashboard player = createPlayer();
        Production prod = new Production(new ResourceCount(0,0,0,0,0),new ResourceCount(0,0,0,0,0));
        DevelopmentCard card1 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1, CardColour.BLUE);
        DevelopmentCard card2 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1,CardColour.GREEN);
        DevelopmentCard card3 = new DevelopmentCard(1,new ResourceCount(1,0,0,0,0),prod,1,CardColour.YELLOW);

        player.getDevCards()[0] = new DeckDashboard();
        player.getDevCards()[1] = new DeckDashboard();
        player.getDevCards()[2] = new DeckDashboard();
        player.getDevCards()[0].addCard(card1);
        player.getDevCards()[1].addCard(card2);
        player.getDevCards()[1].addCard(card3);

        TypeOfCardRequirement req = new TypeOfCardRequirement(new ColourCount(1,1,0,0)); //Playable
        assertTrue(req.isPlayable(player));

        req = new TypeOfCardRequirement(new ColourCount(1,0,0,1)); //Not Playable
        assertFalse(req.isPlayable(player));

        req = new TypeOfCardRequirement(new ColourCount(1,1,1,0)); //Playable
        assertTrue(req.isPlayable(player));
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