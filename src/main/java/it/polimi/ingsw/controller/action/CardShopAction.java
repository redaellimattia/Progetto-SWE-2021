package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.Shop;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;

public class CardShopAction extends Action {
    private Shop shop;
    private int row;
    private int column;
    private int deckPosition;
    private ResourceCount storageCount;
    private ResourceCount chestCount;

    public CardShopAction(Shop shop, int row, int column, int deckPosition, ResourceCount storageCount, ResourceCount chestCount) {
        this.shop = shop;
        this.row = row;
        this.column = column;
        this.deckPosition = deckPosition;
        this.storageCount = storageCount;
        this.chestCount = chestCount;
    }

    //AFTER CHECKING IF THE CHOSEN POSITION ON THE BOARD CAN FIT THE CHOSEN CARD AND THE PLAYER CAN AFFORD IT, RETURN TRUE IF EVERYTHING IS DONE CORRECTLY, FALSE IF NOT
    @Override
    public boolean useAction(PlayerDashboard player){

        DevelopmentCard chosen = shop.getGrid()[row][column].getFirst();

        if(checkIfPossible(chosen.getLevel(),deckPosition, player) && chosen.getCost().equals(ResourceCount.getTotal(storageCount,chestCount))){
            chosen = shop.buy(row,column);
            player.addDevCards(chosen,deckPosition);

            for (LeaderCard l: player.getLeaderCards()) {
                Resource res = l.getSpecialAbility().getResourceType();
                if(storageCount != null)
                    if(res.get(storageCount) != 0)
                        l.getSpecialAbility().useDiscountAbility(storageCount);
                else if(chestCount != null)
                    if(res.get(chestCount) != 0 && chestCount != null)
                        l.getSpecialAbility().useDiscountAbility(chestCount);
            }
            deleteRes(storageCount,chestCount,player);
            return true;
        }
        return false;
    }

    //CHECK IF THE CHOSEN POSITION FIT THE CARD CHOSEN
    private boolean checkIfPossible(int level, int position, PlayerDashboard player){
        boolean check = false;
        if(level == 1)
            if(player.getDevCards()[position].getDeck().size() == 0)
                check = true;
        else
            if(player.getDevCards()[position].getFirst().getLevel() == (level - 1))
                check = true;
        return check;
     }
}
