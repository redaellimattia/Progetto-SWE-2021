package it.polimi.ingsw.model.card;

public abstract class Card {
    private int victoryPoints;

    /**
     *
     * @param victoryPoints victoryPoints value of the card
     */
    public Card(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    /**
     *
     * @return victoryPoints value
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }
}
