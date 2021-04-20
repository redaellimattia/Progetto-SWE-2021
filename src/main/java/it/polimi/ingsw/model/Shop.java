package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColour;

import java.util.ArrayList;

public class Shop {
    private Deck[][] shopGrid;

    /**
     *
     * @param shopGrid the matrix used to initialize the shop
     */
    public Shop(Deck[][] shopGrid) {
        this.shopGrid = shopGrid;
    }


    /**
     *
     * @param row selected shop's row
     * @param column selected shop's column
     * @return the first visible card from that position [row,column]
     */
    public DevelopmentCard getFirst(int row, int column){
        return shopGrid[row][column].getFirst();
    }

    /**
     *
     * @return the matrix of the shop
     */
    public Deck[][] getGrid(){ return shopGrid; }


    /**
     *
     * @param row selected shop's row
     * @param column selected shop's column
     * @return the first visible card from that position [row,column], after removing it from the shop
     */
    // BUY A SELECTED CARD FROM SHOP, RETURN THE CARD TO THE CALLER AND DELETE IT FROM THE GRID;
    public DevelopmentCard buy(int row, int column){ //the controller check if the player can buy the card before;
        if(shopGrid[row][column].getDeck().size() == 0)
            return null;

        DevelopmentCard bought = shopGrid[row][column].getFirst();
        shopGrid[row][column].removeFirst();
        return bought;
    }



    /**
     *
     * @param colour the specific colour of which 2 development card have to be discarded from the shop (soloplayer)
     */
    //METHOD TO DISCARD 2 CARD FROM THE POOL OF CARD OF THAT COLOUR BECAUSE OF THE SOLOTOKEN;
    public void discardFromToken(CardColour colour){
        int column = colour.getColumn();
        ArrayList<DevelopmentCard> deck;
        int toDelete=2, i=2;
        do {
            deck = shopGrid[i][column].getDeck();
            while(!deck.isEmpty() && toDelete > 0){
                shopGrid[i][column].removeFirst();
                toDelete--;
            }
            i--;
        }while(toDelete > 0 && i>=0);

    }
}
