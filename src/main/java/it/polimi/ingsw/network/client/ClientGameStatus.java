package it.polimi.ingsw.network.client;

import it.polimi.ingsw.exceptions.network.UnrecognisedPlayerException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

public class ClientGameStatus {
    /**
     * Ordered as on Server
     */
    private ArrayList<PlayerDashboard> players;
    private Shop shop;
    private MarketDashboard market;

    public ClientGameStatus(ArrayList<PlayerDashboard> players, Shop shop, MarketDashboard market) {
        this.players = players;
        this.shop = shop;
        this.market = market;
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
    public void updateShop(Deck[][] shopGrid){
        this.shop.setShopGrid(shopGrid);
    }

    /**
     * Updates the pathPosition of the player with that nickname
     * @param nickname nickname of the affected player
     * @param position new PathPosition coming from server
     */
    public void updatePathPosition(String nickname,int position){
        PlayerDashboard p = getClientDashboard(nickname);
        p.setPathPosition(position);
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
     * Puth the leader in Position, in Game
     * @param nickname nickname of the player
     * @param position position of the leaderCard
     */
    public void updateLeaderInGame(String nickname,int position){
        PlayerDashboard p = getClientDashboard(nickname);
        p.setLeaderInGame(position);
    }

    /**
     * Discards the leader in position
     * @param nickname nickname of the player
     * @param position position of the leaderCard
     */
    public void updateDiscardLeader(String nickname,int position){
        PlayerDashboard p = getClientDashboard(nickname);
        p.discardLeader(position);
    }

    /**
     * Init a new deposit
     * @param nickname nickname of the player
     * @param res Resource of the new deposit
     */
    public void initArrayDeposit(String nickname, Resource res){
        PlayerDashboard p = getClientDashboard(nickname);
        p.initArrayDeposit(res);
    }

    /**
     * Updates arrayDeposit
     * @param nickname nickname of the player
     * @param arrayDeposit new arrayDeposit from the server
     */
    public void updateArrayDeposit(String nickname,ArrayList<CounterTop> arrayDeposit){
        PlayerDashboard p = getClientDashboard(nickname);
        p.setArrayDeposit(arrayDeposit);
    }

    /**
     * Updates the chest
     * @param nickname nickname of the player
     * @param chest new chest from the server
     */
    public void updateChest(String nickname,ResourceCount chest){
        PlayerDashboard p = getClientDashboard(nickname);
        p.setChest(chest);
    }

    /**
     * Updates the DevCards
     * @param nickname nickname of the player
     * @param card bought card
     * @param position position of the card
     */
    public void updateDevCards(String nickname, DevelopmentCard card,int position){
        PlayerDashboard p = getClientDashboard(nickname);
        p.addDevCards(card,position);
    }

    /**
     * Updates the storage first row
     * @param nickname nickname of the player
     * @param firstRow new firstRow
     */
    public void updateFirstRow(String nickname,CounterTop firstRow){
        PlayerDashboard p = getClientDashboard(nickname);
        p.getStorage().setFirstRow(firstRow);
    }

    /**
     * Updates the storage second row
     * @param nickname nickname of the player
     * @param secondRow new secondRow
     */
    public void updateSecondRow(String nickname,CounterTop secondRow){
        PlayerDashboard p = getClientDashboard(nickname);
        p.getStorage().setSecondRow(secondRow);
    }

    /**
     * Updates the storage thirdRow
     * @param nickname nickname of the player
     * @param thirdRow new thirdRow
     */
    public void updateThirdRow(String nickname,CounterTop thirdRow){
        PlayerDashboard p = getClientDashboard(nickname);
        p.getStorage().setThirdRow(thirdRow);
    }

    /**
     * Updates the bufferProduction
     * @param nickname nickname of the player
     * @param bufferProduction new BufferProduction
     */
    public void updateBufferProduction(String nickname,ResourceCount bufferProduction){
        PlayerDashboard p = getClientDashboard(nickname);
        p.setBufferProduction(bufferProduction);
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
