package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.exceptions.action.CardNotExistsException;
import it.polimi.ingsw.exceptions.action.PaymentFailedException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardProductionActionTest {

    @Test
    void useActionStorageOnlyPayment() { //STORAGE ONLY PAYMENT
        PlayerDashboard player = createPlayer(true);
        ResourceCount storageCount = new ResourceCount(1, 0, 0, 0, 0);
        ResourceCount result = new ResourceCount(0, 2, 0, 0, 0);
        ResourceCount resultBuff = new ResourceCount(1, 0, 0, 0, 1);
        LeaderCard card = createLeaderCard(true);
        LeaderCardProductionAction action = new LeaderCardProductionAction(card,storageCount,new ResourceCount(0, 0, 0, 0, 0),Resource.COIN);
        action.useAction(player);
        assertEquals(result, player.getStorage().readStorage()); //Paid correctly
        assertEquals(resultBuff, player.getBufferProduction()); //Buffer equals to production output
    }

    @Test
    void useActionChestOnlyPayment() { //CHEST ONLY PAYMENT
        PlayerDashboard player = createPlayer(true);
        ResourceCount chestCount = new ResourceCount(1,0,0,0,0);
        ResourceCount result = new ResourceCount(4,5,0,0,0);
        ResourceCount resultBuff = new ResourceCount(1,0,0,0,1);
        LeaderCard card = createLeaderCard(true);
        LeaderCardProductionAction action = new LeaderCardProductionAction(card,new ResourceCount(0, 0, 0, 0, 0),chestCount,Resource.COIN);
        action.useAction(player);
        assertEquals(result,player.getChest()); //Paid correctly
        assertEquals(resultBuff, player.getBufferProduction()); //Buffer equals to production output
    }

    @Test
    void useActionNotEnoughResourcesToPay() {//CANT PAY
        PlayerDashboard player = createPlayer(true);
        ResourceCount chestCount = new ResourceCount(0,0,0,0,0);
        LeaderCard card = createLeaderCard(true);
        LeaderCardProductionAction action = new LeaderCardProductionAction(card,new ResourceCount(0, 0, 0, 0, 0),chestCount,Resource.COIN);
        assertThrows(PaymentFailedException.class, () -> action.useAction(player));
    }

    @Test
    void useActionMoreResourcesThanNecessaryToPay() { //OVERPAY
        PlayerDashboard player = createPlayer(true);
        ResourceCount chestCount = new ResourceCount(5,5,5,5,0);
        LeaderCard card = createLeaderCard(true);
        LeaderCardProductionAction action = new LeaderCardProductionAction(card,new ResourceCount(0, 0, 0, 0, 0),chestCount,Resource.COIN);
        assertThrows(PaymentFailedException.class, () -> action.useAction(player));
    }

    @Test
    void useActionWrongCardNotInGame() { //WRONG CARD, IT ISNT IN GAME
        PlayerDashboard player = createPlayer(false);
        LeaderCard card = createLeaderCard(false);
        ResourceCount chestCount = new ResourceCount(1,0,0,0,0);
        LeaderCardProductionAction action = new LeaderCardProductionAction(card,new ResourceCount(0, 0, 0, 0, 0),chestCount,Resource.COIN);
        assertThrows(CardNotExistsException.class, () -> action.useAction(player));
    }

    @Test
    void useActionWrongCardNotInGamePassingAsItIs() { //WRONG CARD, Passing as it is inGame but it actually isnt
        PlayerDashboard player = createPlayer(false);
        ResourceCount chestCount = new ResourceCount(1,0,0,0,0);
        LeaderCard card = createLeaderCard(true);
        LeaderCardProductionAction action = new LeaderCardProductionAction(card,new ResourceCount(0, 0, 0, 0, 0),chestCount,Resource.COIN);
        assertThrows(CardNotExistsException.class, () -> action.useAction(player));
    }

    @Test
    void useActionWrongCardNotAProductionAbility() { //WRONG CARD, NOT PROD ABILITY
        PlayerDashboard player = createPlayer(false);
        LeaderCard card = createLeaderCardNotProd();
        ResourceCount chestCount = new ResourceCount(1,0,0,0,0);
        LeaderCardProductionAction action = new LeaderCardProductionAction(card,new ResourceCount(0, 0, 0, 0, 0),chestCount,Resource.COIN);
        assertThrows(CardNotExistsException.class, () -> action.useAction(player));
    }


    PlayerDashboard createPlayer(boolean inGame){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,2);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ServerLobby serverLobby = new ServerLobby(2,1);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();

        leaderCards.add(0,createLeaderCard(inGame));
        leaderCards.add(0,createLeaderCard(false));
        PlayerDashboard p = new PlayerDashboard(storage,chest,devCards,leaderCards,nickname,2,false);
        p.addObserver(serverLobby);
        p.getStorage().addObserver(p);
        return p;
    }

    LeaderCard createLeaderCard(boolean inGame){
        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new ProductionAbility(Resource.COIN);
        LeaderCard leader = new LeaderCard(0,0,requirement,specialAbility);
        if(inGame)
            leader.setInGame();
        return leader;
    }

    LeaderCard createLeaderCardNotProd(){
        ColourCount count = new ColourCount(1,0,0,0);
        TypeOfCardRequirement requirement = new TypeOfCardRequirement(count);
        SpecialAbility specialAbility = new DiscountAbility(Resource.COIN);
        LeaderCard leader = new LeaderCard(0,0,requirement,specialAbility);
        leader.setInGame();
        return leader;
    }
}