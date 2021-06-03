package it.polimi.ingsw.network.client;

import it.polimi.ingsw.exceptions.network.UnrecognisedPlayerException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;
import java.util.Arrays;

public class ClientGameStatus {
    private ArrayList<PlayerDashboard> players;
    private Shop shop;
    private MarketDashboard market;
    private VaticanReport[] vReports;

    public ClientGameStatus(ArrayList<PlayerDashboard> players, Shop shop, MarketDashboard market, VaticanReport[] vReports) {
        this.players = players;
        this.shop = shop;
        this.market = market;
        this.vReports = vReports;
    }

    public VaticanReport[] getvReports() {
        return vReports;
    }

    public ArrayList<PlayerDashboard> getPlayers() {
        return players;
    }

    public Shop getShop() {
        return shop;
    }

    public MarketDashboard getMarket() {
        return market;
    }

    public void setPlayers(ArrayList<PlayerDashboard> players) {
        this.players = players;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setMarket(MarketDashboard market) {
        this.market = market;
    }

    /**
     * Updates the shop
     * @param shopGrid new shopGrid coming from the Server
     */
    public void updateShop(DeckShop[][] shopGrid){
        this.shop.setShopGrid(shopGrid);
    }

    /**
     * Updates the pathPosition of the player with that nickname
     * @param nickname nickname of the affected player
     * @param position new PathPosition coming from server
     */
    public void updatePathPosition(String nickname,int position){
        getClientDashboard(nickname).setPathPosition(position);
    }

    /**
     * Updates the MarketDashboard
     * @param structure new structure
     * @param freeMarble new freeMarble
     */
    public void updateMarket(MarketMarble[][] structure,MarketMarble freeMarble){
        market.setStructure(structure);
        market.setFreeMarble(freeMarble);
    }

    /**
     * Puth the leader in Game
     * @param nickname nickname of the player
     * @param updatedLeaderCards updated leader cards
     */
    public void updateLeaderCards(String nickname, ArrayList<LeaderCard> updatedLeaderCards){
        getClientDashboard(nickname).setLeaderCards(updatedLeaderCards);
    }

    /**
     * Init a new deposit
     * @param nickname nickname of the player
     * @param res Resource of the new deposit
     */
    public void initArrayDeposit(String nickname, Resource res){
        getClientDashboard(nickname).initArrayDeposit(res);
    }

    /**
     * Updates arrayDeposit
     * @param nickname nickname of the player
     * @param arrayDeposit new arrayDeposit from the server
     */
    public void updateArrayDeposit(String nickname,ArrayList<CounterTop> arrayDeposit){
        getClientDashboard(nickname).setArrayDeposit(arrayDeposit);
    }

    /**
     * Updates the chest
     * @param nickname nickname of the player
     * @param chest new chest from the server
     */
    public void updateChest(String nickname,ResourceCount chest){
        getClientDashboard(nickname).setChest(chest);
    }

    /**
     * Updates the DevCards
     * @param nickname nickname of the player
     * @param devCards new devCards
     */
    public void updateDevCards(String nickname, DeckDashboard[] devCards){
        getClientDashboard(nickname).setDevCards(devCards);
    }

    /**
     * Updates the storage first row
     * @param nickname nickname of the player
     * @param firstRow new firstRow
     */
    public void updateFirstRow(String nickname,CounterTop firstRow){
        getClientDashboard(nickname).getStorage().setFirstRow(firstRow);
    }

    /**
     * Updates the storage second row
     * @param nickname nickname of the player
     * @param secondRow new secondRow
     */
    public void updateSecondRow(String nickname,CounterTop secondRow){
        getClientDashboard(nickname).getStorage().setSecondRow(secondRow);
    }

    /**
     * Updates the storage thirdRow
     * @param nickname nickname of the player
     * @param thirdRow new thirdRow
     */
    public void updateThirdRow(String nickname,CounterTop thirdRow){
        getClientDashboard(nickname).getStorage().setThirdRow(thirdRow);
    }

    /**
     * Updates the bufferProduction
     * @param nickname nickname of the player
     * @param bufferProduction new BufferProduction
     */
    public void updateBufferProduction(String nickname,ResourceCount bufferProduction){
        getClientDashboard(nickname).setBufferProduction(bufferProduction);
    }


    /**
     * Updates player's victoryPoints
     * @param nickname nickname of the player
     * @param victoryPoints updated victoryPoints
     */
    public void updateVictoryPoints(String nickname,int victoryPoints){
        getClientDashboard(nickname).setPoints(victoryPoints);
    }

    public void updateVaticanReport(int victoryPoints){
        for(int i=0;i<3;i++){
            if(vReports[i].getVictoryPoints()==victoryPoints)
                vReports[i].setUsed();
        }
    }

    /**
     *
     * @param nickname nickname of the player we want the dashboard
     * @return PlayerDashboard of the player with the passed nickname
     */
    public PlayerDashboard getClientDashboard(String nickname){
        for(PlayerDashboard p:players){
            if(p.getNickname().equals(nickname))
                return p;
        }
        throw new UnrecognisedPlayerException();
    }
}
