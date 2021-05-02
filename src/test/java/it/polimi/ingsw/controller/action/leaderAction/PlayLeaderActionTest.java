package it.polimi.ingsw.controller.action.leaderAction;

import it.polimi.ingsw.exceptions.action.CardNotExistsException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayLeaderActionTest {

    @Test
    void useActionFirstCard() { //PLAY FIRST CARD
        PlayerDashboard player = createPlayer();
        LeaderCard card = createLeaderCard(new ColourCount(1, 0, 0, 0));
        PlayLeaderAction action = new PlayLeaderAction(card);
        assertFalse(player.leadersInGame()); //Not in game
        action.useAction(player); //Play the card
        assertTrue(player.leadersInGame()); //Now it's in game
        assertTrue(player.getLeaderCards().get(1).isInGame()); //CARD POS=1 IN GAME
        assertFalse(player.getLeaderCards().get(0).isInGame()); //CARD POS=0 NOT IN GAME
    }

    @Test
    void useActionSecondCard() { //PLAY SECOND CARD
        PlayerDashboard player = createPlayer();
        LeaderCard card = createLeaderCard(new ColourCount(0,2,1,0));
        PlayLeaderAction action = new PlayLeaderAction(card);
        assertFalse(player.leadersInGame()); //Not in game
        action.useAction(player); //Play the card
        assertTrue(player.leadersInGame()); //Now it's in game
        assertFalse(player.getLeaderCards().get(1).isInGame()); //CARD POS=1 NOT IN GAME
        assertTrue(player.getLeaderCards().get(0).isInGame()); //CARD POS=0 IN GAME
    }

    @Test
    void useActionPlayFirstAfterSecondIsInGame() { //PLAY FIRST CARD AFTER SECOND ALREADY IN GAME
        PlayerDashboard player = createPlayer();
        LeaderCard card = createLeaderCard(new ColourCount(0,2,1,0));
        PlayLeaderAction action = new PlayLeaderAction(card);
        action.useAction(player); //Play the card
        card = createLeaderCard(new ColourCount(1,0,0,0));
        action = new PlayLeaderAction(card);
        action.useAction(player); //Play the card
        assertTrue(player.getLeaderCards().get(1).isInGame()); //CARD POS=0 IN GAME
    }

    @Test
    void useActionCardDoesntExists(){ //CARD THAT DOESN'T EXISTS
        PlayerDashboard player = createPlayer();
        LeaderCard card = createLeaderCard(new ColourCount(5,5,5,5));
        PlayLeaderAction action = new PlayLeaderAction(card);
        assertFalse(player.leadersInGame()); //Not in game
        assertThrows(CardNotExistsException.class, () -> action.useAction(player)); //Play the card
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

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(0,createLeaderCard(new ColourCount(1,0,0,0)));
        leaderCards.add(0,createLeaderCard(new ColourCount(0,2,1,0)));
        return new PlayerDashboard(storage,chest,devCards,leaderCards,1,nickname,2, false);
    }

    LeaderCard createLeaderCard(ColourCount count){
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new ProductionAbility(Resource.COIN);
        return new LeaderCard(0,requirement,specialAbility);
    }
}