package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.exceptions.action.CardNotExistsException;
import it.polimi.ingsw.exceptions.action.NoWhiteMarbleException;
import it.polimi.ingsw.exceptions.action.WrongAbilityException;
import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

public class ConvertWhiteMarble extends AtomicMarketAction {

    private LeaderCard leaderCard;
    private int storageRow;

    /**
     *
     * @param leaderCard the leader card used to convert the white marble
     * @param storageRow the number of the storage row in which the resource should be added
     *                   1-3: first row, second row, third row
     *                   4: special deposit (leader card ability)
     */
    public ConvertWhiteMarble(LeaderCard leaderCard, int storageRow) {
        this.leaderCard = leaderCard;
        this.storageRow = storageRow;
    }

    @Override
    public boolean checkAction(MarketMarble marble, PlayerDashboard player, Storage tempStorage, ArrayList<CounterTop> tempArrayDeposit) {
        if(marble.getColour() != MarbleColour.WHITE) {
            return false;
            //throw new NoWhiteMarbleException(player); // User cannot convert a marble that isn't white
        }
        if(!player.leaderCardExists(leaderCard) || !leaderCard.isInGame()) {
            return false;
            //throw new CardNotExistsException("Leader Card", player); // User must own the leaderCard
        }
        Resource resource = leaderCard.getSpecialAbility().useWhiteChangeAbility();
        if(resource == null) {
            return false;
            //throw new WrongAbilityException("White change ability", player); // Leader card must have WhiteChangeAbility
        }
        return checkStore(tempStorage, tempArrayDeposit, resource, storageRow);
    }
    /**
     *
     * @param marble the marble to convert
     * @param player the player performing the action
     * @return true if ended correctly
     *         false if the action was illegal
     */
    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player, GameManager gameManager) {
        Resource resource = leaderCard.getSpecialAbility().useWhiteChangeAbility();
        return storeResource(player, resource, storageRow);
    }
}
