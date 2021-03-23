package it.polimi.ingsw.model.card;

public abstract class Card {
    private int victoryPoints;

    public Card(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
