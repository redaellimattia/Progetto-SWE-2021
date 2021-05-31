package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.EmptyDeckException;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.network.server.Observer;

import java.util.ArrayList;

public class Shop {
    private DeckShop[][] shopGrid;
    private transient Observer observer;

    /**
     * Adds reference to the observer
     * @param observer ServerLobby that is observing the Shop
     */
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    /**
     * Remove reference to the observer
     * @param observer ServerLobby that is observing the Shop
     */
    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    /**
     *
     * @param shopGrid the matrix used to initialize the shop
     */
    public Shop(DeckShop[][] shopGrid) {
        this.shopGrid = shopGrid;
    }

    public void setShopGrid(DeckShop[][] shopGrid) {
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
    public DevelopmentCard buy(int row, int column, PlayerDashboard player, PlayerTurnManager turnManager) throws EmptyDeckException { //the controller check if the player can buy the card before;
        if(shopGrid[row][column].getDeck().size() == 0)
            throw new EmptyDeckException(player,turnManager);

        DevelopmentCard bought = shopGrid[row][column].getFirst();
        shopGrid[row][column].removeFirst();
        observer.updateShop(shopGrid);
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
        observer.updateShop(shopGrid);
    }

    public boolean emptyColumn(){
        if(shopGrid[0][0].getDeck().size() == 0 && shopGrid[1][0].getDeck().size() == 0 && shopGrid[2][0].getDeck().size() == 0 )
            return true;
        if(shopGrid[0][1].getDeck().size() == 0 && shopGrid[1][1].getDeck().size() == 0 && shopGrid[2][1].getDeck().size() == 0 )
            return true;
        return shopGrid[0][2].getDeck().size() == 0 && shopGrid[1][2].getDeck().size() == 0 && shopGrid[2][2].getDeck().size() == 0;
    }

    public DevelopmentCard getCardByID(int ID){
        DevelopmentCard card = null;
        for(int i=0;i<3;i++)
            for(int j=0;j<4;j++) {
                if(shopGrid[i][j].getDeck().size()>0) {
                    card = shopGrid[i][j].getFirst();
                    if (card.getId() == ID)
                        return card;
                }
            }
        return null;
    }
}
