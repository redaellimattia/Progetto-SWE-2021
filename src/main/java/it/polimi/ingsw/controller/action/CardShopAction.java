package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.Parameter;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.Shop;
import it.polimi.ingsw.model.card.DevelopmentCard;

public class CardShopAction extends Action {
    //AFTER CHECKING IF THE CHOSEN POSITION ON THE BOARD CAN FIT THE CHOSEN CARD AND THE PLAYER CAN AFFORD IT, RETURN TRUE IF EVERYTHING IS DONE CORRECTLY, FALSE IF NOT
    @Override
    public boolean useAction(PlayerDashboard player, Parameter param ){
        Shop shop = param.getShop();
        int row = param.getFrom_row();
        int column = param.getTo_column();
        int deckPosition = param.getNumber_deckPosition();
        ResourceCount storageCount = param.getStorageCount();
        ResourceCount chestCount = param.getChestCount();
        DevelopmentCard chosen = shop.getGrid()[row][column].getFirst();

        if(checkIfPossible(chosen.getLevel(),deckPosition, player) && chosen.getCost().equals(ResourceCount.getTotal(storageCount,chestCount))){
            chosen = shop.buy(row,column);
            player.addDevCards(chosen,deckPosition);
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
