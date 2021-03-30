package it.polimi.ingsw.model.card;

public class LeaderCard extends Card {
    private Requirement requirement;
    private SpecialAbility specialAbility;
    private boolean inGame;

    public LeaderCard(int victoryPoints,Requirement requirement, SpecialAbility specialAbility) {
        super(victoryPoints);
        this.requirement = requirement;
        this.specialAbility = specialAbility;
        this.inGame = false;
    }

    //GETTERS
    public boolean isInGame() {
        return inGame;
    } //True if leaderCard is in Game
    public Requirement getRequirement() {
        return requirement;
    }
    public SpecialAbility getSpecialAbility() {
        return specialAbility;
    }

    public void setInGame() {
        this.inGame = true;
    } //LeaderCard has been played
}
