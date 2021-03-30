package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColour;

import java.util.ArrayList;

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

    public void discardFromToken(CardColour colour){
        int column = colour.getColumn();
        ArrayList<DevelopmentCard> deck;
        int toDelete=2, i=2;
        while(toDelete > 0){
            deck = shopGrid[i][column].getDeck();
            while(deck.size() != 0 && toDelete > 0){
                deck.remove(0);
                toDelete--;
            }
            i--;
        }
    }
}
