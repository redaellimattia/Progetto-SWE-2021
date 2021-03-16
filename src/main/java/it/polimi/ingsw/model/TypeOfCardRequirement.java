package it.polimi.ingsw.model;

public class TypeOfCardRequirement extends Requirement{
    private ColourCount cardColours;

    public TypeOfCardRequirement(RequirementsType type,ColourCount cardColours) {
        super(type);
        this.cardColours = cardColours;
    }

    public ColourCount getCardColours() {
        return cardColours;
    }
}
