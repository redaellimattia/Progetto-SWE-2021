package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.action.PaymentFailedException;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.Shop;
import it.polimi.ingsw.model.card.DevelopmentCard;

public class CardShopAction extends Action {
    private final Shop shop;
    private final int row;
    private final int column;
    private final int deckPosition;
    private final ResourceCount storageCount;
    private final ResourceCount chestCount;

    /**
     *
     * @param shop Game's related shop
     * @param row chosen shop's row
     * @param column chosen shop's column
     * @param deckPosition chosen player.DevCards deck position on which the card will be placed
     * @param storageCount resources taken to pay from the storage
     * @param chestCount resources taken to pay from the chest
     */
    public CardShopAction(Shop shop, int row, int column, int deckPosition, ResourceCount storageCount, ResourceCount chestCount) {
        this.shop = shop;
        this.row = row;
        this.column = column;
        this.deckPosition = deckPosition;
        this.storageCount = storageCount;
        this.chestCount = chestCount;
    }

    /**
     * After checking if the chosen position on the board can fit the chosen card and the player can afford it, return true if everything is done correctly, false if not
     * @param player player that wants to buy the card
     * @param turnManager object responsible for the handling of the turn
     * @return true if everything went fine
     */
    @Override
    public boolean useAction(PlayerDashboard player, PlayerTurnManager turnManager) {

        DevelopmentCard chosen = shop.getGrid()[row][column].getFirst();
        ResourceCount chosenCost = new ResourceCount(chosen.getCost().getCoins(),chosen.getCost().getRocks(),chosen.getCost().getServants(),chosen.getCost().getShields(),0);
        if(player.getLeaderCards().get(0).isInGame())
            player.getLeaderCards().get(0).getSpecialAbility().useDiscountAbility(chosenCost);
        if(player.getLeaderCards().get(1).isInGame())
            player.getLeaderCards().get(1).getSpecialAbility().useDiscountAbility(chosenCost);

        if(checkIfPossible(chosen.getLevel(),deckPosition, player) && chosenCost.equals(ResourceCount.getTotal(storageCount,chestCount))){
            chosen = shop.buy(row,column,player,turnManager);
            player.addDevCards(chosen,deckPosition);
            deleteRes(storageCount,chestCount,player);
            return true;
        }
        else {
            throw new PaymentFailedException(player,turnManager);
        }
    }


    /**
     * Check if the chosen position fit the card chosen
     * @param level of the card
     * @param position of the deck chosen by the player
     * @param player player who's doing the action
     * @return true if the positioning is doable
     */
    private boolean checkIfPossible(int level, int position, PlayerDashboard player){
        if(level == 1)
            return player.getDevCards()[position].getDeck().size() == 0;
        else
            if(player.getDevCards()[position].getDeck().size()>0)
                return player.getDevCards()[position].getFirst().getLevel() == (level - 1);
            else
                return false;
    }
}
