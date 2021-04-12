package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.Shop;
import it.polimi.ingsw.model.card.DevelopmentCard;

public class CardShopAction extends Action {
    //AFTER CHECKING IF THE CHOSEN POSITION ON THE BOARD CAN FIT THE CHOSEN CARD, RETURN TRUE IF EVERYTHING IS DONE CORRECTLY, FALSE IF NOT
    public boolean buyCard(int row,int column, ResourceCount storageCount, ResourceCount chestCount, PlayerDashboard player, int deckPosition, Shop shop){
        DevelopmentCard chosen = shop.getGrid()[row][column].getDeck().get(0);

        if(checkIfPossible(chosen.getLevel(), player)){
            chosen = shop.buy(row,column);
            player.addDevCards(chosen,deckPosition);
            if(deleteRes(storageCount,chestCount,player))
                return true;
            else
                return false;
        }
        return false;
    }

    //1st METHOD: CHECK ALL POSITIONS AND SEE IF THERE'S ONE THAT FITS THE CARD CHOSEN
    public boolean checkIfPossible(int level, PlayerDashboard player){
        boolean check = false;
        for(int i=0; i<3; i++) {
            if (level == 1)
                if (player.getDevCards()[i] == null)
                    check = true;
                else if (player.getDevCards()[i].getFirst().getLevel() == level - 1)
                    check = true;
        }
        return check;
    }
    //2nd METHOD: CHECK IF THE CHOSEN POSITION FIT THE CARD CHOSEN
    /*public boolean checkIfPossible(int level, int position, PlayerDashboard player){
        boolean check = false;
        if(level == 1)
            if(player.getDevCards()[position] == null)
                check = true;
        else
            if(player.getDevCards()[position].getFirst().getLevel() == level - 1)
                check = true;
        return check;
     }*/
}
