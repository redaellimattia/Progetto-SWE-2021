package it.polimi.ingsw.controller.action.leaderAction;


import it.polimi.ingsw.exceptions.action.CardNotExistsException;
import it.polimi.ingsw.exceptions.action.CardInGameException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiscardLeaderActionTest {

    @Test
    void useActionFirstCard() { //DISCARD FIRST CARD
        PlayerDashboard player = createPlayer();
        LeaderCard card = createLeaderCard(new ColourCount(1, 0, 0, 0));
        DiscardLeaderAction action = new DiscardLeaderAction(card);
        action.useAction(player);
        assertEquals(1, player.getPathPosition());
    }

    @Test
    void useActionSecondCard() { //DISCARD SECOND CARD
        PlayerDashboard player = createPlayer();
        LeaderCard card = createLeaderCard(new ColourCount(0,2,1,0));
        DiscardLeaderAction action = new DiscardLeaderAction(card);
        action.useAction(player);
        assertEquals(1,player.getPathPosition());
    }

    @Test
    void useActionFirstAndSecondCard() { //DISCARD FIRST CARD AFTER SECOND
        PlayerDashboard player = createPlayer();
        LeaderCard card = createLeaderCard(new ColourCount(0,2,1,0));
        DiscardLeaderAction action = new DiscardLeaderAction(card);
        action.useAction(player);
        card = createLeaderCard(new ColourCount(1,0,0,0));
        action = new DiscardLeaderAction(card);
        action.useAction(player);
        assertEquals(2,player.getPathPosition());
    }

    @Test
    void useActionCardNotInModel() { //CAN'T DISCARD, DOESN'T EXIST IN MODEL
        PlayerDashboard player = createPlayer();
        LeaderCard card = createLeaderCard(new ColourCount(5,5,5,5));
        DiscardLeaderAction action = new DiscardLeaderAction(card);
        assertThrows(CardNotExistsException.class, () -> action.useAction(player)); //CAN'T DISCARD
        assertEquals(0,player.getPathPosition());
    }

    @Test
    void useActionInGame() { //CAN'T DISCARD, IT'S IN GAME
        PlayerDashboard player = createPlayer();
        LeaderCard card = createLeaderCard(new ColourCount(0,2,1,0));
        DiscardLeaderAction action = new DiscardLeaderAction(card);
        PlayLeaderAction playAction = new PlayLeaderAction(card);
        playAction.useAction(player); //Playing leader
        assertTrue(player.leadersInGame());
        card.setInGame();
        assertThrows(CardInGameException.class, () -> action.useAction(player)); //CAN'T DISCARD
        assertEquals(0,player.getPathPosition());
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
        return new PlayerDashboard(storage,chest,devCards,leaderCards,1,nickname,2,false);
    }

    LeaderCard createLeaderCard(ColourCount count){
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new ProductionAbility(Resource.COIN);
        return new LeaderCard(0,0,requirement,specialAbility);
    }
}