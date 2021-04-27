package it.polimi.ingsw.model.card;

public class LeaderCard extends Card {
    private Requirement requirement;
    private SpecialAbility specialAbility;
    private boolean inGame;

    /**
     *
     * @param victoryPoints victoryPoints value of the card
     * @param requirement requirement of the card as Requirement
     * @param specialAbility specialAbility of the Card as SpecialAbility
     */
    public LeaderCard(int victoryPoints,Requirement requirement, SpecialAbility specialAbility) {
        super(victoryPoints);
        this.requirement = requirement;
        this.specialAbility = specialAbility;
        this.inGame = false;
    }

    //GETTERS

    /**
     *
     * @return true if this is in game
     */
    public boolean isInGame() {
        return inGame;
    } //True if leaderCard is in Game

    /**
     *
     * @return requirement as Requirement
     */
    public Requirement getRequirement() {
        return requirement;
    }

    /**
     *
     * @return specialAbility as SpecialAbility
     */
    public SpecialAbility getSpecialAbility() {
        return specialAbility;
    }

    /**
     * turns the card inGame value = true
     */
    public void setInGame() {
        this.inGame = true;
    } //LeaderCard has been played

    /**
     * EQUALS
     *
     * @param o passed Object
     * @return true if o is the same object of this, or it has the same attributes
     */
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof LeaderCard))
            return false;

        //Check if same values
        LeaderCard c = (LeaderCard) o;
        return this.getVictoryPoints() == c.getVictoryPoints() && this.getRequirement().equals(c.getRequirement()) &&
                this.getSpecialAbility().equals(c.getSpecialAbility()) && this.inGame == c.inGame;
    }
}
