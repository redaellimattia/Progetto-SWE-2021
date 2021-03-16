package it.polimi.ingsw.model;

public class MarketDashboard {
    private MarketMarble[][] structure = new MarketMarble[3][4];
    private MarketMarble freeMarble;

    public MarketMarble getFreeMarble() {
        return freeMarble;
    }

    public void setFreeMarble(MarketMarble freeMarble) {
        this.freeMarble = freeMarble;
    }

    public void swapStructure(){

    }
}
