package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.CardNotExistsException;
import it.polimi.ingsw.exceptions.action.PaymentFailedException;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.Optional;

public class DevCardProductionAction extends Action {
    DevelopmentCard card;
    ResourceCount storageCount;
    ResourceCount chestCount;

    /**
     *
     * @param card DevCard chosen by the player to use its production
     * @param storageCount amount of resources in the Storage, that the player wants to pay with, can be null
     * @param chestCount amount of resources in the Chest, that the player wants to pay with, can be null
     */
    public DevCardProductionAction(DevelopmentCard card, ResourceCount storageCount, ResourceCount chestCount) {
        this.card = card;
        this.storageCount = storageCount;
        this.chestCount = chestCount;
    }

    /**
     * Devcards,
     * return true if everything is done correctly (payment from storage and/or chest) and player has enough resources
     * i get from the view the card, the chosen amount of resources from storage and chest (eventually null) and the player
     *
     * @param player player that is doing the action
     */
    @Override
    public boolean useAction(PlayerDashboard player, PlayerTurnManager turnManager) {
        Optional<ResourceCount> output = card.getProductionPower().useProduction(ResourceCount.getTotal(storageCount,chestCount));


        //If devCard doesnt exist in the model then throw Exception
        if(!player.devCardExists(card))
            throw new CardNotExistsException("Development Card",player,turnManager);

        if(output.isEmpty() || !deleteRes(storageCount,chestCount,player)) //NOT ENOUGH RESOURCES OR PAYMENT FAILED
            throw new PaymentFailedException(player,turnManager);

        player.incrementBufferProduction(output.get());
        return true;
    }
}
