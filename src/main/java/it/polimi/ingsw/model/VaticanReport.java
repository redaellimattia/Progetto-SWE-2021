package it.polimi.ingsw.model;

import it.polimi.ingsw.network.server.Observer;

import java.util.ArrayList;

/**
 * VaticanReports Class
 */
public class VaticanReport {
    private boolean isUsed;
    private final int victoryPoints;
    private final int start;
    private final int finish;
    private transient Observer observer;

    /**
     * Adds reference to the observer
     * @param observer ServerLobby that is observing the Player
     */
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    public VaticanReport(int victoryPoints, int start, int finish) {
        this.isUsed = false;
        this.victoryPoints = victoryPoints;
        this.start = start;
        this.finish = finish;
    }

    public void setUsed() {
        isUsed = true;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public boolean isUsed() { return isUsed; }

    /**
     * Activate a report, add points to the affected players and update the observer
     * @param players players in the game
     */
    public void activateReport(ArrayList<PlayerDashboard> players){
        ArrayList<String> nicknames = new ArrayList<>();
        for (PlayerDashboard p: players) {
            int pos = p.getPathPosition();
            if(pos >= start && pos <= finish) {
                p.addVictoryPoints(victoryPoints);
                nicknames.add(p.getNickname());
            }
        }
        isUsed = true;
        observer.updateVaticanReport(victoryPoints,nicknames);
    }
}
