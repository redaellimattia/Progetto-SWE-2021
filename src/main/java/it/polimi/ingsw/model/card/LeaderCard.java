package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.Requirement;
import it.polimi.ingsw.model.card.SpecialAbility;

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
