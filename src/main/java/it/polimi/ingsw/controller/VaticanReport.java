package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PlayerDashboard;

import java.util.ArrayList;

public class VaticanReport {
    private final boolean isUsed;
    private final int victoryPoints;
    private final int start;
    private final int finish;

    public VaticanReport(boolean isUsed, int victoryPoints, int start, int finish) {
        this.isUsed = isUsed;
        this.victoryPoints = victoryPoints;
        this.start = start;
        this.finish = finish;
    }

    public boolean isUsed() { return isUsed; }

    public void activateReport(ArrayList<PlayerDashboard> players, PlayerDashboard playerOnTile){
        for (PlayerDashboard p: players) {
            int pos = p.getPathPosition();
            if(pos >= start && pos <= finish)
                p.addPoints(victoryPoints);
        }
    }
}
