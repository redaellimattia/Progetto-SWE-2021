package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

public class Shop {
    private Deck[][] shopGrid = new DeckShop[3][4];

    public DevelopmentCard getFirst(int row, int column){
        return shopGrid[row][column].getFirst();
    }

    public Deck[][] getGrid(){
        return shopGrid;
    }

    public DevelopmentCard buy(int row, int column){ //the controller check if the player can buy the card before;
        DevelopmentCard bought = shopGrid[row][column].getFirst();
        shopGrid[row][column].getDeck().remove(0);
        return bought;
    }
}
