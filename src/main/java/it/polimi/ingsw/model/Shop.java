package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColour;

import java.util.ArrayList;

public class Shop {
    public Shop(Deck[][] shopGrid) {
        this.shopGrid = shopGrid;
    }

    private Deck[][] shopGrid = new DeckShop[3][4];

    public DevelopmentCard getFirst(int row, int column){
        return shopGrid[row][column].getFirst();
    }

    public Deck[][] getGrid(){ return shopGrid; }

    // BUY A SELECTED CARD FROM SHOP, RETURN THE CARD TO THE CALLER AND DELETE IT FROM THE GRID;
    public DevelopmentCard buy(int row, int column){ //the controller check if the player can buy the card before;
        DevelopmentCard bought = shopGrid[row][column].getFirst();
        shopGrid[row][column].removeFirst();
        return bought;
    }

    //METHOD TO DISCARD 2 CARD FROM THE POOL OF CARD OF THAT COLOUR BECAUSE OF THE SOLOTOKEN;
    public void discardFromToken(CardColour colour){
        int column = colour.getColumn();
        ArrayList<DevelopmentCard> deck;
        int toDelete=2, i=2;
        while(toDelete > 0){
            deck = shopGrid[i][column].getDeck();
            while(deck.size() != 0 && toDelete > 0){
                shopGrid[i][column].removeFirst();
                toDelete--;
            }
            i--;
        }
    }
}
