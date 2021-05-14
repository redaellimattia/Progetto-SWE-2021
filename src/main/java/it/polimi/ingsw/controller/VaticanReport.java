package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.network.server.Observer;

import java.util.ArrayList;

public class VaticanReport {
    private boolean isUsed;
    private final int victoryPoints;
    private final int start;
    private final int finish;
    private Observer observer;

    /**
     * Adds reference to the observer
     * @param observer ServerThread that is observing the Player
     */
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    /**
     * Remove reference to the observer
     * @param observer ServerThread that is observing the Player
     */
    public void removeObserver(Observer observer) {
        this.observer = null;
    }


    public VaticanReport(int victoryPoints, int start, int finish) {
        this.isUsed = false;
        this.victoryPoints = victoryPoints;
        this.start = start;
        this.finish = finish;
    }

    public boolean isUsed() { return isUsed; }

    public void activateReport(ArrayList<PlayerDashboard> players){
        for (PlayerDashboard p: players) {
            int pos = p.getPathPosition();
            if(pos >= start && pos <= finish) {
                p.addVictoryPoints(victoryPoints);
                observer.updateVaticanReport(p.getNickname(),victoryPoints,true);
            }
            else
                observer.updateVaticanReport(p.getNickname(),victoryPoints,false);
        }
        isUsed = true;
    }
}
