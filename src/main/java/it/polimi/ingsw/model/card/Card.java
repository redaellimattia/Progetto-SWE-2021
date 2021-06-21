package it.polimi.ingsw.model.card;

public abstract class Card {
    private final int victoryPoints;
    private final int id;

    /**
     *
     * @param victoryPoints victoryPoints value of the card
     * @param id the ID assigned to the card
     */
    public Card(int id, int victoryPoints) {
        this.id = id;
        this.victoryPoints = victoryPoints;
    }

    /**
     *
     * @return victoryPoints value
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     *
     * @return the ID of the card
     */
    public int getId() {
        return id;
    }
}
