package it.polimi.ingsw.model.card;

public class TypeOfCardRequirement implements Requirement {
    private ColourCount cardColours;

    public TypeOfCardRequirement(ColourCount cardColours) {
        this.cardColours = cardColours;
    }

    @Override
    public boolean isPlayable() {
        return true;
    }
}
