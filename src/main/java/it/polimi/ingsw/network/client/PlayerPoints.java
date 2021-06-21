package it.polimi.ingsw.network.client;

/**
 * Class used to save a player points for the endGame message
 */
public class PlayerPoints {
    private final String player;
    private final int victoryPoints;

    public PlayerPoints(String player, int victoryPoints) {
        this.player = player;
        this.victoryPoints = victoryPoints;
    }

    public String getPlayer() {
        return player;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}