package it.polimi.ingsw.controller.action.leaderAction;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.action.CannotPlayCardException;
import it.polimi.ingsw.exceptions.action.CardNotExistsException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayLeaderActionTest {

    @Test
    void useActionFirstCard() { //PLAY FIRST CARD
        PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
        LeaderCard card = createLeaderCard(new ResourceCount(1,0,0,0,0));
        PlayLeaderAction action = new PlayLeaderAction(card);
        assertFalse(player.leadersInGame()); //Not in game
        action.useAction(player,turnManager); //Play the card
        assertTrue(player.leadersInGame()); //Now it's in game
        assertTrue(player.getLeaderCards().get(1).isInGame()); //CARD POS=1 IN GAME
        assertFalse(player.getLeaderCards().get(0).isInGame()); //CARD POS=0 NOT IN GAME
    }

    @Test
    void useActionSecondCard() { //PLAY SECOND CARD
        PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
        LeaderCard card = createLeaderCard(new ResourceCount(0,1,0,0,0));
        PlayLeaderAction action = new PlayLeaderAction(card);
        assertFalse(player.leadersInGame()); //Not in game
        action.useAction(player,turnManager); //Play the card
        assertTrue(player.leadersInGame()); //Now it's in game
        assertFalse(player.getLeaderCards().get(1).isInGame()); //CARD POS=1 NOT IN GAME
        assertTrue(player.getLeaderCards().get(0).isInGame()); //CARD POS=0 IN GAME
    }

    @Test
    void useActionPlayFirstAfterSecondIsInGame() { //PLAY FIRST CARD AFTER SECOND ALREADY IN GAME
        PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
        LeaderCard card = createLeaderCard(new ResourceCount(0,1,0,0,0));
        PlayLeaderAction action = new PlayLeaderAction(card);
        action.useAction(player,turnManager); //Play the card
        card = createLeaderCard(new ResourceCount(1,0,0,0,0));
        action = new PlayLeaderAction(card);
        action.useAction(player,turnManager); //Play the card
        assertTrue(player.getLeaderCards().get(1).isInGame()); //CARD POS=0 IN GAME
    }

    @Test
    void useActionCardDoesntExists(){ //CARD THAT DOESN'T EXISTS
        PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
        LeaderCard card = createLeaderCard(new ResourceCount(5,0,0,0,0));
        PlayLeaderAction action = new PlayLeaderAction(card);
        assertFalse(player.leadersInGame()); //Not in game
        assertThrows(CardNotExistsException.class, () -> action.useAction(player,turnManager)); //Play the card
        assertFalse(player.leadersInGame()); //Not in game
    }

    @Test
    void useActionCantPlay(){ //CANT PLAY CARD
        PlayerDashboard player = createPlayer();
        PlayerTurnManager turnManager = createTurnManager(player);
        player.getStorage().getFirstRow().removeContent(1);
        assertEquals(0,player.getStorage().getFirstRow().getContent());
        LeaderCard card = createLeaderCard(new ResourceCount(1,0,0,0,0));
        PlayLeaderAction action = new PlayLeaderAction(card);
        assertFalse(player.leadersInGame()); //Not in game
        assertThrows(CannotPlayCardException.class, () -> action.useAction(player,turnManager)); //Play the card
        assertFalse(player.leadersInGame()); //Not in game
    }

    PlayerDashboard createPlayer(){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,2);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount chest = new ResourceCount(0,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(0,createLeaderCard(new ResourceCount(1,0,0,0,0)));
        leaderCards.add(0,createLeaderCard(new ResourceCount(0,1,0,0,0)));
        PlayerDashboard p = new PlayerDashboard(storage,chest,devCards,leaderCards,nickname,2, false);
        p.addObserver(playerObserver);
        p.getStorage().addObserver(p);
        return p;
    }

    LeaderCard createLeaderCard(ResourceCount count){
        ResourceRequirement requirement = new ResourceRequirement(count);
        SpecialAbility specialAbility = new ProductionAbility(Resource.COIN);
        return new LeaderCard(0,0,requirement,specialAbility);
    }
    PlayerTurnManager createTurnManager(PlayerDashboard player){
        return new PlayerTurnManager(player);
    }
}