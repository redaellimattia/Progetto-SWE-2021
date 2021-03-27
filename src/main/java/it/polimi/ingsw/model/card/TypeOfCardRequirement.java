package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;

public class TypeOfCardRequirement implements Requirement {
    private ColourCount cardColours;

    public TypeOfCardRequirement(ColourCount cardColours) {
        this.cardColours = cardColours;
    }

    @Override
    public boolean isPlayable(PlayerDashboard player) {
        return true;
    }
}
