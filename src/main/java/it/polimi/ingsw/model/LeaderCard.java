package it.polimi.ingsw.model;

public class LeaderCard extends Card{
    private Requirement requirement;
    private SpecialAbility specialAbility;
    private boolean inGame;

    public LeaderCard(Requirement requirement, SpecialAbility specialAbility) {
        this.requirement = requirement;
        this.specialAbility = specialAbility;
        this.inGame = false;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public SpecialAbility getSpecialAbility() {
        return specialAbility;
    }
}
