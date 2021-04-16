package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.Shop;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;

public class Parameter {
    //ProductionAction
    private ResourceCount storageCount;
    private ResourceCount chestCount;
    private LeaderCard leaderCard;
    private DevelopmentCard devCard;
    private Resource res;

    //OrganizeStorage_CardShop
    private int from_row;
    private int to_column;
    private int number_deckPosition;
    private CounterTop leaderDeposit;
    private Shop shop;

    public Parameter(ResourceCount storageCount, ResourceCount chestCount, LeaderCard leaderCard, DevelopmentCard devCard, Resource res, int from_row, int to_column, int number_deckPosition, CounterTop leaderDeposit, Shop shop) {
        this.storageCount = storageCount;
        this.chestCount = chestCount;
        this.leaderCard = leaderCard;
        this.devCard = devCard;
        this.res = res;
        this.from_row = from_row;
        this.to_column = to_column;
        this.number_deckPosition = number_deckPosition;
        this.leaderDeposit = leaderDeposit;
        this.shop = shop;
    }

    public ResourceCount getStorageCount() {
        return storageCount;
    }

    public void setStorageCount(ResourceCount storageCount) {
        this.storageCount = storageCount;
    }

    public ResourceCount getChestCount() {
        return chestCount;
    }

    public void setChestCount(ResourceCount chestCount) {
        this.chestCount = chestCount;
    }

    public LeaderCard getLeaderCard() {
        return leaderCard;
    }

    public void setLeaderCard(LeaderCard leaderCard) {
        this.leaderCard = leaderCard;
    }

    public DevelopmentCard getDevCard() {
        return devCard;
    }

    public void setDevCard(DevelopmentCard devCard) {
        this.devCard = devCard;
    }

    public Resource getRes() {
        return res;
    }

    public void setRes(Resource res) {
        this.res = res;
    }

    public int getFrom_row() {
        return from_row;
    }

    public void setFrom_row(int from_row) {
        this.from_row = from_row;
    }

    public int getTo_column() {
        return to_column;
    }

    public void setTo_column(int to_column) {
        this.to_column = to_column;
    }

    public int getNumber_deckPosition() {
        return number_deckPosition;
    }

    public void setNumber_deckPosition(int number_deckPosition) {
        this.number_deckPosition = number_deckPosition;
    }

    public CounterTop getLeaderDeposit() {
        return leaderDeposit;
    }

    public void setLeaderDeposit(CounterTop leaderDeposit) {
        this.leaderDeposit = leaderDeposit;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
